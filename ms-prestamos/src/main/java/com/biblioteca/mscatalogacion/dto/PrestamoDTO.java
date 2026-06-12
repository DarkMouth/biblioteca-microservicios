package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrestamoDTO {

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotNull(message = "El libroId es requerido")
    private Long libroId;
}