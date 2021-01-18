package com.reading.mscadastro.domain.repository;

import com.reading.mscadastro.domain.model.Livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

    boolean existsByIsbn(String isbn);

}
