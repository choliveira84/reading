package com.reading.mscadastro.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro")
public class Livro implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL(message = "A URL da capa do livro está inválida")
    @NotEmpty(message = "A URL da capa está inválida")
    @Column(nullable = false)
    private String capa;

    @NotEmpty(message = "O título está inválida")
    @Column(nullable = false)
    private String titulo;

    @NotEmpty(message = "O isbn está inválido")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotEmpty(message = "O nome do autor está inválida")
    @Column(nullable = false)
    private String autor;

    @PositiveOrZero(message = "O número de páginas está inválido")
    @Column(nullable = false)
    private Long numeroPagina;

    @PositiveOrZero(message = "O ano de lançamento está inválido")
    @Column(nullable = false)
    private Integer anoLancamento;

}
