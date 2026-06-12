package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CatalogacionDTO {

    @NotNull(message = "El libroId es requerido")
    private Long libroId;

    @NotNull(message = "El autorId es requerido")
    private Long autorId;

    @NotNull(message = "El categoriaId es requerido")
    private Long categoriaId;

    @NotBlank(message = "El codigo de catalogacion es requerido")
    private String codigoCatalogacion;

    private String observaciones;
}