package com.reading.msleitura.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.reading.msleitura.infrastructure.exceptions.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Leitura implements Serializable {

        private static final String LIVRO_SENDO_LIDO = "O livro já está sendo lido";

        private static final String LIVRO_FINALIZADO = "O livro já está finalizado";

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
        private Long idUsuario;

        @NotEmpty
        @Column(nullable = false)
        private String nomeUsuario;

        @NotNull
        @Positive
        @Column(nullable = false)
        private Long idLivro;

        @NotEmpty
        @Column(nullable = false)
        private String tituloLivro;

        @PastOrPresent
        private LocalDate dataInicio;

        private LocalDate dataFim;

        @Positive
        private Long paginaAtual;

        private NotasLivro nota;

        private StatusLeitura status;

        public Leitura(Long defaultIdUsuario, String defaultNomeUsuario, Long defaultIdLivro,
                        String defaultTituloLivro) {
                this.idUsuario = defaultIdUsuario;
                this.nomeUsuario = defaultNomeUsuario;
                this.idLivro = defaultIdLivro;
                this.tituloLivro = defaultTituloLivro;
        }

        public enum NotasLivro {
                UM, DOIS, TRES, QUATRO, CINCO
        }

        public enum StatusLeitura {
                LIDO, LENDO, A_LER
        }

        public void incluirNaListaDeLeitura() {
                if (this.status != null && this.status == StatusLeitura.A_LER) {
                        throw new BadRequestException("O livro já está adicionado à lista de leitura");
                }

                this.status = StatusLeitura.A_LER;
        }

        public void iniciarLeitura() {
                if (this.status != null && this.status == StatusLeitura.LENDO) {
                        throw new BadRequestException(LIVRO_SENDO_LIDO);
                }
                if (this.dataInicio != null) {
                        throw new BadRequestException(LIVRO_SENDO_LIDO);
                }

                this.status = StatusLeitura.LENDO;
                this.dataInicio = LocalDate.now();
        }

        public void finalizarLeitura() {
                if (this.status != null && this.status == StatusLeitura.LIDO) {
                        throw new BadRequestException(LIVRO_FINALIZADO);
                }

                if (this.nota == null) {
                        throw new BadRequestException("Um livro não pode ser finalizado sem uma nota");
                }

                this.status = StatusLeitura.LIDO;
                this.dataFim = LocalDate.now();
        }

        public void atualizarNota(NotasLivro nota) {
                this.nota = nota;
        }

        public void atualizarPagina(Long pagina) {
                if (this.status != null && this.status == StatusLeitura.LIDO) {
                        throw new BadRequestException(LIVRO_FINALIZADO);
                }

                this.paginaAtual = pagina;
        }
}
