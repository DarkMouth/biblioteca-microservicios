package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    private String descripcion;
}