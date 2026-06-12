package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.CatalogacionDTO;
import com.biblioteca.mscatalogacion.model.Catalogacion;
import com.biblioteca.mscatalogacion.repository.CatalogacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CatalogacionService {

    @Autowired
    private CatalogacionRepository repository;

    public List<Catalogacion> listar() {
        log.info("Listando todas las catalogaciones");
        return repository.findAll();
    }

    public Catalogacion obtener(Long id) {
        log.info("Buscando catalogacion con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catalogacion no encontrada con id: " + id));
    }

    public List<Catalogacion> listarPorLibro(Long libroId) {
        log.info("Listando catalogaciones del libro id={}", libroId);
        return repository.findByLibroId(libroId);
    }

    public Catalogacion crear(CatalogacionDTO dto) {
        if (repository.existsByCodigoCatalogacion(dto.getCodigoCatalogacion())) {
            log.warn("Codigo de catalogacion duplicado: {}", dto.getCodigoCatalogacion());
            throw new RuntimeException("Ya existe una catalogacion con ese codigo");
        }
        Catalogacion c = new Catalogacion();
        c.setLibroId(dto.getLibroId());
        c.setAutorId(dto.getAutorId());
        c.setCategoriaId(dto.getCategoriaId());
        c.setCodigoCatalogacion(dto.getCodigoCatalogacion());
        c.setObservaciones(dto.getObservaciones());
        log.info("Creando catalogacion: {}", dto.getCodigoCatalogacion());
        return repository.save(c);
    }

    public Catalogacion actualizar(Long id, CatalogacionDTO dto) {
        Catalogacion c = obtener(id);
        c.setLibroId(dto.getLibroId());
        c.setAutorId(dto.getAutorId());
        c.setCategoriaId(dto.getCategoriaId());
        c.setCodigoCatalogacion(dto.getCodigoCatalogacion());
        c.setObservaciones(dto.getObservaciones());
        log.info("Actualizando catalogacion id={}", id);
        return repository.save(c);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando catalogacion id={}", id);
        repository.deleteById(id);
    }
}