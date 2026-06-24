package com.biblioteca.msmultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "multas")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotNull(message = "El prestamoId es requerido")
    private Long prestamoId;

    @Min(value = 0, message = "El monto no puede ser negativo")
    private Double monto;

    @Enumerated(EnumType.STRING)
    private EstadoMulta estado = EstadoMulta.PENDIENTE;
}