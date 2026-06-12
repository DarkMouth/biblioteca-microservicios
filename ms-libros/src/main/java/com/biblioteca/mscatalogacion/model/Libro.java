package com.biblioteca.mscatalogacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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