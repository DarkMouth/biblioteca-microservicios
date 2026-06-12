package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MultaDTO {

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotNull(message = "El prestamoId es requerido")
    private Long prestamoId;

    @Min(value = 0, message = "El monto no puede ser negativo")
    private Double monto;
}