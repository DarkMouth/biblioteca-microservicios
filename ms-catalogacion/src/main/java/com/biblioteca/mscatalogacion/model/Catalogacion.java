package com.biblioteca.mscatalogacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalogacion")
public class Catalogacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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