package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Component
public class AlumnoRepositoryAdapter implements AlumnoRepositoryPort {

  private final AlumnoR2dbcRepository repository;

  public AlumnoRepositoryAdapter(AlumnoR2dbcRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Void> save(Alumno alumno) {
    AlumnoEntity entity = AlumnoEntity.fromDomain(alumno);
    return repository.insertAlumno(
        entity.getId(),
        entity.getNombre(),
        entity.getApellido(),
        entity.getEstado(),
        entity.getEdad());
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    return repository.existsById(id);
  }

  @Override
  public Flux<Alumno> findAlumnosActivosPaginados(int page, int size) {
    var pageDatabase = page - 1;
    int offset = pageDatabase * size;
    return repository.findAlumnosActivosPaginados(size, offset).map(AlumnoEntity::toDomain);
  }
}
