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
public class NotificacaoLivroRequest {

    private String tituloLivro;

    private Long idLivro;
}
