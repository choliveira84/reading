package com.reading.msleitura.application.impl;

import java.util.Optional;

import com.reading.msleitura.application.dto.LeituraRequest;
import com.reading.msleitura.application.dto.LeituraResponse;
import com.reading.msleitura.domain.model.Leitura;
import com.reading.msleitura.domain.model.Leitura.NotasLivro;
import com.reading.msleitura.domain.repository.LeituraRepository;
import com.reading.msleitura.domain.services.LeituraService;
import com.reading.msleitura.infrastructure.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeituraServiceImpl implements LeituraService {

    private final Logger log = LoggerFactory.getLogger(LeituraServiceImpl.class);

    @Autowired
    private LeituraRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public LeituraResponse criar(LeituraRequest request) {
        log.debug("Criando leitura {}", request);

        Leitura novaLeitura = mapearParaEntidade(request);

        return mapearParaDTO(repository.save(novaLeitura));
    }

    @Override
    public void atualizarNota(Long id, NotasLivro nota) {
        log.debug("Atualizando a nota do livro de id {}", id);

        Leitura leituraEncontrada = buscarLivroPorId(id);

        leituraEncontrada.atualizarNota(nota);

        repository.save(leituraEncontrada);
    }

    @Override
    public void atualizarPagina(Long id, Long pagina) {
        log.debug("Atualizando a página do livro de id {}", id);

        Leitura leituraEncontrada = buscarLivroPorId(id);

        leituraEncontrada.atualizarPagina(pagina);

        repository.save(leituraEncontrada);
    }

    @Override
    public void finalizarLeitura(Long id) {
        log.debug("Finalizando a leitura do livro de id {}", id);

        Leitura leituraEncontrada = buscarLivroPorId(id);

        leituraEncontrada.finalizarLeitura();

        repository.save(leituraEncontrada);
    }

    @Override
    public void iniciarLeitura(Long id) {
        log.debug("Iniciando a leitura do livro de id {}", id);

        Leitura leituraEncontrada = buscarLivroPorId(id);

        leituraEncontrada.iniciarLeitura();

        repository.save(leituraEncontrada);
    }

    @Override
    public void incluirNaListaParaLer(Long id) {
        log.debug("Incluindo o livro de id {} na lista de leitura", id);

        Leitura leituraEncontrada = buscarLivroPorId(id);

        leituraEncontrada.incluirNaListaDeLeitura();

        repository.save(leituraEncontrada);
    }

    @Override
    public LeituraResponse buscar(Long id) {
        log.debug("Buscando livros de acordo com o id {}", id);

        Optional<Leitura> possivelLeitura = repository.findById(id);

        if (!possivelLeitura.isPresent()) {
            throw new EntityNotFoundException(String.format("Livro não existe com o id %s", id));
        }

        return mapearParaDTO(possivelLeitura.get());
    }

    private Leitura buscarLivroPorId(Long id) {
        Optional<Leitura> possivelLeitura = repository.findByIdLivro(id);

        if (!possivelLeitura.isPresent()) {
            throw new EntityNotFoundException(String.format("Livro não existe com o id %s", id));
        }

        return possivelLeitura.get();
    }

    @Override
    public Page<LeituraResponse> buscar(Pageable page) {
        log.debug("Buscando livros de acordo com o page {}", page);

        return repository.findAll(page).map(this::mapearParaDTO);
    }

    private Leitura mapearParaEntidade(LeituraRequest dto) {
        return new Leitura(null, dto.getIdUsuario(), dto.getNomeUsuario(), dto.getIdLivro(), dto.getTituloLivro(), null,
                null, dto.getPaginaAtual(), dto.getNota(), dto.getStatus());
    }

    private LeituraResponse mapearParaDTO(Leitura livro) {
        return mapper.map(livro, LeituraResponse.class);
    }

}
