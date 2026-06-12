package com.biblioteca.mscatalogacion.repository;

import com.biblioteca.mscatalogacion.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNombre(String nombre);
}