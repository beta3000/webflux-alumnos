package rodriguez.ciro.webfluxalumnos.application.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.application.port.in.CrearAlumnoUseCase;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoYaExisteException;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Service
public class CrearAlumnoService implements CrearAlumnoUseCase {

  private final AlumnoRepositoryPort alumnoRepository;

  public CrearAlumnoService(AlumnoRepositoryPort alumnoRepository) {
    this.alumnoRepository = alumnoRepository;
  }

  @Override
  public Mono<Void> crearAlumno(Alumno alumno) {

    return validarAlumno(alumno)
        .then(Mono.defer(() -> verificarIdNoExiste(alumno.getId())))
        .then(Mono.defer(() -> alumnoRepository.save(alumno)));
  }

  private Mono<Void> validarAlumno(Alumno alumno) {
    return Mono.fromRunnable(
        () -> {
          if (alumno.getId() == null || alumno.getId() <= 0) {
            throw new AlumnoInvalidoException("El ID del alumno debe ser un número positivo");
          }
          if (alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            throw new AlumnoInvalidoException("El nombre del alumno es obligatorio");
          }
          if (alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            throw new AlumnoInvalidoException("El apellido del alumno es obligatorio");
          }
          if (alumno.getEstado() == null) {
            throw new AlumnoInvalidoException("El estado del alumno es obligatorio");
          }
          if (alumno.getEdad() == null || alumno.getEdad() <= 0 || alumno.getEdad() > 150) {
            throw new AlumnoInvalidoException(
                "La edad del alumno debe ser un número positivo menor a 150");
          }
        });
  }

  private Mono<Void> verificarIdNoExiste(Long id) {
    return alumnoRepository
        .existsById(id)
        .flatMap(
            existe -> {
              if (Boolean.TRUE.equals(existe)) {
                return Mono.error(
                    new AlumnoYaExisteException("Ya existe un alumno con el ID: " + id));
              }
              return Mono.empty();
            });
  }
}
