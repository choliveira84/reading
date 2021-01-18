package com.reading.mscadastro.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.reading.mscadastro.application.dto.ResenhaDTO;
import com.reading.mscadastro.application.dto.ResenhaPostDTO;
import com.reading.mscadastro.domain.services.ResenhaService;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/resenhas")
public class ResenhaController {
    private final Logger log = LoggerFactory.getLogger(ResenhaController.class);

    @Autowired
    private ResenhaService service;

    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "O DTO da resenha criada"), //
            @ApiResponse(code = 400, message = "Dados inválidos"), //
            @ApiResponse(code = 422, message = "Validação de dados"), //
    })
    @PostMapping
    public ResponseEntity<ResenhaDTO> criar(@Valid @RequestBody ResenhaPostDTO dto) throws URISyntaxException {
        log.debug("Requisição para criar uma resenha a partir do DTO {}", dto);

        ResenhaDTO resenhaSalva = service.criar(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-mscadastro-created", "Resenha criada.");

        return ResponseEntity.created(new URI("/api/resenhas/" + resenhaSalva.getId())).headers(headers)
                .body(resenhaSalva);
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Paginação do resultado") })
    @GetMapping
    public ResponseEntity<List<ResenhaDTO>> buscar(Pageable pageable) {
        log.debug("Requisição para buscar a resenha");

        Page<ResenhaDTO> page = service.buscar(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "DTO com a resenha"),
            @ApiResponse(code = 404, message = "Resenha não encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<ResenhaDTO> buscar(@PathVariable Long id) {
        log.debug("Requisição para buscar a resenha a partir do id: {}", id);

        return ResponseEntity.ok().body(service.buscar(id));
    }

}
