package com.biblioteca.msprestamos.controller;

import com.biblioteca.msprestamos.dto.PrestamoDTO;
import com.biblioteca.msprestamos.model.Prestamo;
import com.biblioteca.msprestamos.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Préstamos", description = "Gestión de préstamos de la Biblioteca Digital")
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService service;

    @Operation(summary = "Listar todos los préstamos",
            description = "Retorna una lista con todos los préstamos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener préstamo por ID",
            description = "Retorna un préstamo específico según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(
            @Parameter(description = "ID del préstamo", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Listar préstamos por usuario",
            description = "Retorna todos los préstamos asociados a un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> listarPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @Operation(summary = "Crear nuevo préstamo",
            description = "Crea un préstamo nuevo. Valida que el usuario no tenga multas pendientes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Préstamo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario tiene multas pendientes o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Prestamo> crear(@Valid @RequestBody PrestamoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Devolver préstamo",
            description = "Marca un préstamo como devuelto y registra la fecha de devolución.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto exitosamente"),
            @ApiResponse(responseCode = "400", description = "El préstamo ya fue devuelto"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolver(
            @Parameter(description = "ID del préstamo", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.devolver(id));
    }

    @Operation(summary = "Eliminar préstamo",
            description = "Elimina un préstamo del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Préstamo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del préstamo", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}