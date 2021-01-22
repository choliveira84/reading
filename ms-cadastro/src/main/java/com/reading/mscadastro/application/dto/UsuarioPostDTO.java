package com.reading.mscadastro.application.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
public class UsuarioPostDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "O nome está inválido")
    private String nome;

    @NotEmpty(message = "A cidade está inválida")
    private String cidade;

    @Email(message = "O e-mail está inválido")
    private String email;

    @NotEmpty(message = "O Pais está inválida")
    private String pais;

    @URL(message = "A URL da foto do livro está inválida")
    @NotEmpty(message = "A URL da foto está inválida")
    private String foto;
}
