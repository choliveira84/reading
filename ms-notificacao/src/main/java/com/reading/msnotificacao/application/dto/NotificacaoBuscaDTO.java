package com.reading.msnotificacao.application.dto;

import java.util.List;

import com.reading.msnotificacao.domain.model.Notificacao.TiposNotificacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <code>GET</code> Classe para obter uma notificação baseada nas variáveis, que
 * será utilizadas na condição <code>IN</code>.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotificacaoBuscaDTO {

    private Long idUsuario;

    private List<TiposNotificacao> tipo;

    private List<Long> idResenha;

    private List<Long> idLivro;

}
