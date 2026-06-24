package com.biblioteca.mslibros.controller;

import com.biblioteca.mslibros.dto.LibroDTO;
import com.biblioteca.mslibros.model.Libro;
import com.biblioteca.mslibros.service.LibroService;
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

@Tag(name = "Libros", description = "Gestión de libros de la Biblioteca Digital")
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @Operation(summary = "Listar todos los libros",
            description = "Retorna una lista con todos los libros registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener libro por ID",
            description = "Retorna un libro específico según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(
            @Parameter(description = "ID del libro", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Crear nuevo libro",
            description = "Registra un nuevo libro en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody LibroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Actualizar libro",
            description = "Actualiza los datos de un libro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(
            @Parameter(description = "ID del libro", example = "1") @PathVariable Long id,
            @Valid @RequestBody LibroDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Descontar stock del libro",
            description = "Reduce en 1 el stock disponible de un libro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("/{id}/descontar-stock")
    public ResponseEntity<Void> descontarStock(
            @Parameter(description = "ID del libro", example = "1") @PathVariable Long id) {
        service.descontarStock(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar libro",
            description = "Elimina un libro del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del libro", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}