package com.reading.mscadastro.infrastructure.client;

import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.ResenhaDTO;
import com.reading.mscadastro.infrastructure.client.response.NotificacaoResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientFallBack implements NotificacaoClient {

    private final Logger log = LoggerFactory.getLogger(NotificacaoClientFallBack.class);

    @Override
    public ResponseEntity<NotificacaoResponse> criar(ResenhaDTO request) {
        log.error("Houve um erro ao tentar criar uma notificação para a resenha de id {}", request.getId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<NotificacaoResponse> criar(LivroDTO request) {
        log.error("Houve um erro ao tentar criar uma notificação para o livro de id {}", request.getId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
