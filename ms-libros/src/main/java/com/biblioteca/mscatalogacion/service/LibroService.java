package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.LibroDTO;
import com.biblioteca.mscatalogacion.model.Libro;
import com.biblioteca.mscatalogacion.repository.LibroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LibroService {

    @Autowired
    private LibroRepository repository;

    public List<Libro> listar() {
        log.info("Listando todos los libros");
        return repository.findAll();
    }

    public Libro obtener(Long id) {
        log.info("Buscando libro con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));
    }

    public Libro crear(LibroDTO dto) {
        if (repository.existsByIsbn(dto.getIsbn())) {
            log.warn("ISBN duplicado: {}", dto.getIsbn());
            throw new RuntimeException("Ya existe un libro con ese ISBN");
        }
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setStock(dto.getStock());
        libro.setCategoria(dto.getCategoria());
        log.info("Creando libro: {}", dto.getTitulo());
        return repository.save(libro);
    }

    public Libro actualizar(Long id, LibroDTO dto) {
        Libro libro = obtener(id);
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setStock(dto.getStock());
        libro.setCategoria(dto.getCategoria());
        log.info("Actualizando libro id={}", id);
        return repository.save(libro);
    }

    public void descontarStock(Long id) {
        Libro libro = obtener(id);
        if (libro.getStock() <= 0) {
            throw new RuntimeException("Sin stock disponible para libro id: " + id);
        }
        libro.setStock(libro.getStock() - 1);
        log.info("Stock descontado para libro id={}", id);
        repository.save(libro);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando libro id={}", id);
        repository.deleteById(id);
    }
}