package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web;

import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoYaExisteException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(AlumnoYaExisteException.class)
  public ResponseEntity<Map<String, Object>> handleAlumnoYaExiste(AlumnoYaExisteException ex) {
    logger.error("Alumno ya existe: ", ex);
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(createErrorResponse("ALUMNO_YA_EXISTE", ex.getMessage()));
  }

  @ExceptionHandler(AlumnoInvalidoException.class)
  public ResponseEntity<Map<String, Object>> handleAlumnoInvalido(AlumnoInvalidoException ex) {
    logger.error("Alumno inválido: ", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(createErrorResponse("ALUMNO_INVALIDO", ex.getMessage()));
  }

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(
      WebExchangeBindException ex) {
    logger.error("Alumno inválido (validación): ", ex);
    String mensaje =
        ex.getFieldErrors().isEmpty()
            ? "Datos inválidos"
            : ex.getFieldErrors().get(0).getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(createErrorResponse("ALUMNO_INVALIDO", mensaje));
  }

  @ExceptionHandler(org.springframework.web.server.ServerWebInputException.class)
  public ResponseEntity<Map<String, Object>> handleServerWebInputException(
      org.springframework.web.server.ServerWebInputException ex) {
    logger.error("Error de entrada/salida: ", ex);
    String message = "Error en el formato de los datos enviados";
    if (ex.getCause() instanceof com.fasterxml.jackson.core.JsonParseException) {
      message = "El JSON enviado no es válido";
    } else if (ex.getCause() instanceof com.fasterxml.jackson.databind.JsonMappingException) {
      message = "El JSON no corresponde al formato esperado";
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(createErrorResponse("DATOS_INVALIDOS", message));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
    logger.error("Argumento ilegal: ", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(createErrorResponse("PARAMETROS_INVALIDOS", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
    logger.error("Error interno del servidor: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(createErrorResponse("ERROR_INTERNO", "Ha ocurrido un error interno del servidor"));
  }

  private Map<String, Object> createErrorResponse(String code, String message) {
    return Map.of(
        "codigo", code,
        "mensaje", message,
        "timestamp", LocalDateTime.now());
  }
}
