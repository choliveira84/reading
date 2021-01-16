package com.reading.mscadastro.domain.services;

import com.reading.mscadastro.application.dto.ResenhaDTO;
import com.reading.mscadastro.application.dto.ResenhaPostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResenhaService {

    ResenhaDTO buscar(Long id);

    ResenhaDTO criar(ResenhaPostDTO dto);

    Page<ResenhaDTO> buscar(Pageable pageable);

}
