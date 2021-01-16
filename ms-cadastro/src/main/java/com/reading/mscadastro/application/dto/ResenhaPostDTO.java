package com.reading.mscadastro.application.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class ResenhaPostDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "A descrição está inválida")
    private String descricao;

    @NotEmpty(message = "O título está inválido")
    private String titulo;

    @NotNull(message = "O id do usuário está inválido")
    private Long usuarioId;

    @NotNull(message = "O id do livro está inválido")
    private Long livroId;
}
