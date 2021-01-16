package com.reading.mscadastro.application.impl;

import java.util.Optional;

import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.LivroPostDTO;
import com.reading.mscadastro.domain.model.Livro;
import com.reading.mscadastro.domain.repository.LivroRepository;
import com.reading.mscadastro.domain.services.LivroService;
import com.reading.mscadastro.infrastructure.exceptions.EntityAlreadyExistsException;
import com.reading.mscadastro.infrastructure.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public LivroDTO criar(LivroPostDTO dto) {
        // log
        if (repository.existsByIsbn(dto.getIsbn())) {
            throw new EntityAlreadyExistsException(String.format("Já existe um livro com o ISBN %s", dto.getIsbn()));
        }

        return mapearParaDTO(repository.save(mapearParaEntidade(dto)));
    }

    @Override
    public Page<LivroDTO> buscar(Pageable pageable) {
        // log
        PageRequest page = PageRequest.of((int) pageable.getOffset(), pageable.getPageSize(),
                Sort.by(Direction.ASC, "titulo"));

        return repository.findAll(page).map(this::mapearParaDTO);
    }

    @Override
    public LivroDTO buscar(Long id) {
        // log
        Optional<Livro> livroOptional = repository.findById(id); // NOSONAR

        if (!livroOptional.isPresent()) {
            throw new EntityNotFoundException(String.format("Livro de id %s não encontrado", id));
        } else {
            return mapearParaDTO(livroOptional.get());
        }
    }

    private Livro mapearParaEntidade(LivroPostDTO dto) {
        return new Livro(null, dto.getCapa(), dto.getTitulo(), dto.getIsbn(), dto.getAutor(), dto.getNumeroPagina(),
                dto.getAnoLancamento());
    }

    private LivroDTO mapearParaDTO(Livro livro) {
        return mapper.map(livro, LivroDTO.class);
    }

}
