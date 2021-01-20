package com.reading.msnotificacao.application.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

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

    @NotNull
    @Positive
    private Long id;

    @URL
    private String capa;

    @NotEmpty
    private String titulo;

    @NotEmpty
    private String autor;

    @NotEmpty
    private String isbn;

    @PositiveOrZero
    private Long numeroPagina;

    @NotEmpty
    private String anoLancamento;
}
