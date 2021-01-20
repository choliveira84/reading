package com.reading.mscadastro.infrastructure.events.service.impl;

import com.reading.mscadastro.infrastructure.client.NotificacaoClient;
import com.reading.mscadastro.infrastructure.client.request.NotificacaoLivroRequest;
import com.reading.mscadastro.infrastructure.client.request.NotificacaoResenhaRequest;
import com.reading.mscadastro.infrastructure.client.response.NotificacaoResponse;
import com.reading.mscadastro.infrastructure.config.Constants;
import com.reading.mscadastro.infrastructure.events.interfaces.ApplicationEvent;
import com.reading.mscadastro.infrastructure.events.interfaces.EventBus;
import com.reading.mscadastro.infrastructure.events.interfaces.EventSubscriber;
import com.reading.mscadastro.infrastructure.events.service.ApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApplicationServiceImp implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImp.class);

    private EventBus eventBus;

    @Autowired
    private NotificacaoClient notificacaoClient;

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void listenLivroSalvoEvent() {
        log.debug("Tentando criar a notificação do livro...");

        this.eventBus.subscribe(Constants.EVENT_LIVRO_SALVO, new EventSubscriber() {
            @Override
            public <E extends ApplicationEvent> void onEvent(E event) {
                String livroId = event.getPayloadValue("livro_id");
                String livroTitulo = event.getPayloadValue("livro_titulo");

                NotificacaoLivroRequest request = new NotificacaoLivroRequest(livroTitulo, Long.parseLong(livroId));
                ResponseEntity<NotificacaoResponse> response = notificacaoClient.criar(request);

                log.debug("Resposta: {}", response.getStatusCode());
            }
        });
    }

    public void listenResenhaSalvoEvent() {
        log.debug("Tentando criar a notificação da resenha...");

        this.eventBus.subscribe(Constants.EVENT_RESENHA_PUBLICADA, new EventSubscriber() {
            @Override
            public <E extends ApplicationEvent> void onEvent(E event) {
                String resenhaId = event.getPayloadValue("resenha_id");
                String resenhaTitulo = event.getPayloadValue("resenha_titulo");
                String livroId = event.getPayloadValue("livro_id");
                String usuarioId = event.getPayloadValue("usuario_id");

                NotificacaoResenhaRequest request = new NotificacaoResenhaRequest(Long.parseLong(usuarioId),
                        resenhaTitulo, Long.parseLong(resenhaId), Long.parseLong(livroId));

                ResponseEntity<NotificacaoResponse> response = notificacaoClient.criar(request);

                log.debug("Resposta: {}", response.getStatusCode());
            }
        });
    }

}
