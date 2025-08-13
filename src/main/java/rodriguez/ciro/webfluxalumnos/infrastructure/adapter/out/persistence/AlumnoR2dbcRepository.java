package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AlumnoR2dbcRepository extends ReactiveCrudRepository<AlumnoEntity, Long> {

  @Query("SELECT * FROM alumnos WHERE estado = 'ACTIVO' ORDER BY id LIMIT $1 OFFSET $2")
  Flux<AlumnoEntity> findAlumnosActivosPaginados(int size, int offset);

  @Query("INSERT INTO alumnos (id, nombre, apellido, estado, edad) VALUES ($1, $2, $3, $4, $5)")
  Mono<Void> insertAlumno(Long id, String nombre, String apellido, String estado, Integer edad);
}
