package com.reading.mscadastro.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "livro")
    private Set<Resenha> resenhas = new HashSet<>();

    public Livro(
            @URL(message = "A URL da capa do livro está inválida") @NotEmpty(message = "A URL da capa está inválida") String capa,
            @NotEmpty(message = "O título está inválida") String titulo,
            @NotEmpty(message = "O isbn está inválido") String isbn,
            @NotEmpty(message = "O nome do autor está inválida") String autor,
            @PositiveOrZero(message = "O número de páginas está inválido") Long numeroPagina,
            @PositiveOrZero(message = "O ano de lançamento está inválido") Integer anoLancamento) {
        this.capa = capa;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.numeroPagina = numeroPagina;
        this.anoLancamento = anoLancamento;
    }

    public Livro(Long id,
            @URL(message = "A URL da capa do livro está inválida") @NotEmpty(message = "A URL da capa está inválida") String capa,
            @NotEmpty(message = "O título está inválida") String titulo,
            @NotEmpty(message = "O isbn está inválido") String isbn,
            @NotEmpty(message = "O nome do autor está inválida") String autor,
            @PositiveOrZero(message = "O número de páginas está inválido") Long numeroPagina,
            @PositiveOrZero(message = "O ano de lançamento está inválido") Integer anoLancamento) {
        this(capa, titulo, isbn, autor, numeroPagina, anoLancamento);
        this.id = id;
    }

}
