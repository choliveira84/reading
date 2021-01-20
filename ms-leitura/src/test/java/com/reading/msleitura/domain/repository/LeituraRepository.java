package com.reading.msleitura.domain.repository;

import com.reading.msleitura.domain.model.Leitura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Long> {

}
