package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SedeDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "La dirección es requerida")
    private String direccion;

    private String telefono;
}