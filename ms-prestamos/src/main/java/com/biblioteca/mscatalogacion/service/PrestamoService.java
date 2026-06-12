package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.PrestamoDTO;
import com.biblioteca.mscatalogacion.model.EstadoPrestamo;
import com.biblioteca.mscatalogacion.model.Prestamo;
import com.biblioteca.mscatalogacion.repository.PrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PrestamoService {

    @Autowired
    private PrestamoRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Prestamo> listar() {
        log.info("Listando todos los prestamos");
        return repository.findAll();
    }

    public Prestamo obtener(Long id) {
        log.info("Buscando prestamo con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));
    }

    public List<Prestamo> listarPorUsuario(Long usuarioId) {
        log.info("Listando prestamos del usuario id={}", usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    public Prestamo crear(PrestamoDTO dto) {
        log.info("Iniciando proceso de prestamo para usuario={}", dto.getUsuarioId());

        // Comunicación con ms-multas via WebClient
        Boolean tieneMulta = webClientBuilder.build()
                .get()
                .uri("http://localhost:8087/api/multas/verificar/" + dto.getUsuarioId())
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false)
                .block();

        if (Boolean.TRUE.equals(tieneMulta)) {
            log.warn("Usuario {} tiene multas pendientes", dto.getUsuarioId());
            throw new RuntimeException(
                    "No se puede crear el prestamo: el usuario tiene multas pendientes");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(dto.getUsuarioId());
        prestamo.setLibroId(dto.getLibroId());
        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaVencimiento(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        log.info("Prestamo creado exitosamente para usuario={}", dto.getUsuarioId());
        return repository.save(prestamo);
    }

    public Prestamo devolver(Long id) {
        Prestamo prestamo = obtener(id);
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new RuntimeException("El prestamo ya fue devuelto");
        }
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDate.now());
        log.info("Prestamo id={} devuelto", id);
        return repository.save(prestamo);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando prestamo id={}", id);
        repository.deleteById(id);
    }
}