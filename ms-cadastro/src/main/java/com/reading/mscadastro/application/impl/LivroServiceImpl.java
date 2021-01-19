package com.reading.mscadastro.application.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.reading.mscadastro.application.dto.LivroConsultaExternaDTO;
import com.reading.mscadastro.application.dto.LivroCriterioDTO;
import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.LivroPostDTO;
import com.reading.mscadastro.domain.model.Livro;
import com.reading.mscadastro.domain.repository.LivroRepository;
import com.reading.mscadastro.domain.services.LivroService;
import com.reading.mscadastro.infrastructure.exceptions.BadRequestException;
import com.reading.mscadastro.infrastructure.exceptions.EntityAlreadyExistsException;
import com.reading.mscadastro.infrastructure.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
class LivroServiceImpl implements LivroService {

    private static final String UNCHECKED = "unchecked";

    private static final String RAWTYPES = "rawtypes";

    private static final String VALUE = "value";

    private static final String PROP_NULA = "O tipo da propriedade está nula";

    private final Logger log = LoggerFactory.getLogger(LivroServiceImpl.class);

    private static final String URL_BIGGLABS = "https://search.biggylabs.com.br/search-api/v1/livrariacultura/autocomplete/suggestion_products?term=";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LivroRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    @Override
    public LivroDTO criar(LivroPostDTO dto) {
        log.debug("Requisição para criar um livro a partir do DTO {}", dto);

        if (repository.existsByIsbn(dto.getIsbn())) {
            throw new EntityAlreadyExistsException(String.format("Já existe um livro com o ISBN %s", dto.getIsbn()));
        }

        return mapearParaDTO(repository.save(mapearParaEntidade(dto)));
    }

    @SuppressWarnings(value = { UNCHECKED, RAWTYPES })
    @Override
    public List<LivroConsultaExternaDTO> buscar(String criterio) {
        log.debug("Requisição para buscar livros a partir do critério {}", criterio);

        if (criterio.length() < 3) {
            throw new BadRequestException("O critério da busca deve ter, pelo menos, 3 caracters");
        }

        String busca = URL_BIGGLABS + criterio;

        try {
            LinkedHashMap<String, Object> lista = restTemplate.getForObject(busca, LinkedHashMap.class);

            if (Objects.isNull(lista)) {
                return Collections.emptyList();
            }

            log.debug("Itens retornados: {}", lista.size());

            List<LivroConsultaExternaDTO> retorno = new ArrayList<>();

            var products = (List<Object>) lista.get("products");

            products.forEach(produto -> {
                try {
                    var object = (LinkedHashMap) produto;

                    LivroConsultaExternaDTO livro = new LivroConsultaExternaDTO();

                    livro.setTitulo(obterPropString(object.get("name")));
                    livro.setCapa(obterCapa(object.get("images")));

                    obterPropriedades(object.get("attributes"), livro);

                    retorno.add(livro);
                } catch (Exception e) {
                    log.error("Houve o seguinte erro ao tentar converter o item em objeto: {}", e.getMessage());
                }
            });

            return retorno;
        } catch (Exception e) {
            log.error("Houve o seguinte erro ao tentar obter os itens: {}", e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public Page<LivroDTO> buscar(LivroCriterioDTO dto, Pageable pageable) {
        log.debug("Requisição para buscar o livro a partir do criterio: {}, página {}", dto, pageable);

        PageRequest page = PageRequest.of((int) pageable.getOffset(), pageable.getPageSize(),
                Sort.by(Direction.ASC, "titulo"));

        return repository.findAll(page).map(this::mapearParaDTO);
    }

    @Override
    public LivroDTO buscar(Long id) {
        log.debug("Requisição para buscar o livro a partir do id: {}", id);

        Optional<Livro> livroOptional = repository.findById(id); // NOSONAR

        if (!livroOptional.isPresent()) {
            throw new EntityNotFoundException(String.format("Livro de id %s não encontrado", id));
        } else {
            return mapearParaDTO(livroOptional.get());
        }
    }

    private Livro mapearParaEntidade(LivroPostDTO dto) {
        return new Livro(dto.getCapa(), dto.getTitulo(), dto.getIsbn(), dto.getAutor(), dto.getNumeroPagina(),
                dto.getAnoLancamento());
    }

    private LivroDTO mapearParaDTO(Livro livro) {
        return mapper.map(livro, LivroDTO.class);
    }

    @SuppressWarnings(value = { RAWTYPES })
    private String obterPropString(Object prop) {
        if (prop instanceof String) {
            return (String) prop;
        } else if (prop instanceof ArrayList) {
            return (String) ((List) prop).get(0); // NOSONAR
        } else if (prop instanceof Integer) {
            return (String) prop;
        }

        log.warn(PROP_NULA);
        return "-";
    }

    @SuppressWarnings(value = { RAWTYPES })
    private String obterCapa(Object items) {
        if (items instanceof ArrayList) {
            Object list = ((List) items).get(0);

            if (list != null) {
                Object item = ((LinkedHashMap) list).get(VALUE);

                if (item != null) {
                    return (String) item;
                }
            }
        }

        log.warn(PROP_NULA);
        return "http://url.nula.com";
    }

    @SuppressWarnings(value = { RAWTYPES, UNCHECKED })
    private void obterPropriedades(Object attributes, LivroConsultaExternaDTO livro) {
        var att = new ArrayList<LinkedHashMap>(); // NOSONAR

        att.addAll((ArrayList) attributes);

        att.forEach(item -> {
            String key = (String) item.get("key");

            if ("ano".equals(key)) {
                String value = (String) item.get(VALUE);
                livro.setAnoLancamento(value);
            } else if ("autor".equals(key)) {
                String labelValue = (String) item.get("labelValue");
                livro.setAutor(labelValue);
            } else if ("isbn".equals(key)) {
                String value = (String) item.get(VALUE);
                livro.setIsbn(value);
            } else if ("paginas".equals(key)) {
                Long value = Long.parseLong((String) item.get(VALUE));
                livro.setNumeroPagina(value);
            }
        });
    }

}
