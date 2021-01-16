package com.reading.mscadastro.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenha")
public class Resenha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "A descrição está inválida")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotEmpty(message = "O título está inválido")
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(insertable = true, nullable = false, updatable = false, name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "resenhas", allowSetters = true)
    private Usuario usuario;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(insertable = true, nullable = false, updatable = false, name = "livro_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = "resenhas", allowSetters = true)
    private Livro livro;
}
