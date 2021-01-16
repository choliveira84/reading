package com.reading.mscadastro.domain.repository;

import com.reading.mscadastro.domain.model.Resenha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenhaRepository extends JpaRepository<Resenha, Long> {

}
