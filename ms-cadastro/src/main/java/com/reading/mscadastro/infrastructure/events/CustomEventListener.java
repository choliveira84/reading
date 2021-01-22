package com.reading.mscadastro.infrastructure.events;

import com.reading.mscadastro.infrastructure.client.NotificacaoClient;
import com.reading.mscadastro.infrastructure.client.response.NotificacaoResponse;
import com.reading.mscadastro.infrastructure.events.CustomEvent.TiposNotificacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    private final Logger log = LoggerFactory.getLogger(CustomEventListener.class);

    @Autowired
    private NotificacaoClient notificacaoClient;

    
    @Async
    @Override
    public void onApplicationEvent(CustomEvent event) {
        try {
            log.debug("Tentando criar a notificação do livro...");

            ResponseEntity<NotificacaoResponse> response;

            TiposNotificacao tipo = event.getTipo();

            if (TiposNotificacao.LIVRO == tipo) {
                response = notificacaoClient.criar(event.getLivro());
            } else {
                response = notificacaoClient.criar(event.getResenha());
            }

            log.debug("Resposta: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Houve o seguinte erro ao tentar enviar o evento: {}", e.getMessage());
        }
    }

}
