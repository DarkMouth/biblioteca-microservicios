package com.biblioteca.msautores.controller;

import com.biblioteca.msautores.dto.AutorDTO;
import com.biblioteca.msautores.model.Autor;
import com.biblioteca.msautores.service.AutorService;
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

@Tag(name = "Autores", description = "Gestión de autores de la Biblioteca Digital")
@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService service;

    @Operation(summary = "Listar todos los autores",
            description = "Retorna una lista con todos los autores registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Autor>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener autor por ID",
            description = "Retorna un autor específico según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtener(
            @Parameter(description = "ID del autor", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Crear nuevo autor",
            description = "Registra un nuevo autor en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Autor> crear(@Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar autor",
            description = "Actualiza los datos de un autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(
            @Parameter(description = "ID del autor", example = "1") @PathVariable Long id,
            @Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar autor",
            description = "Elimina un autor del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del autor", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}