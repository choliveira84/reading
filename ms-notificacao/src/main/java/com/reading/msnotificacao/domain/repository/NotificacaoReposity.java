package com.reading.msnotificacao.domain.repository;

import com.reading.msnotificacao.domain.model.Notificacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoReposity extends JpaRepository<Notificacao, Long>, JpaSpecificationExecutor<Notificacao> {

}
