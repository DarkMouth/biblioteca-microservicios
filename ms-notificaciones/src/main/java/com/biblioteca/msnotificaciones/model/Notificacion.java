package com.biblioteca.msnotificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotBlank(message = "El mensaje es requerido")
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo = TipoNotificacion.GENERAL;

    private Boolean leida = false;
    private LocalDateTime fecha = LocalDateTime.now();
}