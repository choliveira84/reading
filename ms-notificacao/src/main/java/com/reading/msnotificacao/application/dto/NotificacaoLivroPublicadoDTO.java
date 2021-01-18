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
 * <code>POST</code> Classe para criar uma notificação de um livro publicado.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotificacaoLivroPublicadoDTO {

    @NotEmpty
    String tituloLivro;

    @NotNull
    @Positive
    private Long idLivro;
}
