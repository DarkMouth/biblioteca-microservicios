package com.biblioteca.mscatalogacion.repository;

import com.biblioteca.mscatalogacion.model.EstadoMulta;
import com.biblioteca.mscatalogacion.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {
    List<Multa> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndEstado(Long usuarioId, EstadoMulta estado);
}