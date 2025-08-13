package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.application.port.in.CrearAlumnoUseCase;
import rodriguez.ciro.webfluxalumnos.application.port.in.ObtenerAlumnosActivosUseCase;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoYaExisteException;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@WebFluxTest(AlumnoController.class)
class AlumnoControllerTest {

  @Autowired private WebTestClient webTestClient;

  @MockitoBean private CrearAlumnoUseCase crearAlumnoUseCase;

  @MockitoBean private ObtenerAlumnosActivosUseCase obtenerAlumnosActivosUseCase;

  private Alumno alumno1;
  private Alumno alumno2;

  @BeforeEach
  void setUp() {
    alumno1 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    alumno2 = new Alumno(2L, "Ana", "González", Alumno.Estado.ACTIVO, 30);
  }

  @Test
  void shouldCreateAlumnoSuccessfully() {
    // Given
    when(crearAlumnoUseCase.crearAlumno(any(Alumno.class))).thenReturn(Mono.empty());

    String requestBody =
        """
        {
          "id": 1,
          "nombre": "Juan",
          "apellido": "Pérez",
          "estado": "ACTIVO",
          "edad": 25
        }
        """;

    // When & Then
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .isEmpty();

    verify(crearAlumnoUseCase).crearAlumno(any(Alumno.class));
  }

  @Test
  void shouldReturnBadRequestWhenAlumnoIsInvalid() {
    // Given
    when(crearAlumnoUseCase.crearAlumno(any(Alumno.class)))
        .thenReturn(Mono.error(new AlumnoInvalidoException("El nombre es obligatorio")));

    String requestBody =
        """
        {
          "id": 1,
          "nombre": "",
          "apellido": "Pérez",
          "estado": "ACTIVO",
          "edad": 25
        }
        """;

    // When & Then
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("ALUMNO_INVALIDO")
        .jsonPath("$.mensaje")
        .isEqualTo("El nombre es obligatorio");
  }

  @Test
  void shouldReturnConflictWhenAlumnoAlreadyExists() {
    // Given
    when(crearAlumnoUseCase.crearAlumno(any(Alumno.class)))
        .thenReturn(Mono.error(new AlumnoYaExisteException("Ya existe un alumno con el ID: 1")));

    String requestBody =
        """
        {
          "id": 1,
          "nombre": "Juan",
          "apellido": "Pérez",
          "estado": "ACTIVO",
          "edad": 25
        }
        """;

    // When & Then
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isEqualTo(409)
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("ALUMNO_YA_EXISTE")
        .jsonPath("$.mensaje")
        .isEqualTo("Ya existe un alumno con el ID: 1");
  }

  @Test
  void shouldReturnBadRequestWhenJsonIsInvalid() {
    // Given
    String invalidJson = "{ invalid json }";

    // When & Then
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(invalidJson)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.codigo")
        .exists()
        .jsonPath("$.mensaje")
        .exists();
  }

  @Test
  void shouldGetAlumnosActivosSuccessfully() {
    // Given
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(1, 10))
        .thenReturn(Flux.just(alumno1, alumno2));

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos?page=1&size=10")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Object.class)
        .hasSize(2);

    verify(obtenerAlumnosActivosUseCase).obtenerAlumnosActivos(1, 10);
  }

  @Test
  void shouldGetAlumnosActivosWithDefaultPagination() {
    // Given
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(1, 10)).thenReturn(Flux.just(alumno1));

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Object.class)
        .hasSize(1);

    verify(obtenerAlumnosActivosUseCase).obtenerAlumnosActivos(1, 10);
  }

  @Test
  void shouldGetAlumnosActivosWithCustomPagination() {
    // Given
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(2, 5)).thenReturn(Flux.just(alumno1));

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos?page=2&size=5")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Object.class)
        .hasSize(1);

    verify(obtenerAlumnosActivosUseCase).obtenerAlumnosActivos(2, 5);
  }

  @Test
  void shouldHandlePageZeroCorrectly() {
    // Given - page=0 debería causar error de validación en el servicio
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(0, 10))
        .thenReturn(
            Flux.error(
                new IllegalArgumentException("El número de página debe ser mayor o igual a 1")));

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos?page=0&size=10")
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("PARAMETROS_INVALIDOS");

    verify(obtenerAlumnosActivosUseCase).obtenerAlumnosActivos(0, 10);
  }

  @Test
  void shouldReturnEmptyListWhenNoAlumnosFound() {
    // Given
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(1, 10)).thenReturn(Flux.empty());

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Object.class)
        .hasSize(0);
  }

  @Test
  void shouldHandleInternalServerError() {
    // Given
    when(obtenerAlumnosActivosUseCase.obtenerAlumnosActivos(anyInt(), anyInt()))
        .thenReturn(Flux.error(new RuntimeException("Database error")));

    // When & Then
    webTestClient
        .get()
        .uri("/api/alumnos/activos")
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("ERROR_INTERNO")
        .jsonPath("$.mensaje")
        .isEqualTo("Ha ocurrido un error interno del servidor");
  }
}
