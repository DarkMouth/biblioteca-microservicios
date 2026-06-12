package com.biblioteca.mscatalogacion.controller;

import com.biblioteca.mscatalogacion.dto.CatalogacionDTO;
import com.biblioteca.mscatalogacion.model.Catalogacion;
import com.biblioteca.mscatalogacion.service.CatalogacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogacion")
public class CatalogacionController {

    @Autowired
    private CatalogacionService service;

    @GetMapping
    public ResponseEntity<List<Catalogacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Catalogacion> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Catalogacion>> listarPorLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(service.listarPorLibro(libroId));
    }

    @PostMapping
    public ResponseEntity<Catalogacion> crear(@Valid @RequestBody CatalogacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Catalogacion> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody CatalogacionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}