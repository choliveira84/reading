package com.reading.mslivro.application.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LivroPostDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @URL(message = "A URL da capa está inválida")
    @NotEmpty(message = "A URL data capa está inválida")
    private String capa;

    @NotEmpty(message = "O título está inválido")
    private String titulo;

    @NotEmpty(message = "O nome do autor está inválido")
    private String autor;

    @NotEmpty(message = "O isbn está inválido")
    private String isbn;

    @PositiveOrZero(message = "O número de páginas está inválido")
    private Long numeroPagina;

    @PositiveOrZero(message = "O ano de lançamento está inválido")
    private Integer anoLancamento;

}
