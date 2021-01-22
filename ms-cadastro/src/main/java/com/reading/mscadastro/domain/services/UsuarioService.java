package com.reading.mscadastro.domain.services;

import javax.validation.Valid;

import com.reading.mscadastro.application.dto.UsuarioDTO;
import com.reading.mscadastro.application.dto.UsuarioPostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    
    UsuarioDTO buscar(Long id);

	Page<UsuarioDTO> buscar(Pageable pageable);

	UsuarioDTO criar(@Valid UsuarioPostDTO dto);
}
