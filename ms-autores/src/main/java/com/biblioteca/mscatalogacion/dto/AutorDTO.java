package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AutorDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "La nacionalidad es requerida")
    private String nacionalidad;

    private String fechaNacimiento;
}