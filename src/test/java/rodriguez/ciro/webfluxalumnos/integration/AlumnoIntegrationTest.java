package rodriguez.ciro.webfluxalumnos.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AlumnoIntegrationTest {

  @Autowired private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    // Limpiar la base de datos antes de cada test si es necesario
  }

  @Test
  void shouldCreateAndRetrieveAlumnoSuccessfully() {
    // Given - Crear un alumno
    String createRequest =
        """
        {
          "id": 999,
          "nombre": "Integration",
          "apellido": "Test",
          "estado": "ACTIVO",
          "edad": 25
        }
        """;

    // When - Crear el alumno
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(createRequest)
        .exchange()
        .expectStatus()
        .isCreated();

    // Then - Verificar que el alumno se puede obtener
    webTestClient
        .get()
        .uri("/api/alumnos/activos")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$")
        .isArray()
        .jsonPath("$[?(@.id == 999)].nombre")
        .isEqualTo("Integration")
        .jsonPath("$[?(@.id == 999)].apellido")
        .isEqualTo("Test")
        .jsonPath("$[?(@.id == 999)].estado")
        .isEqualTo("ACTIVO")
        .jsonPath("$[?(@.id == 999)].edad")
        .isEqualTo(25);
  }

  @Test
  void shouldNotCreateDuplicateAlumno() {
    // Given - Crear un alumno
    String createRequest =
        """
        {
          "id": 998,
          "nombre": "Duplicate",
          "apellido": "Test",
          "estado": "ACTIVO",
          "edad": 30
        }
        """;

    // When - Crear el primer alumno
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(createRequest)
        .exchange()
        .expectStatus()
        .isCreated();

    // Then - Intentar crear el mismo alumno debería fallar
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(createRequest)
        .exchange()
        .expectStatus()
        .isEqualTo(409)
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("ALUMNO_YA_EXISTE")
        .jsonPath("$.mensaje")
        .value(
            message ->
                assertTrue(message.toString().contains("Ya existe un alumno con el ID: 998")));
  }

  @Test
  void shouldValidateAlumnoFields() {
    // Given - Alumno con campos inválidos
    String invalidRequest =
        """
        {
          "id": null,
          "nombre": "",
          "apellido": "Test",
          "estado": "ACTIVO",
          "edad": -1
        }
        """;

    // When & Then - Debería fallar la validación
    webTestClient
        .post()
        .uri("/api/alumnos")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(invalidRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.codigo")
        .isEqualTo("ALUMNO_INVALIDO");
  }

  @Test
  void shouldHandleInvalidJsonPayload() {
    // Given - JSON inválido
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
        .exists()
        .jsonPath("$.timestamp")
        .exists();
  }

  @Test
  void shouldHandlePaginationCorrectly() {
    // Given - Crear varios alumnos para probar paginación
    for (int i = 1000; i < 1005; i++) {
      String createRequest =
          String.format(
              """
          {
            "id": %d,
            "nombre": "Test%d",
            "apellido": "Apellido%d",
            "estado": "ACTIVO",
            "edad": %d
          }
          """,
              i, i, i, 20 + (i % 10));

      webTestClient
          .post()
          .uri("/api/alumnos")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(createRequest)
          .exchange()
          .expectStatus()
          .isCreated();
    }

    // When & Then - Probar paginación
    webTestClient
        .get()
        .uri("/api/alumnos/activos?page=1&size=2")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$")
        .isArray()
        .jsonPath("$.length()")
        .isEqualTo(2);
  }

  @Test
  void shouldReturnEmptyArrayWhenNoActiveAlumnos() {
    // When & Then - Consultar cuando no hay alumnos activos
    webTestClient
        .get()
        .uri("/api/alumnos/activos?page=100&size=10")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$")
        .isArray()
        .jsonPath("$.length()")
        .isEqualTo(0);
  }
}
