package com.biblioteca.msreservas.service;

import com.biblioteca.msreservas.dto.ReservaDTO;
import com.biblioteca.msreservas.model.EstadoReserva;
import com.biblioteca.msreservas.model.Reserva;
import com.biblioteca.msreservas.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> listar() {
        log.info("Listando todas las reservas");
        return repository.findAll();
    }

    public Reserva obtener(Long id) {
        log.info("Buscando reserva con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    public List<Reserva> listarPorUsuario(Long usuarioId) {
        log.info("Listando reservas del usuario id={}", usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    public Reserva crear(ReservaDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setLibroId(dto.getLibroId());
        reserva.setEstado(EstadoReserva.PENDIENTE);
        log.info("Creando reserva para usuario={}", dto.getUsuarioId());
        return repository.save(reserva);
    }

    public Reserva cancelar(Long id) {
        Reserva reserva = obtener(id);
        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new RuntimeException("La reserva ya fue cancelada");
        }
        reserva.setEstado(EstadoReserva.CANCELADA);
        log.info("Reserva id={} cancelada", id);
        return repository.save(reserva);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando reserva id={}", id);
        repository.deleteById(id);
    }
}