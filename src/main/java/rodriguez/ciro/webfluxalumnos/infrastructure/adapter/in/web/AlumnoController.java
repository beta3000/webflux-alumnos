package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Alumnos", description = "API para gestión de alumnos")
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
  @Operation(summary = "Crear un nuevo alumno", description = "Crea un nuevo alumno en el sistema")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Alumno creado exitosamente"),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de alumno inválidos",
            content = @Content),
        @ApiResponse(responseCode = "409", description = "El alumno ya existe", content = @Content)
      })
  public Mono<Void> crearAlumno(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Datos del alumno a crear",
              required = true,
              content = @Content(schema = @Schema(implementation = AlumnoRequestDTO.class)))
          @RequestBody
          @Valid
          AlumnoRequestDTO request) {
    return crearAlumnoUseCase
        .crearAlumno(request.toDomain())
        .onErrorMap(
            IllegalArgumentException.class,
            ex ->
                new rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException(
                    ex.getMessage()));
  }

  @GetMapping("/activos")
  @Operation(
      summary = "Obtener alumnos activos",
      description = "Obtiene una lista paginada de alumnos activos")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de alumnos activos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = AlumnoResponseDTO.class)))
      })
  public Flux<AlumnoResponseDTO> obtenerAlumnosActivos(
      @Parameter(description = "Número de página (empezando desde 1)", example = "1")
          @RequestParam(defaultValue = "1")
          int page,
      @Parameter(description = "Tamaño de página", example = "10")
          @RequestParam(defaultValue = "10")
          int size) {

    return obtenerAlumnosActivosUseCase
        .obtenerAlumnosActivos(page, size)
        .map(AlumnoResponseDTO::fromDomain);
  }
}
