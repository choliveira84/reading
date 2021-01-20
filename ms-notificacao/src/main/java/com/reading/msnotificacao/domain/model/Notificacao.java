package com.reading.msnotificacao.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Notificacao implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Long idLivro;

    private String tituloLivro;

    @NotNull
    @Column(nullable = false)
    private TiposNotificacao tipo;

    @Positive
    private Long idUsuario;

    private String nomeUsuario;

    @Positive
    private Long idResenha;

    private String tituloResenha;

    public Notificacao(String tituloLivro, Long idLivro) {
        this.tituloLivro = tituloLivro;
        this.idLivro = idLivro;
        this.tipo = TiposNotificacao.LIVRO_PUBLICADO;
    }

    public Notificacao(String tituloResenha, Long idLivro, Long idResenha, Long idUsuario, String nomeUsuario) {
        this.tituloResenha = tituloResenha;
        this.idLivro = idLivro;
        this.tipo = TiposNotificacao.RESENHA_PUBLICADA;
        this.idResenha = idResenha;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public enum TiposNotificacao {
        RESENHA_PUBLICADA, LIVRO_PUBLICADO
    }
}