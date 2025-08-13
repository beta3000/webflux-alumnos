package rodriguez.ciro.webfluxalumnos.domain.exception;

public class AlumnoYaExisteException extends RuntimeException {
  public AlumnoYaExisteException(String message) {
    super(message);
  }
}
