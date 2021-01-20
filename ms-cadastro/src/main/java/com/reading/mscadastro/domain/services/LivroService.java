package com.reading.mscadastro.domain.services;

import java.util.List;

import com.reading.mscadastro.application.dto.LivroConsultaExternaDTO;
import com.reading.mscadastro.application.dto.LivroCriterioDTO;
import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.LivroPostDTO;
import com.reading.mscadastro.infrastructure.events.service.ApplicationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroService extends ApplicationService {

    LivroDTO criar(LivroPostDTO dto);

    /**
     * Irá buscar os dados de acordo com o critério feito pelo usuário.
     * 
     * @param criterio
     * @return Lista de livros
     */
    List<LivroConsultaExternaDTO> buscar(String criterio);

    /**
     * Irá buscar os livros já cadastrados previamente.
     * 
     * @param dto
     * @param pageable
     * @return Lista de livros
     */
    Page<LivroDTO> buscar(LivroCriterioDTO dto, Pageable pageable);

    LivroDTO buscar(Long id);
}
