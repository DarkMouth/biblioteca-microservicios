package com.biblioteca.msusuarios.service;

import com.biblioteca.msusuarios.dto.UsuarioDTO;
import com.biblioteca.msusuarios.model.Usuario;
import com.biblioteca.msusuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        log.info("Listando todos los usuarios");
        return repository.findAll();
    }

    public Usuario obtener(Long id) {
        log.info("Buscando usuario con id={}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario crear(UsuarioDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            log.warn("Intento de registro con email duplicado: {}", dto.getEmail());
            throw new RuntimeException("El email ya está registrado");
        }
        if (repository.existsByRut(dto.getRut())) {
            log.warn("Intento de registro con RUT duplicado: {}", dto.getRut());
            throw new RuntimeException("El RUT ya está registrado");
        }
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setRut(dto.getRut());
        u.setActivo(true);
        log.info("Creando usuario: {}", dto.getNombre());
        return repository.save(u);
    }

    public Usuario actualizar(Long id, UsuarioDTO dto) {
        Usuario u = obtener(id);
        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setRut(dto.getRut());
        log.info("Actualizando usuario id={}", id);
        return repository.save(u);
    }

    public void eliminar(Long id) {
        obtener(id); // valida que exista
        log.warn("Eliminando usuario id={}", id);
        repository.deleteById(id);
    }
}
