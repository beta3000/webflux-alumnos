package rodriguez.ciro.webfluxalumnos.application.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.application.port.in.CrearAlumnoUseCase;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
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
    return Mono.fromRunnable(alumno::validar)
        .then(Mono.defer(() -> verificarIdNoExiste(alumno.getId())))
        .then(Mono.defer(() -> alumnoRepository.save(alumno)));
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
