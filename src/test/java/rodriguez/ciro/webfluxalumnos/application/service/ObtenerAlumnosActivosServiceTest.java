package rodriguez.ciro.webfluxalumnos.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@ExtendWith(MockitoExtension.class)
class ObtenerAlumnosActivosServiceTest {

  @Mock private AlumnoRepositoryPort alumnoRepositoryPort;

  @InjectMocks private ObtenerAlumnosActivosService obtenerAlumnosActivosService;

  private Alumno alumno1;
  private Alumno alumno2;

  @BeforeEach
  void setUp() {
    alumno1 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    alumno2 = new Alumno(2L, "Ana", "González", Alumno.Estado.ACTIVO, 30);
  }

  @Test
  void shouldReturnAlumnosActivosSuccessfully() {
    // Given
    int page = 1;
    int size = 10;
    when(alumnoRepositoryPort.findAlumnosActivosPaginados(page, size))
        .thenReturn(Flux.just(alumno1, alumno2));

    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(page, size))
        .expectNext(alumno1)
        .expectNext(alumno2)
        .verifyComplete();

    verify(alumnoRepositoryPort).findAlumnosActivosPaginados(page, size);
  }

  @Test
  void shouldReturnEmptyFluxWhenNoAlumnosFound() {
    // Given
    int page = 1;
    int size = 10;
    when(alumnoRepositoryPort.findAlumnosActivosPaginados(page, size)).thenReturn(Flux.empty());

    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(page, size))
        .verifyComplete();

    verify(alumnoRepositoryPort).findAlumnosActivosPaginados(page, size);
  }

  @Test
  void shouldHandleRepositoryError() {
    // Given
    int page = 1;
    int size = 10;
    when(alumnoRepositoryPort.findAlumnosActivosPaginados(page, size))
        .thenReturn(Flux.error(new RuntimeException("Database error")));

    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(page, size))
        .expectError(RuntimeException.class)
        .verify();

    verify(alumnoRepositoryPort).findAlumnosActivosPaginados(page, size);
  }

  @Test
  void shouldHandleDifferentPageSizes() {
    // Given
    int page = 2;
    int size = 5;
    when(alumnoRepositoryPort.findAlumnosActivosPaginados(page, size))
        .thenReturn(Flux.just(alumno1));

    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(page, size))
        .expectNext(alumno1)
        .verifyComplete();

    verify(alumnoRepositoryPort).findAlumnosActivosPaginados(page, size);
  }

  @Test
  void shouldPassCorrectParametersToRepository() {
    // Given
    int page = 3;
    int size = 15;
    when(alumnoRepositoryPort.findAlumnosActivosPaginados(page, size)).thenReturn(Flux.empty());

    // When
    obtenerAlumnosActivosService.obtenerAlumnosActivos(page, size).blockLast();

    // Then
    verify(alumnoRepositoryPort).findAlumnosActivosPaginados(page, size);
  }

  @Test
  void shouldThrowExceptionWhenPageIsZero() {
    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(0, 10))
        .expectError(IllegalArgumentException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).findAlumnosActivosPaginados(anyInt(), anyInt());
  }

  @Test
  void shouldThrowExceptionWhenPageIsNegative() {
    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(-1, 10))
        .expectError(IllegalArgumentException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).findAlumnosActivosPaginados(anyInt(), anyInt());
  }

  @Test
  void shouldThrowExceptionWhenSizeIsZero() {
    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(1, 0))
        .expectError(IllegalArgumentException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).findAlumnosActivosPaginados(anyInt(), anyInt());
  }

  @Test
  void shouldThrowExceptionWhenSizeIsTooLarge() {
    // When & Then
    StepVerifier.create(obtenerAlumnosActivosService.obtenerAlumnosActivos(1, 101))
        .expectError(IllegalArgumentException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).findAlumnosActivosPaginados(anyInt(), anyInt());
  }
}
