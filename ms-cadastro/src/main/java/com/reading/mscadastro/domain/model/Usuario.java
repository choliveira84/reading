package com.reading.mscadastro.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome está inválido")
    @Column(nullable = false)
    private String nome;

    @NotEmpty(message = "A cidade está inválida")
    @Column(nullable = false)
    private String cidade;

    @Email(message = "O e-mail está inválido")
    @Column(nullable = false)
    private String email;

    @NotEmpty(message = "O Pais está inválida")
    @Column(nullable = false)
    private String pais;

    @URL(message = "A URL da foto do livro está inválida")
    @NotEmpty(message = "A URL da foto está inválida")
    @Column(nullable = false)
    private String foto;
}
