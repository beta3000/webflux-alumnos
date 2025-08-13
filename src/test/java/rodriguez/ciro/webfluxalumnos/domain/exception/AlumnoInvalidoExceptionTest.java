package rodriguez.ciro.webfluxalumnos.domain.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlumnoInvalidoExceptionTest {

  @Test
  void shouldCreateExceptionWithMessage() {
    // Given
    String expectedMessage = "El alumno es inválido";

    // When
    AlumnoInvalidoException exception = new AlumnoInvalidoException(expectedMessage);

    // Then
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldBeRuntimeException() {
    // Given
    AlumnoInvalidoException exception = new AlumnoInvalidoException("Test message");

    // Then
    assertInstanceOf(RuntimeException.class, exception);
  }
}
