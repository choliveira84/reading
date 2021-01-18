package com.reading.mscadastro.application.dto;

import java.io.Serializable;

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
public class LivroCriterioDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String titulo;

    private String autor;

    private String isbn;
}
