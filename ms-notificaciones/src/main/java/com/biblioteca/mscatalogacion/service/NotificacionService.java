package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.NotificacionDTO;
import com.biblioteca.mscatalogacion.model.Notificacion;
import com.biblioteca.mscatalogacion.model.TipoNotificacion;
import com.biblioteca.mscatalogacion.repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> listar() {
        log.info("Listando todas las notificaciones");
        return repository.findAll();
    }

    public Notificacion obtener(Long id) {
        log.info("Buscando notificacion con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
    }

    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        log.info("Listando notificaciones del usuario id={}", usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    public Notificacion crear(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        n.setUsuarioId(dto.getUsuarioId());
        n.setMensaje(dto.getMensaje());
        n.setTipo(dto.getTipo() != null ? dto.getTipo() : TipoNotificacion.GENERAL);
        n.setLeida(false);
        log.info("Creando notificacion para usuario={}", dto.getUsuarioId());
        return repository.save(n);
    }

    public Notificacion marcarLeida(Long id) {
        Notificacion n = obtener(id);
        if (n.getLeida()) {
            throw new RuntimeException("La notificacion ya fue leida");
        }
        n.setLeida(true);
        log.info("Notificacion id={} marcada como leida", id);
        return repository.save(n);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando notificacion id={}", id);
        repository.deleteById(id);
    }
}