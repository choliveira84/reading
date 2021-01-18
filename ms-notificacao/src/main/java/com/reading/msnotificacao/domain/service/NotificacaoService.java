package com.reading.msnotificacao.domain.service;

import com.reading.msnotificacao.application.dto.NotificacaoBuscaDTO;
import com.reading.msnotificacao.application.dto.NotificacaoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoLivroPublicadoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoResenhaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificacaoService {

    Page<NotificacaoDTO> buscar(NotificacaoBuscaDTO request, Pageable pageable);

    NotificacaoDTO criar(NotificacaoLivroPublicadoDTO request);

    NotificacaoDTO criar(NotificacaoResenhaDTO request);

    void deletar(Long id);

	NotificacaoDTO consultar(Long id);

}
