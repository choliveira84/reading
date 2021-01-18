package com.reading.msnotificacao.domain.service;

import com.reading.msnotificacao.application.dto.NotificacaoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoLivroPublicadoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoResenhaDTO;
import com.reading.msnotificacao.domain.model.Notificacao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoMapper {

    @Autowired
    private ModelMapper mapper;

    public NotificacaoDTO paraResponse(Notificacao notificacao) {
        NotificacaoDTO map = mapper.map(notificacao, NotificacaoDTO.class);
        return map;
    }

    public Notificacao paraNotificacao(NotificacaoResenhaDTO request) {
        Notificacao map = mapper.map(request, Notificacao.class);
        return map;
    }

    public Notificacao paraNotificacao(NotificacaoLivroPublicadoDTO request) {
        Notificacao map = mapper.map(request, Notificacao.class);
        return map;
    }
}
