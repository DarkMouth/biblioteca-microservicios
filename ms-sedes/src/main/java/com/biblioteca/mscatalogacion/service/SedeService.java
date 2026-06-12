package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.SedeDTO;
import com.biblioteca.mscatalogacion.model.Sede;
import com.biblioteca.mscatalogacion.repository.SedeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SedeService {

    @Autowired
    private SedeRepository repository;

    public List<Sede> listar() {
        log.info("Listando todas las sedes");
        return repository.findAll();
    }

    public Sede obtener(Long id) {
        log.info("Buscando sede con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con id: " + id));
    }

    public Sede crear(SedeDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            log.warn("Sede duplicada: {}", dto.getNombre());
            throw new RuntimeException("Ya existe una sede con ese nombre");
        }
        Sede sede = new Sede();
        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setTelefono(dto.getTelefono());
        sede.setActiva(true);
        log.info("Creando sede: {}", dto.getNombre());
        return repository.save(sede);
    }

    public Sede actualizar(Long id, SedeDTO dto) {
        Sede sede = obtener(id);
        sede.setNombre(dto.getNombre());
        sede.setDireccion(dto.getDireccion());
        sede.setTelefono(dto.getTelefono());
        log.info("Actualizando sede id={}", id);
        return repository.save(sede);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando sede id={}", id);
        repository.deleteById(id);
    }
}