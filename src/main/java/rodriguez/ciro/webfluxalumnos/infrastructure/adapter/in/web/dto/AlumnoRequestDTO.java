package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

public class AlumnoRequestDTO {
  private final Long id;
  private final String nombre;
  private final String apellido;
  private final String estado;
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
