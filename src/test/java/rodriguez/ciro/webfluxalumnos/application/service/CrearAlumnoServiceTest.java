package rodriguez.ciro.webfluxalumnos.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoYaExisteException;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@ExtendWith(MockitoExtension.class)
class CrearAlumnoServiceTest {

  @Mock private AlumnoRepositoryPort alumnoRepositoryPort;

  @InjectMocks private CrearAlumnoService crearAlumnoService;

  private Alumno alumnoValido;

  @BeforeEach
  void setUp() {
    alumnoValido = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
  }

  @Test
  void shouldCreateAlumnoSuccessfully() {
    // Given
    when(alumnoRepositoryPort.existsById(1L)).thenReturn(Mono.just(false));
    when(alumnoRepositoryPort.save(any(Alumno.class))).thenReturn(Mono.empty());

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoValido)).verifyComplete();

    verify(alumnoRepositoryPort).existsById(1L);
    verify(alumnoRepositoryPort).save(alumnoValido);
  }

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    // Given
    Alumno alumnoInvalido = new Alumno(null, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenIdIsZero() {
    // Given
    Alumno alumnoInvalido = new Alumno(0L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenIdIsNegative() {
    // Given
    Alumno alumnoInvalido = new Alumno(-1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenNombreIsNull() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, null, "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenNombreIsEmpty() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenNombreIsBlank() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "   ", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenApellidoIsNull() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", null, Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenApellidoIsEmpty() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "", Alumno.Estado.ACTIVO, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenEstadoIsNull() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "Pérez", null, 25);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenEdadIsNull() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, null);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenEdadIsZero() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 0);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenEdadIsNegative() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, -1);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenEdadIsTooHigh() {
    // Given
    Alumno alumnoInvalido = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 151);

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoInvalido))
        .expectError(AlumnoInvalidoException.class)
        .verify();

    verify(alumnoRepositoryPort, never()).existsById(any());
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldThrowExceptionWhenAlumnoAlreadyExists() {
    // Given
    when(alumnoRepositoryPort.existsById(1L)).thenReturn(Mono.just(true));

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoValido))
        .expectError(AlumnoYaExisteException.class)
        .verify();

    verify(alumnoRepositoryPort).existsById(1L);
    verify(alumnoRepositoryPort, never()).save(any());
  }

  @Test
  void shouldHandleRepositoryError() {
    // Given
    when(alumnoRepositoryPort.existsById(1L))
        .thenReturn(Mono.error(new RuntimeException("Database error")));

    // When & Then
    StepVerifier.create(crearAlumnoService.crearAlumno(alumnoValido))
        .expectError(RuntimeException.class)
        .verify();

    verify(alumnoRepositoryPort).existsById(1L);
    verify(alumnoRepositoryPort, never()).save(any());
  }
}
