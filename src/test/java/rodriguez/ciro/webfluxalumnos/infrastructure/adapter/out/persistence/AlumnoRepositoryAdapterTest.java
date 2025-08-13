package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.out.persistence;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@ExtendWith(MockitoExtension.class)
class AlumnoRepositoryAdapterTest {

  @Mock private AlumnoR2dbcRepository repository;

  @InjectMocks private AlumnoRepositoryAdapter alumnoRepositoryAdapter;

  private Alumno alumno;

  @BeforeEach
  void setUp() {
    alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
  }

  @Test
  void shouldSaveAlumnoSuccessfully() {
    // Given
    when(repository.insertAlumno(anyLong(), anyString(), anyString(), anyString(), anyInt()))
        .thenReturn(Mono.empty());

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.save(alumno)).verifyComplete();

    verify(repository).insertAlumno(1L, "Juan", "Pérez", "ACTIVO", 25);
  }

  @Test
  void shouldReturnTrueWhenAlumnoExists() {
    // Given
    when(repository.existsById(1L)).thenReturn(Mono.just(true));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.existsById(1L)).expectNext(true).verifyComplete();

    verify(repository).existsById(1L);
  }

  @Test
  void shouldReturnFalseWhenAlumnoDoesNotExist() {
    // Given
    when(repository.existsById(1L)).thenReturn(Mono.just(false));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.existsById(1L)).expectNext(false).verifyComplete();

    verify(repository).existsById(1L);
  }

  @Test
  void shouldFindAlumnosActivosPaginadosSuccessfully() {
    // Given
    int page = 1;
    int size = 10;
    AlumnoEntity entity1 = new AlumnoEntity(1L, "Juan", "Pérez", "ACTIVO", 25);
    AlumnoEntity entity2 = new AlumnoEntity(2L, "Ana", "González", "ACTIVO", 30);

    when(repository.findAlumnosActivosPaginados(size, 0)).thenReturn(Flux.just(entity1, entity2));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.findAlumnosActivosPaginados(page, size))
        .expectNextMatches(
            alumno1 ->
                alumno1.getId().equals(1L)
                    && alumno1.getNombre().equals("Juan")
                    && alumno1.getEstado().equals(Alumno.Estado.ACTIVO))
        .expectNextMatches(
            alumno2 ->
                alumno2.getId().equals(2L)
                    && alumno2.getNombre().equals("Ana")
                    && alumno2.getEstado().equals(Alumno.Estado.ACTIVO))
        .verifyComplete();

    verify(repository).findAlumnosActivosPaginados(size, 0);
  }

  @Test
  void shouldCalculateOffsetCorrectlyForPagination() {
    // Given
    int page = 3;
    int size = 5;
    int expectedOffset = 2 * 5; // (page - 1) * size

    when(repository.findAlumnosActivosPaginados(size, expectedOffset)).thenReturn(Flux.empty());

    // When
    alumnoRepositoryAdapter.findAlumnosActivosPaginados(page, size).blockLast();

    // Then
    verify(repository).findAlumnosActivosPaginados(size, expectedOffset);
  }

  @Test
  void shouldReturnEmptyFluxWhenNoAlumnosFound() {
    // Given
    int page = 1;
    int size = 10;
    when(repository.findAlumnosActivosPaginados(size, 0)).thenReturn(Flux.empty());

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.findAlumnosActivosPaginados(page, size))
        .verifyComplete();

    verify(repository).findAlumnosActivosPaginados(size, 0);
  }

  @Test
  void shouldHandleSaveError() {
    // Given
    when(repository.insertAlumno(anyLong(), anyString(), anyString(), anyString(), anyInt()))
        .thenReturn(Mono.error(new RuntimeException("Database error")));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.save(alumno))
        .expectError(RuntimeException.class)
        .verify();

    verify(repository).insertAlumno(1L, "Juan", "Pérez", "ACTIVO", 25);
  }

  @Test
  void shouldHandleExistsByIdError() {
    // Given
    when(repository.existsById(1L)).thenReturn(Mono.error(new RuntimeException("Database error")));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.existsById(1L))
        .expectError(RuntimeException.class)
        .verify();

    verify(repository).existsById(1L);
  }

  @Test
  void shouldHandleFindAlumnosActivosError() {
    // Given
    when(repository.findAlumnosActivosPaginados(anyInt(), anyInt()))
        .thenReturn(Flux.error(new RuntimeException("Database error")));

    // When & Then
    StepVerifier.create(alumnoRepositoryAdapter.findAlumnosActivosPaginados(1, 10))
        .expectError(RuntimeException.class)
        .verify();
  }
}
