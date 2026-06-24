package com.biblioteca.msmultas.controller;

import com.biblioteca.msmultas.dto.MultaDTO;
import com.biblioteca.msmultas.model.Multa;
import com.biblioteca.msmultas.service.MultaService;
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

@Tag(name = "Multas", description = "Gestión de multas de la Biblioteca Digital")
@RestController
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService service;

    @Operation(summary = "Listar todas las multas",
            description = "Retorna una lista con todas las multas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Multa>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener multa por ID",
            description = "Retorna una multa específica según su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa encontrada"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Multa> obtener(
            @Parameter(description = "ID de la multa", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @Operation(summary = "Listar multas por usuario",
            description = "Retorna todas las multas asociadas a un usuario específico.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Multa>> listarPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @Operation(summary = "Verificar deuda del usuario",
            description = "Verifica si un usuario tiene multas pendientes. " +
                    "Retorna true si tiene deuda, false si no. " +
                    "Este endpoint es consumido por ms-prestamos para validar préstamos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Verificación exitosa")
    })
    @GetMapping("/verificar/{usuarioId}")
    public ResponseEntity<Boolean> verificarDeuda(
            @Parameter(description = "ID del usuario a verificar", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.verificarDeuda(usuarioId));
    }

    @Operation(summary = "Crear nueva multa",
            description = "Registra una nueva multa para un usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Multa creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Multa> crear(@Valid @RequestBody MultaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @Operation(summary = "Pagar multa",
            description = "Marca una multa como pagada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Multa pagada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La multa ya fue pagada"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    @PutMapping("/{id}/pagar")
    public ResponseEntity<Multa> pagar(
            @Parameter(description = "ID de la multa", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @Operation(summary = "Eliminar multa",
            description = "Elimina una multa del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Multa eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Multa no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la multa", example = "1") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}