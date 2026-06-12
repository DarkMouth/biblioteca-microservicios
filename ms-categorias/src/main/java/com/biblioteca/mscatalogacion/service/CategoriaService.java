package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.CategoriaDTO;
import com.biblioteca.mscatalogacion.model.Categoria;
import com.biblioteca.mscatalogacion.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listar() {
        log.info("Listando todas las categorias");
        return repository.findAll();
    }

    public Categoria obtener(Long id) {
        log.info("Buscando categoria con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + id));
    }

    public Categoria crear(CategoriaDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            log.warn("Categoria duplicada: {}", dto.getNombre());
            throw new RuntimeException("Ya existe una categoria con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        log.info("Creando categoria: {}", dto.getNombre());
        return repository.save(categoria);
    }

    public Categoria actualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = obtener(id);
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        log.info("Actualizando categoria id={}", id);
        return repository.save(categoria);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando categoria id={}", id);
        repository.deleteById(id);
    }
}