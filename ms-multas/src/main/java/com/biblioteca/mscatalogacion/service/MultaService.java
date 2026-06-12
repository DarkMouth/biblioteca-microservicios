package com.biblioteca.mscatalogacion.service;

import com.biblioteca.mscatalogacion.dto.MultaDTO;
import com.biblioteca.mscatalogacion.model.EstadoMulta;
import com.biblioteca.mscatalogacion.model.Multa;
import com.biblioteca.mscatalogacion.repository.MultaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MultaService {

    @Autowired
    private MultaRepository repository;

    public List<Multa> listar() {
        log.info("Listando todas las multas");
        return repository.findAll();
    }

    public Multa obtener(Long id) {
        log.info("Buscando multa con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con id: " + id));
    }

    public List<Multa> listarPorUsuario(Long usuarioId) {
        log.info("Listando multas del usuario id={}", usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    // Este endpoint lo usa ms-prestamos via WebClient
    public boolean verificarDeuda(Long usuarioId) {
        boolean tieneDeuda = repository.existsByUsuarioIdAndEstado(
                usuarioId, EstadoMulta.PENDIENTE);
        log.info("Verificando deuda usuario={}: {}", usuarioId, tieneDeuda);
        return tieneDeuda;
    }

    public Multa crear(MultaDTO dto) {
        Multa multa = new Multa();
        multa.setUsuarioId(dto.getUsuarioId());
        multa.setPrestamoId(dto.getPrestamoId());
        multa.setMonto(dto.getMonto());
        multa.setEstado(EstadoMulta.PENDIENTE);
        log.info("Creando multa para usuario id={}", dto.getUsuarioId());
        return repository.save(multa);
    }

    public Multa pagar(Long id) {
        Multa multa = obtener(id);
        if (multa.getEstado() == EstadoMulta.PAGADA) {
            throw new RuntimeException("La multa ya fue pagada");
        }
        multa.setEstado(EstadoMulta.PAGADA);
        log.info("Multa id={} marcada como pagada", id);
        return repository.save(multa);
    }

    public void eliminar(Long id) {
        obtener(id);
        log.warn("Eliminando multa id={}", id);
        repository.deleteById(id);
    }
}