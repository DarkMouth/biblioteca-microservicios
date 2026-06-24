package com.biblioteca.msprestamos.service;

import com.biblioteca.msprestamos.dto.PrestamoDTO;
import com.biblioteca.msprestamos.model.EstadoPrestamo;
import com.biblioteca.msprestamos.model.Prestamo;
import com.biblioteca.msprestamos.repository.PrestamoRepository;
import com.biblioteca.msprestamos.service.PrestamoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PrestamoService")
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository repository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PrestamoService prestamoService;

    private Prestamo prestamoActivo;
    private Prestamo prestamoDevuelto;
    private PrestamoDTO prestamoDTO;

    // =========================================================
    // SETUP: datos reutilizables en cada prueba
    // =========================================================
    @BeforeEach
    void setUp() {
        prestamoActivo = new Prestamo();
        prestamoActivo.setId(1L);
        prestamoActivo.setUsuarioId(10L);
        prestamoActivo.setLibroId(20L);
        prestamoActivo.setEstado(EstadoPrestamo.ACTIVO);
        prestamoActivo.setFechaInicio(LocalDate.now());
        prestamoActivo.setFechaVencimiento(LocalDate.now().plusDays(14));

        prestamoDevuelto = new Prestamo();
        prestamoDevuelto.setId(2L);
        prestamoDevuelto.setUsuarioId(10L);
        prestamoDevuelto.setLibroId(30L);
        prestamoDevuelto.setEstado(EstadoPrestamo.DEVUELTO);
        prestamoDevuelto.setFechaInicio(LocalDate.now().minusDays(10));
        prestamoDevuelto.setFechaVencimiento(LocalDate.now().plusDays(4));
        prestamoDevuelto.setFechaDevolucion(LocalDate.now());

        prestamoDTO = new PrestamoDTO();
        prestamoDTO.setUsuarioId(10L);
        prestamoDTO.setLibroId(20L);
    }

    // =========================================================
    // PRUEBAS: listar()
    // =========================================================
    @Test
    @DisplayName("listar() - Retorna lista con todos los prestamos")
    void listar_retornaListaDePrestamos() {
        // Given
        List<Prestamo> prestamos = List.of(prestamoActivo, prestamoDevuelto);
        when(repository.findAll()).thenReturn(prestamos);

        // When
        List<Prestamo> resultado = prestamoService.listar();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("listar() - Retorna lista vacía cuando no hay prestamos")
    void listar_retornaListaVacia() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        List<Prestamo> resultado = prestamoService.listar();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // =========================================================
    // PRUEBAS: obtener(id)
    // =========================================================
    @Test
    @DisplayName("obtener() - Retorna prestamo existente por id")
    void obtener_retornaPrestamoExistente() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(prestamoActivo));

        // When
        Prestamo resultado = prestamoService.obtener(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(EstadoPrestamo.ACTIVO, resultado.getEstado());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("obtener() - Lanza excepción cuando prestamo no existe")
    void obtener_lanzaExcepcionCuandoNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.obtener(99L));

        assertTrue(ex.getMessage().contains("Prestamo no encontrado con id: 99"));
        verify(repository, times(1)).findById(99L);
    }

    // =========================================================
    // PRUEBAS: listarPorUsuario(usuarioId)
    // =========================================================
    @Test
    @DisplayName("listarPorUsuario() - Retorna prestamos del usuario")
    void listarPorUsuario_retornaPrestamosFiltradosPorUsuario() {
        // Given
        List<Prestamo> prestamosUsuario = List.of(prestamoActivo, prestamoDevuelto);
        when(repository.findByUsuarioId(10L)).thenReturn(prestamosUsuario);

        // When
        List<Prestamo> resultado = prestamoService.listarPorUsuario(10L);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        resultado.forEach(p -> assertEquals(10L, p.getUsuarioId()));
        verify(repository, times(1)).findByUsuarioId(10L);
    }

    @Test
    @DisplayName("listarPorUsuario() - Retorna lista vacía si usuario no tiene prestamos")
    void listarPorUsuario_retornaVacioSiNoTienePrestamos() {
        // Given
        when(repository.findByUsuarioId(99L)).thenReturn(List.of());

        // When
        List<Prestamo> resultado = prestamoService.listarPorUsuario(99L);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // =========================================================
    // PRUEBAS: crear(dto) - REGLA DE NEGOCIO PRINCIPAL
    // =========================================================
    @SuppressWarnings("unchecked")
    private void mockWebClientSinMulta() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(false));
    }

    @SuppressWarnings("unchecked")
    private void mockWebClientConMulta() {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(true));
    }

    @Test
    @DisplayName("crear() - Crea prestamo exitosamente cuando usuario no tiene multas")
    void crear_creaPrestamoExitosamenteSinMultas() {
        // Given
        mockWebClientSinMulta();
        when(repository.save(any(Prestamo.class))).thenReturn(prestamoActivo);

        // When
        Prestamo resultado = prestamoService.crear(prestamoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(EstadoPrestamo.ACTIVO, resultado.getEstado());
        assertEquals(10L, resultado.getUsuarioId());
        verify(repository, times(1)).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("crear() - Lanza excepción si usuario tiene multas pendientes (regla de negocio)")
    void crear_lanzaExcepcionCuandoUsuarioTieneMultas() {
        // Given
        mockWebClientConMulta();

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.crear(prestamoDTO));

        assertTrue(ex.getMessage().contains("multas pendientes"));
        verify(repository, never()).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("crear() - Asigna fecha inicio hoy y vencimiento en 14 días")
    void crear_asignaFechasCorrectamente() {
        // Given
        mockWebClientSinMulta();
        Prestamo prestamoGuardado = new Prestamo();
        prestamoGuardado.setId(5L);
        prestamoGuardado.setUsuarioId(10L);
        prestamoGuardado.setLibroId(20L);
        prestamoGuardado.setEstado(EstadoPrestamo.ACTIVO);
        prestamoGuardado.setFechaInicio(LocalDate.now());
        prestamoGuardado.setFechaVencimiento(LocalDate.now().plusDays(14));

        when(repository.save(any(Prestamo.class))).thenReturn(prestamoGuardado);

        // When
        Prestamo resultado = prestamoService.crear(prestamoDTO);

        // Then
        assertEquals(LocalDate.now(), resultado.getFechaInicio());
        assertEquals(LocalDate.now().plusDays(14), resultado.getFechaVencimiento());
        assertNull(resultado.getFechaDevolucion());
    }

    @Test
    @DisplayName("crear() - Asigna estado ACTIVO al nuevo prestamo")
    void crear_asignaEstadoActivoAlNuevoPrestamo() {
        // Given
        mockWebClientSinMulta();
        when(repository.save(any(Prestamo.class))).thenReturn(prestamoActivo);

        // When
        Prestamo resultado = prestamoService.crear(prestamoDTO);

        // Then
        assertEquals(EstadoPrestamo.ACTIVO, resultado.getEstado());
    }

    // =========================================================
    // PRUEBAS: devolver(id)
    // =========================================================
    @Test
    @DisplayName("devolver() - Cambia estado a DEVUELTO y registra fecha de devolución")
    void devolver_cambiaEstadoADevueltoYRegistraFecha() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(prestamoActivo));
        when(repository.save(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        Prestamo resultado = prestamoService.devolver(1L);

        // Then
        assertEquals(EstadoPrestamo.DEVUELTO, resultado.getEstado());
        assertEquals(LocalDate.now(), resultado.getFechaDevolucion());
        verify(repository, times(1)).save(prestamoActivo);
    }

    @Test
    @DisplayName("devolver() - Lanza excepción si prestamo ya fue devuelto")
    void devolver_lanzaExcepcionSiYaFueDevuelto() {
        // Given
        when(repository.findById(2L)).thenReturn(Optional.of(prestamoDevuelto));

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.devolver(2L));

        assertTrue(ex.getMessage().contains("ya fue devuelto"));
        verify(repository, never()).save(any(Prestamo.class));
    }

    @Test
    @DisplayName("devolver() - Lanza excepción si prestamo no existe")
    void devolver_lanzaExcepcionSiPrestamoNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.devolver(99L));

        assertTrue(ex.getMessage().contains("Prestamo no encontrado"));
        verify(repository, never()).save(any(Prestamo.class));
    }

    // =========================================================
    // PRUEBAS: eliminar(id)
    // =========================================================
    @Test
    @DisplayName("eliminar() - Elimina prestamo existente correctamente")
    void eliminar_eliminaPrestamoExistente() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(prestamoActivo));
        doNothing().when(repository).deleteById(1L);

        // When
        prestamoService.eliminar(1L);

        // Then
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - Lanza excepción si prestamo no existe")
    void eliminar_lanzaExcepcionSiNoExiste() {
        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.eliminar(99L));

        assertTrue(ex.getMessage().contains("Prestamo no encontrado"));
        verify(repository, never()).deleteById(any());
    }
}
