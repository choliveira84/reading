package com.reading.msleitura.application.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.reading.msleitura.domain.model.Leitura.NotasLivro;
import com.reading.msleitura.domain.model.Leitura.StatusLeitura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO utilizado para chamadas </code>POST</code>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeituraRequest {

    @NotNull
    @Positive
    private Long idUsuario;

    @NotEmpty
    private String nomeUsuario;

    @NotNull
    @Positive
    private Long idLivro;

    @NotEmpty
    private String tituloLivro;

    @Positive
    private Long paginaAtual;

    private NotasLivro nota;

    private StatusLeitura status;

    public LeituraRequest(Long defaultIdUsuario, String defaultNomeUsuario, Long defaultIdLivro,
            String defaultTituloLivro) {
        this.idUsuario = defaultIdUsuario;
        this.nomeUsuario = defaultNomeUsuario;
        this.idLivro = defaultIdLivro;
        this.tituloLivro = defaultTituloLivro;
    }
}
