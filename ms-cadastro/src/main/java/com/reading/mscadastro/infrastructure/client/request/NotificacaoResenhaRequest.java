package com.reading.mscadastro.infrastructure.client.request;

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
public class NotificacaoResenhaRequest {
    
    private Long idUsuario;

    private String tituloResenha;

    private Long idResenha;

    private Long idLivro;
}
