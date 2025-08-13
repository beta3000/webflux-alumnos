package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rodriguez.ciro.webfluxalumnos.application.port.in.CrearAlumnoUseCase;
import rodriguez.ciro.webfluxalumnos.application.port.in.ObtenerAlumnosActivosUseCase;
import rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto.AlumnoRequestDTO;
import rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto.AlumnoResponseDTO;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

  private final CrearAlumnoUseCase crearAlumnoUseCase;
  private final ObtenerAlumnosActivosUseCase obtenerAlumnosActivosUseCase;

  public AlumnoController(
      CrearAlumnoUseCase crearAlumnoUseCase,
      ObtenerAlumnosActivosUseCase obtenerAlumnosActivosUseCase) {
    this.crearAlumnoUseCase = crearAlumnoUseCase;
    this.obtenerAlumnosActivosUseCase = obtenerAlumnosActivosUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> crearAlumno(@RequestBody AlumnoRequestDTO request) {
    return crearAlumnoUseCase
        .crearAlumno(request.toDomain())
        .onErrorMap(
            IllegalArgumentException.class,
            ex ->
                new rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException(
                    ex.getMessage()));
  }

  @GetMapping("/activos")
  public Flux<AlumnoResponseDTO> obtenerAlumnosActivos(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

    return obtenerAlumnosActivosUseCase
        .obtenerAlumnosActivos(page, size)
        .map(AlumnoResponseDTO::fromDomain);
  }
}
