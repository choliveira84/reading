package com.reading.msleitura.application.dto;

import java.time.LocalDate;

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
    
    private Long idUsuario;

    private String nomeUsuario;

    private Long idLivro;

    private String tituloLivro;

    private Long paginaAtual;

    private NotasLivro nota;

    private StatusLeitura status;
}
