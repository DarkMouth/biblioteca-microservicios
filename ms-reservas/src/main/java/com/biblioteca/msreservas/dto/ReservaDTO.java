package com.biblioteca.msreservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaDTO {

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotNull(message = "El libroId es requerido")
    private Long libroId;
}