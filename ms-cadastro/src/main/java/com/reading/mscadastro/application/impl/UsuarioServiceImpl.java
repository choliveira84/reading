package com.reading.mscadastro.application.impl;

import java.util.Optional;

import com.reading.mscadastro.application.dto.UsuarioDTO;
import com.reading.mscadastro.application.dto.UsuarioPostDTO;
import com.reading.mscadastro.domain.model.Usuario;
import com.reading.mscadastro.domain.repository.UsuarioRepository;
import com.reading.mscadastro.domain.services.UsuarioService;
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
class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UsuarioDTO buscar(Long id) {
        Optional<Usuario> usuarioOptional = repository.findById(id); // NOSONAR

        if (!usuarioOptional.isPresent()) {
            throw new EntityNotFoundException(String.format("Usuário de id %s não encontrado", id));
        } else {
            return mapearParaDTO(usuarioOptional.get());
        }
    }

    @Override
    public Page<UsuarioDTO> buscar(Pageable pageable) {
        PageRequest page = PageRequest.of((int) pageable.getOffset(), pageable.getPageSize(),
                Sort.by(Direction.ASC, "nome"));

        return repository.findAll(page).map(this::mapearParaDTO);
    }

    @Override
    public UsuarioDTO criar(UsuarioPostDTO dto) {
        Usuario usuarioSalvo = repository.save(mapearParaEntidade(dto));

        return mapearParaDTO(usuarioSalvo);
    }

    private UsuarioDTO mapearParaDTO(Usuario usuario) {
        return mapper.map(usuario, UsuarioDTO.class);
    }

    private Usuario mapearParaEntidade(UsuarioPostDTO dto) {
        return new Usuario(null, dto.getNome(), dto.getCidade(), dto.getEmail(), dto.getPais(), dto.getFoto());
    }

}
