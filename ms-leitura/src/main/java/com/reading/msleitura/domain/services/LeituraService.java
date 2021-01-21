package com.reading.msleitura.domain.services;

import com.reading.msleitura.application.dto.LeituraRequest;
import com.reading.msleitura.application.dto.LeituraResponse;
import com.reading.msleitura.domain.model.Leitura.NotasLivro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeituraService {

    LeituraResponse criar(LeituraRequest request);

    void atualizarNota(Long id, NotasLivro nota);

    void atualizarPagina(Long id, Long pagina);

    void finalizarLeitura(Long id);

    void iniciarLeitura(Long id);

    void incluirNaListaParaLer(Long id);

    LeituraResponse buscar(Long id);

    Page<LeituraResponse> buscar(Pageable page);
}
