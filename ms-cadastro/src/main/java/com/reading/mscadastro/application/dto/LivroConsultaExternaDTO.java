package com.reading.mscadastro.application.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Utilizado como response para o usuário quando for buscado externamente
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LivroConsultaExternaDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String capa;

    private String titulo;

    private String autor;

    private String isbn;

    private Long numeroPagina;

    private String anoLancamento;

}
