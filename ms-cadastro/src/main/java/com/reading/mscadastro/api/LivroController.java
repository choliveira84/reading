package com.reading.mscadastro.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.reading.mscadastro.application.dto.LivroConsultaExternaDTO;
import com.reading.mscadastro.application.dto.LivroCriterioDTO;
import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.LivroPostDTO;
import com.reading.mscadastro.domain.services.LivroService;

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
@RequestMapping("/api/livros")
public class LivroController {

    private final Logger log = LoggerFactory.getLogger(LivroController.class);

    @Autowired
    private LivroService service;

    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "O DTO do livro criado"), //
            @ApiResponse(code = 409, message = "ISBN Já existente"), //
            @ApiResponse(code = 400, message = "Dados inválidos"), //
            @ApiResponse(code = 422, message = "Validação de dados"), //
    })
    @PostMapping
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroPostDTO dto) throws URISyntaxException {
        log.debug("Requisição para criar um livro a partir do DTO {}", dto);

        LivroDTO livroSalvo = service.criar(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-mscadastro-created", "Livro criado.");

        return ResponseEntity.created(new URI("/api/livros/" + livroSalvo.getId())).headers(headers).body(livroSalvo);
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Paginação do resultado") })
    @GetMapping
    public ResponseEntity<List<LivroDTO>> buscar(LivroCriterioDTO dto, Pageable pageable) {
        log.debug("Requisição para buscar o livro a partir do criterio: {}, página {}", dto, pageable);

        Page<LivroDTO> page = service.buscar(dto, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista contendo livros de acordo com o critério passado pelo usuário") })
    @GetMapping("/consulta-externa/{criterio}")
    public ResponseEntity<List<LivroConsultaExternaDTO>> buscar(@PathVariable String criterio) {
        log.debug("Requisição para buscar livros a partir do critério {}", criterio);

        return ResponseEntity.ok().body(service.buscar(criterio));
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "DTO com o livro"),
            @ApiResponse(code = 404, message = "Livro não encontrato") })
    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscar(@PathVariable Long id) {
        log.debug("Requisição para buscar o livro a partir do id: {}", id);

        return ResponseEntity.ok().body(service.buscar(id));
    }

}
