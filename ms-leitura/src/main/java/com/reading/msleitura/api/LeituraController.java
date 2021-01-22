package com.reading.msleitura.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.reading.msleitura.application.dto.LeituraRequest;
import com.reading.msleitura.application.dto.LeituraResponse;
import com.reading.msleitura.domain.model.Leitura.NotasLivro;
import com.reading.msleitura.domain.services.LeituraService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/leituras")
public class LeituraController {

    private final Logger log = LoggerFactory.getLogger(LeituraController.class);

    @Autowired
    private LeituraService service;

    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "O DTO da leitura criado"), //
            @ApiResponse(code = 400, message = "Dados inválidos"), //
            @ApiResponse(code = 422, message = "Validação de dados"), //
    })
    @PostMapping
    public ResponseEntity<LeituraResponse> criar(@Valid @RequestBody LeituraRequest dto) throws URISyntaxException {
        log.debug("Requisição para criar uma leitura a partir do DTO {}", dto);

        LeituraResponse leituraSalva = service.criar(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-msleitura-created", "Leitura criada.");

        return ResponseEntity.created(new URI("/api/leituras/" + leituraSalva.getId())).headers(headers)
                .body(leituraSalva);
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Paginação do resultado") })
    @GetMapping
    public ResponseEntity<List<LeituraResponse>> buscar(Pageable pageable) {
        log.debug("Requisição para buscar a leitura a partir da página {}", pageable);

        Page<LeituraResponse> page = service.buscar(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "DTO com o livro"),
            @ApiResponse(code = 404, message = "Livro não encontrato") })
    @GetMapping("/{id}")
    public ResponseEntity<LeituraResponse> buscar(@PathVariable Long id) {
        log.debug("Requisição para buscar a leitura partir do id: {}", id);

        return ResponseEntity.ok().body(service.buscar(id));
    }

    @PutMapping("/atualizar-nota/{idLivro}/livro/{nota}/nota")
    public ResponseEntity<Void> atualizarNota(@PathVariable Long idLivro, @PathVariable NotasLivro nota) {
        log.debug("Requisição para atualizar a nota do livro {}", idLivro);

        service.atualizarNota(idLivro, nota);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/iniciar-leitura/{idLivro}/livro")
    public ResponseEntity<Void> iniciarLeitura(@PathVariable Long idLivro) {
        log.debug("Requisição para iniciar a leitura do livro {}", idLivro);

        service.iniciarLeitura(idLivro);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/finalizar-leitura/{idLivro}/livro")
    public ResponseEntity<Void> finalizarLeitura(@PathVariable Long idLivro) {
        log.debug("Requisição para finalizar a leitura do livro {}", idLivro);

        service.finalizarLeitura(idLivro);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/incluir/{idLivro}/livro")
    public ResponseEntity<Void> incluirNaLista(@PathVariable Long idLivro) {
        log.debug("Requisição para incluir o livro {} na lista", idLivro);

        service.incluirNaListaParaLer(idLivro);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar-pagina/{idLivro}/livro/{pagina}/pagina")
    public ResponseEntity<Void> atualizarPagina(@PathVariable Long idLivro, @PathVariable Long pagina) {
        log.debug("Requisição para atualizar a página do livro {}", idLivro);

        service.atualizarPagina(idLivro, pagina);

        return ResponseEntity.noContent().build();
    }
}
