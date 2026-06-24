package com.biblioteca.msmultas.controller;

import com.biblioteca.msmultas.dto.MultaDTO;
import com.biblioteca.msmultas.model.Multa;
import com.biblioteca.msmultas.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService service;

    @GetMapping
    public ResponseEntity<List<Multa>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multa> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Multa>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    // Endpoint que consume ms-prestamos via WebClient
    @GetMapping("/verificar/{usuarioId}")
    public ResponseEntity<Boolean> verificarDeuda(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.verificarDeuda(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Multa> crear(@Valid @RequestBody MultaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Multa> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}