package com.biblioteca.mscatalogacion.repository;

import com.biblioteca.mscatalogacion.model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
    boolean existsByNombre(String nombre);
}