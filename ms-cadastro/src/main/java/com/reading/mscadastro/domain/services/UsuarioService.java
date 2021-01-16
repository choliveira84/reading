package com.reading.mscadastro.domain.services;

import com.reading.mscadastro.application.dto.UsuarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    
    UsuarioDTO buscar(Long id);

	Page<UsuarioDTO> buscar(Pageable pageable);
}
