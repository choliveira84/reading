package com.reading.msnotificacao.application.dto;

import java.io.Serializable;

import com.reading.msnotificacao.domain.model.Notificacao.TiposNotificacao;

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
public class NotificacaoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private TiposNotificacao tipo;

    private Long idLivro;

    private String tituloLivro;

    private Long idResenha;

    private Long idUsuario;

    private String nomeUsuario;

    private String tituloResenha;

}
