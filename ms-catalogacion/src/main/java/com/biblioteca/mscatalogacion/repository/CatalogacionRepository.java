package com.biblioteca.mscatalogacion.repository;

import com.biblioteca.mscatalogacion.model.Catalogacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogacionRepository extends JpaRepository<Catalogacion, Long> {
    List<Catalogacion> findByLibroId(Long libroId);
    boolean existsByCodigoCatalogacion(String codigo);
}