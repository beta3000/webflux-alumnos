package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Schema(description = "Datos de respuesta de un alumno")
public class AlumnoResponseDTO {
  @Schema(description = "ID único del alumno", example = "1")
  private final Long id;

  @Schema(description = "Nombre del alumno", example = "Juan")
  private final String nombre;

  @Schema(description = "Apellido del alumno", example = "Pérez")
  private final String apellido;

  @Schema(description = "Estado del alumno", example = "ACTIVO")
  private final String estado;

  @Schema(description = "Edad del alumno", example = "20")
  private final Integer edad;

  public AlumnoResponseDTO(Long id, String nombre, String apellido, String estado, Integer edad) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.estado = estado;
    this.edad = edad;
  }

  public static AlumnoResponseDTO fromDomain(Alumno alumno) {
    return new AlumnoResponseDTO(
        alumno.getId(),
        alumno.getNombre(),
        alumno.getApellido(),
        alumno.getEstado().name(),
        alumno.getEdad());
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public String getEstado() {
    return estado;
  }

  public Integer getEdad() {
    return edad;
  }
}
