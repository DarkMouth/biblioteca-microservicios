package com.biblioteca.mscatalogacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LibroDTO {

    @NotBlank(message = "El título es requerido")
    private String titulo;

    @NotBlank(message = "El autor es requerido")
    private String autor;

    @NotBlank(message = "El ISBN es requerido")
    private String isbn;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private String categoria;
}