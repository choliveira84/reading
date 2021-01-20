package com.reading.msnotificacao.application.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <code>POST</code> Classe para criar uma notificação de uma resenha publicada.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotificacaoResenhaDTO {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long usuarioId;

    @NotEmpty
    private String usuarioNome;

    @NotEmpty
    private String titulo;

    @NotNull
    @Positive
    private Long livroId;

    @NotNull
    private String livroTitulo;
}
