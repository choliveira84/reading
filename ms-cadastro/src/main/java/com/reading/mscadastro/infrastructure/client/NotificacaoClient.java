package com.reading.mscadastro.infrastructure.client;

import com.reading.mscadastro.infrastructure.client.request.NotificacaoLivroRequest;
import com.reading.mscadastro.infrastructure.client.request.NotificacaoResenhaRequest;
import com.reading.mscadastro.infrastructure.client.response.NotificacaoResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "ms-notificacao", fallback = NotificacaoClientFallBack.class)
public interface NotificacaoClient {

    @RequestMapping(value = "/api/notificacoes/resenha", method = RequestMethod.POST) // NOSONAR
    ResponseEntity<NotificacaoResponse> criar(@RequestBody NotificacaoResenhaRequest request);

    @RequestMapping(value = "/api/notificacoes/livro", method = RequestMethod.POST) // NOSONAR
    ResponseEntity<NotificacaoResponse> criar(@RequestBody NotificacaoLivroRequest request);
}
