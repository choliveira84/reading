package com.reading.mscadastro.api;

import java.util.List;

import com.reading.mscadastro.application.dto.UsuarioDTO;
import com.reading.mscadastro.domain.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService service;

    @ApiResponses(value = { @ApiResponse(code = 200, message = "DTO com o usuário"),
            @ApiResponse(code = 404, message = "Usuário não encontrato") })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        log.debug("Requisição para buscar o usuário a partir do id: {}", id);

        return ResponseEntity.ok().body(service.buscar(id));
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Paginação do resultado") })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscar(Pageable pageable) {
        log.debug("Requisição para buscar usuários");

        Page<UsuarioDTO> page = service.buscar(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
