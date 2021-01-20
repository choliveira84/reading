package com.reading.mscadastro.infrastructure.events;

import com.reading.mscadastro.application.dto.LivroDTO;
import com.reading.mscadastro.application.dto.ResenhaDTO;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

public class CustomEvent extends ApplicationEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private LivroDTO livro;

    @Getter
    private ResenhaDTO resenha;

    @Getter
    private TiposNotificacao tipo;

    public CustomEvent(LivroDTO livro) {
        super(livro);
        this.livro = livro;
        this.tipo = TiposNotificacao.LIVRO;
    }

    public CustomEvent(ResenhaDTO resenha) {
        super(resenha);
        this.resenha = resenha;
        this.tipo = TiposNotificacao.RESENHA;
    }

    public enum TiposNotificacao {
        LIVRO, RESENHA
    }

}
