package com.biblioteca.mscatalogacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotNull(message = "El libroId es requerido")
    private Long libroId;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

    private LocalDate fechaInicio = LocalDate.now();
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucion;
}