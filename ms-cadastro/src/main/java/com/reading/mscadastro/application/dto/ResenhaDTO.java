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
public class ResenhaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long usuarioId;

    private String usuarioNome;

    private String titulo;

    private Long livroId;

    private String livroTitulo;
}
