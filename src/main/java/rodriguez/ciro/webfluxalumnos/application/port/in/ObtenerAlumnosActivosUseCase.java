package rodriguez.ciro.webfluxalumnos.application.port.in;

import reactor.core.publisher.Flux;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

public interface ObtenerAlumnosActivosUseCase {
  Flux<Alumno> obtenerAlumnosActivos(int page, int size);
}
