package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Schema(description = "Datos de entrada para crear o actualizar un alumno")
public class AlumnoRequestDTO {
  @Schema(description = "ID único del alumno", example = "1", nullable = true)
  private final Long id;

  @Schema(description = "Nombre del alumno", example = "Juan", required = true)
  private final String nombre;

  @Schema(description = "Apellido del alumno", example = "Pérez", required = true)
  private final String apellido;

  @Schema(
      description = "Estado del alumno",
      example = "ACTIVO",
      allowableValues = {"ACTIVO", "INACTIVO"})
  private final String estado;

  @Schema(description = "Edad del alumno", example = "20", minimum = "1", maximum = "120")
  private final Integer edad;

  @JsonCreator
  public AlumnoRequestDTO(
      @JsonProperty("id") Long id,
      @JsonProperty("nombre") String nombre,
      @JsonProperty("apellido") String apellido,
      @JsonProperty("estado") String estado,
      @JsonProperty("edad") Integer edad) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.estado = estado;
    this.edad = edad;
  }

  public Alumno toDomain() {
    Alumno.Estado estadoEnum = estado != null ? Alumno.Estado.valueOf(estado.toUpperCase()) : null;
    return new Alumno(id, nombre, apellido, estadoEnum, edad);
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
