package rodriguez.ciro.webfluxalumnos.application.port.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

public interface AlumnoRepositoryPort {
  Mono<Void> save(Alumno alumno);

  Mono<Boolean> existsById(Long id);

  Flux<Alumno> findAlumnosActivosPaginados(int page, int size);
}
