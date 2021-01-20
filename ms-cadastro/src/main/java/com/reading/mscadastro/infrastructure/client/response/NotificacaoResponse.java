package com.reading.mscadastro.infrastructure.client.response;

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
public class NotificacaoResponse {

    private Long id;

    private Long idLivro;

    private String tituloLivro;

    private Long idResenha;

    private Long idUsuario;

    private String tituloResenha;
}
