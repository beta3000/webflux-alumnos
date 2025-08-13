package rodriguez.ciro.webfluxalumnos.application.port.in;

import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

public interface CrearAlumnoUseCase {
  Mono<Void> crearAlumno(Alumno alumno);
}
