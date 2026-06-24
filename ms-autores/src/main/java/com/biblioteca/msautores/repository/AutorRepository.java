package com.biblioteca.msautores.repository;

import com.biblioteca.msautores.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNombre(String nombre);
}