package com.biblioteca.mscatalogacion.dto;

import com.biblioteca.mscatalogacion.model.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionDTO {

    @NotNull(message = "El usuarioId es requerido")
    private Long usuarioId;

    @NotBlank(message = "El mensaje es requerido")
    private String mensaje;

    private TipoNotificacion tipo;
}