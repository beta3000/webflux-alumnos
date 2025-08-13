package rodriguez.ciro.webfluxalumnos.application.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rodriguez.ciro.webfluxalumnos.application.port.in.ObtenerAlumnosActivosUseCase;
import rodriguez.ciro.webfluxalumnos.application.port.out.AlumnoRepositoryPort;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Service
public class ObtenerAlumnosActivosService implements ObtenerAlumnosActivosUseCase {

  private final AlumnoRepositoryPort alumnoRepository;

  public ObtenerAlumnosActivosService(AlumnoRepositoryPort alumnoRepository) {
    this.alumnoRepository = alumnoRepository;
  }

  @Override
  public Flux<Alumno> obtenerAlumnosActivos(int page, int size) {
    return Flux.defer(
        () -> {
          validarParametrosPaginacion(page, size);
          return alumnoRepository.findAlumnosActivosPaginados(page, size);
        });
  }

  private void validarParametrosPaginacion(int page, int size) {
    if (page < 1) {
      throw new IllegalArgumentException("El número de página debe ser mayor o igual a 1");
    }
    if (size <= 0 || size > 100) {
      throw new IllegalArgumentException("El tamaño de página debe ser entre 1 y 100");
    }
  }
}
