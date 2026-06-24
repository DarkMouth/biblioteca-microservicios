package com.biblioteca.mssedes.controller;

import com.biblioteca.mssedes.dto.SedeDTO;
import com.biblioteca.mssedes.model.Sede;
import com.biblioteca.mssedes.service.SedeService;
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

@Tag(name = "Sedes", description = "Gestión de sedes de la Biblioteca Digital")
@RestController
@RequestMapping("/api/sedes")
public class SedeController {

    @Autowired
    private SedeService service;

    @Operation(summary = "Listar todas las sedes",
            description = "Retorna una lista con todas las sedes registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Sede>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener sede por ID",
            description = "Retorna una sede específica según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede encontrada"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtener(
            @Parameter(description = "ID de la sede", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Crear nueva sede",
            description = "Registra una nueva sede en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sede creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Sede> crear(@Valid @RequestBody SedeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar sede",
            description = "Actualiza los datos de una sede existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sede actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizar(
            @Parameter(description = "ID de la sede", example = "1") @PathVariable Long id,
            @Valid @RequestBody SedeDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar sede",
            description = "Elimina una sede del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sede eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sede no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la sede", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}