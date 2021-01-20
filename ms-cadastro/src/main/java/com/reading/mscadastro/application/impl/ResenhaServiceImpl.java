package com.reading.mscadastro.application.impl;

import java.util.Optional;

import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.ResenhaDTO;
import com.reading.mscadastro.application.dto.ResenhaPostDTO;
import com.reading.mscadastro.application.dto.UsuarioDTO;
import com.reading.mscadastro.domain.model.Livro;
import com.reading.mscadastro.domain.model.Resenha;
import com.reading.mscadastro.domain.model.Usuario;
import com.reading.mscadastro.domain.repository.ResenhaRepository;
import com.reading.mscadastro.domain.services.LivroService;
import com.reading.mscadastro.domain.services.ResenhaService;
import com.reading.mscadastro.domain.services.UsuarioService;
import com.reading.mscadastro.infrastructure.events.CustomEvent;
import com.reading.mscadastro.infrastructure.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class ResenhaServiceImpl implements ResenhaService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ResenhaRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LivroService livroService;

    @Transactional
    @Override
    public ResenhaDTO criar(ResenhaPostDTO dto) {
        ResenhaDTO resenhaSalva = mapearParaDTO(repository.save(mapearParaEntidade(dto)));

        eventPublisher.publishEvent(new CustomEvent(resenhaSalva));

        return resenhaSalva;
    }

    @Override
    public Page<ResenhaDTO> buscar(Pageable pageable) {
        PageRequest page = PageRequest.of((int) pageable.getOffset(), pageable.getPageSize(),
                Sort.by(Direction.ASC, "titulo"));

        return repository.findAll(page).map(this::mapearParaDTO);
    }

    @Override
    public ResenhaDTO buscar(Long id) {
        Optional<Resenha> ResenhaOptional = repository.findById(id); // NOSONAR

        if (!ResenhaOptional.isPresent()) {
            throw new EntityNotFoundException(String.format("Resenha de id %s não encontrada", id));
        } else {
            return mapearParaDTO(ResenhaOptional.get());
        }
    }

    private Resenha mapearParaEntidade(ResenhaPostDTO dto) {
        UsuarioDTO usuarioDTO = usuarioService.buscar(dto.getUsuarioId());
        LivroDTO livroDTO = livroService.buscar(dto.getLivroId());

        Livro livro = new Livro(livroDTO.getId(), livroDTO.getCapa(), livroDTO.getTitulo(), livroDTO.getAutor(),
                livroDTO.getIsbn(), livroDTO.getNumeroPagina(), livroDTO.getAnoLancamento());

        Usuario usuario = new Usuario(usuarioDTO.getId(), usuarioDTO.getNome(), usuarioDTO.getCidade(),
                usuarioDTO.getEmail(), usuarioDTO.getPais(), usuarioDTO.getFoto());

        return new Resenha(null, dto.getDescricao(), dto.getTitulo(), usuario, livro);
    }

    private ResenhaDTO mapearParaDTO(Resenha resenha) {
        return mapper.map(resenha, ResenhaDTO.class);
    }

}
