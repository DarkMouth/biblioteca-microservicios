package com.biblioteca.msautores.service;

import com.biblioteca.msautores.dto.AutorDTO;
import com.biblioteca.msautores.model.Autor;
import com.biblioteca.msautores.repository.AutorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AutorService {

    @Autowired
    private AutorRepository repository;

    public List<Autor> listar() {
        log.info("Listando todos los autores");
        return repository.findAll();
    }

    public Autor obtener(Long id) {
        log.info("Buscando autor con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con id: " + id));
    }

    public Autor crear(AutorDTO dto) {
        if (repository.existsByNombre(dto.getNombre())) {
            log.warn("Autor duplicado: {}", dto.getNombre());
            throw new RuntimeException("Ya existe un autor con ese nombre");
        }
        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        log.info("Creando autor: {}", dto.getNombre());
        return repository.save(autor);
    }

    public Autor actualizar(Long id, AutorDTO dto) {
        Autor autor = obtener(id);
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setFechaNacimiento(dto.getFechaNacimiento());
        log.info("Actualizando autor id={}", id);
        return repository.save(autor);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando autor id={}", id);
        repository.deleteById(id);
    }
}