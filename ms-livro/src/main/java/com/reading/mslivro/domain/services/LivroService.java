package com.reading.mslivro.domain.services;

import com.reading.mslivro.application.dto.LivroDTO;
import com.reading.mslivro.application.dto.LivroPostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroService {

    LivroDTO criar(LivroPostDTO dto);

    Page<LivroDTO> buscar(Pageable pageable);

    LivroDTO buscar(Long id);
}
