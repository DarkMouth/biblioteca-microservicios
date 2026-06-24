package com.biblioteca.mscatalogacion.controller;

import com.biblioteca.mscatalogacion.dto.CatalogacionDTO;
import com.biblioteca.mscatalogacion.model.Catalogacion;
import com.biblioteca.mscatalogacion.service.CatalogacionService;
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

@Tag(name = "Catalogación", description = "Gestión de catalogación de libros de la Biblioteca Digital")
@RestController
@RequestMapping("/api/catalogacion")
public class CatalogacionController {

    @Autowired
    private CatalogacionService service;

    @Operation(summary = "Listar todas las catalogaciones",
            description = "Retorna una lista con todas las catalogaciones registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Catalogacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener catalogación por ID",
            description = "Retorna una catalogación específica según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catalogación encontrada"),
            @ApiResponse(responseCode = "404", description = "Catalogación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Catalogacion> obtener(
            @Parameter(description = "ID de la catalogación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Listar catalogaciones por libro",
            description = "Retorna todas las catalogaciones asociadas a un libro específico.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Catalogacion>> listarPorLibro(
            @Parameter(description = "ID del libro", example = "1") @PathVariable Long libroId) {
        return ResponseEntity.ok(service.listarPorLibro(libroId));
    }

    @Operation(summary = "Crear nueva catalogación",
            description = "Registra una nueva catalogación para un libro.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Catalogación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Catalogacion> crear(@Valid @RequestBody CatalogacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar catalogación",
            description = "Actualiza los datos de una catalogación existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catalogación actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Catalogación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Catalogacion> actualizar(
            @Parameter(description = "ID de la catalogación", example = "1") @PathVariable Long id,
            @Valid @RequestBody CatalogacionDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar catalogación",
            description = "Elimina una catalogación del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Catalogación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Catalogación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la catalogación", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}