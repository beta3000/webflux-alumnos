package rodriguez.ciro.webfluxalumnos.domain.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlumnoYaExisteExceptionTest {

  @Test
  void shouldCreateExceptionWithMessage() {
    // Given
    String expectedMessage = "El alumno ya existe";

    // When
    AlumnoYaExisteException exception = new AlumnoYaExisteException(expectedMessage);

    // Then
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldBeRuntimeException() {
    // Given
    AlumnoYaExisteException exception = new AlumnoYaExisteException("Test message");

    // Then
    assertInstanceOf(RuntimeException.class, exception);
  }
}
