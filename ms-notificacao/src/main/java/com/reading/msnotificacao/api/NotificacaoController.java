package com.reading.msnotificacao.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.reading.msnotificacao.application.dto.NotificacaoBuscaDTO;
import com.reading.msnotificacao.application.dto.NotificacaoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoLivroPublicadoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoResenhaDTO;
import com.reading.msnotificacao.domain.service.NotificacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

        @Autowired
        private NotificacaoService service;

        @ApiResponses(value = {
                        @ApiResponse(code = 201, message = "Response com a notificação criada para o tipo Resenha Publicada"),
                        @ApiResponse(code = 404, message = "Livro ou Autor não encontrato") })
        @PostMapping("/resenha")
        public ResponseEntity<NotificacaoDTO> criar(@Valid @RequestBody NotificacaoResenhaDTO request)
                        throws URISyntaxException {
                NotificacaoDTO notificacao = service.criar(request);

                HttpHeaders headers = new HttpHeaders();
                headers.add("X-msnotificacao-created", "Notificação criada.");
                return ResponseEntity.created(new URI("/api/notificacoes/" + notificacao.getId())).headers(headers)
                                .body(notificacao);
        }

        @ApiResponses(value = {
                        @ApiResponse(code = 201, message = "Response com a notificação criada para o tipo Livro Publicado"),
                        @ApiResponse(code = 404, message = "Livro não encontrato") })
        @PostMapping("/livro")
        public ResponseEntity<NotificacaoDTO> criar(@Valid @RequestBody NotificacaoLivroPublicadoDTO request)
                        throws URISyntaxException {
                NotificacaoDTO notificacao = service.criar(request);

                HttpHeaders headers = new HttpHeaders();
                headers.add("X-msnotificacao-created", "Notificação criada.");
                return ResponseEntity.created(new URI("/api/notificacoes/" + notificacao.getId())).headers(headers)
                                .body(notificacao);
        }

        @ApiResponses(value = { @ApiResponse(code = 200, message = "Response com a notificação") })
        @GetMapping
        public ResponseEntity<List<NotificacaoDTO>> buscar(NotificacaoBuscaDTO request, Pageable pageable) {
                Page<NotificacaoDTO> page = service.buscar(request, pageable);

                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

                return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

        @ApiResponses(value = { @ApiResponse(code = 204, message = "Sem conteúdo"),
                        @ApiResponse(code = 404, message = "Notificação não encontrada") })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
                service.deletar(id);

                HttpHeaders headers = new HttpHeaders();
                headers.add("X-msnotificacao-deleted", "Notificação deletada.");
                return ResponseEntity.noContent().headers(headers).build();
        }

        @ApiResponses(value = { @ApiResponse(code = 200, message = "Response com a notificação"),
                        @ApiResponse(code = 404, message = "Notificação não encontrada") })
        @GetMapping("/{id}")
        public ResponseEntity<NotificacaoDTO> consultar(@PathVariable Long id) {
                NotificacaoDTO notificacao = service.consultar(id);

                return ResponseEntity.ok().body(notificacao);
        }

}
