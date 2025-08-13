package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebInputException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoYaExisteException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @Test
  void shouldHandleAlumnoYaExisteException() {
    // Given
    String expectedMessage = "Ya existe un alumno con el ID: 1";
    AlumnoYaExisteException exception = new AlumnoYaExisteException(expectedMessage);

    // When
    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleAlumnoYaExiste(exception);

    // Then
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("ALUMNO_YA_EXISTE", body.get("codigo"));
    assertEquals(expectedMessage, body.get("mensaje"));
    assertNotNull(body.get("timestamp"));
    assertInstanceOf(LocalDateTime.class, body.get("timestamp"));
  }

  @Test
  void shouldHandleAlumnoInvalidoException() {
    // Given
    String expectedMessage = "El nombre del alumno es obligatorio";
    AlumnoInvalidoException exception = new AlumnoInvalidoException(expectedMessage);

    // When
    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleAlumnoInvalido(exception);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("ALUMNO_INVALIDO", body.get("codigo"));
    assertEquals(expectedMessage, body.get("mensaje"));
    assertNotNull(body.get("timestamp"));
  }

  @Test
  void shouldHandleServerWebInputException() {
    // Given
    ServerWebInputException exception = mock(ServerWebInputException.class);
    when(exception.getCause()).thenReturn(new RuntimeException("Other error"));

    // When
    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleServerWebInputException(exception);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("DATOS_INVALIDOS", body.get("codigo"));
    assertEquals("Error en el formato de los datos enviados", body.get("mensaje"));
    assertNotNull(body.get("timestamp"));
  }

  @Test
  void shouldHandleIllegalArgumentException() {
    // Given
    String expectedMessage = "Parámetro inválido";
    IllegalArgumentException exception = new IllegalArgumentException(expectedMessage);

    // When
    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleIllegalArgument(exception);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("PARAMETROS_INVALIDOS", body.get("codigo"));
    assertEquals(expectedMessage, body.get("mensaje"));
    assertNotNull(body.get("timestamp"));
  }

  @Test
  void shouldHandleGenericException() {
    // Given
    Exception exception = new Exception("Generic error");

    // When
    ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGeneral(exception);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("ERROR_INTERNO", body.get("codigo"));
    assertEquals("Ha ocurrido un error interno del servidor", body.get("mensaje"));
    assertNotNull(body.get("timestamp"));
  }
}
