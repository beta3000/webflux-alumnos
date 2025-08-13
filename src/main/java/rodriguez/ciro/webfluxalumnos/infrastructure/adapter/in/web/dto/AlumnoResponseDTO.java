package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

public class AlumnoResponseDTO {
  private final Long id;
  private final String nombre;
  private final String apellido;
  private final String estado;
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
