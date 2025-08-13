package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

@Table("alumnos")
public class AlumnoEntity {
  @Id private Long id;
  private String nombre;
  private String apellido;
  private String estado;
  private Integer edad;

  public AlumnoEntity() {}

  public AlumnoEntity(Long id, String nombre, String apellido, String estado, Integer edad) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.estado = estado;
    this.edad = edad;
  }

  public static AlumnoEntity fromDomain(Alumno alumno) {
    return new AlumnoEntity(
        alumno.getId(),
        alumno.getNombre(),
        alumno.getApellido(),
        alumno.getEstado() != null ? alumno.getEstado().name() : null,
        alumno.getEdad());
  }

  public Alumno toDomain() {
    Alumno.Estado estadoEnum = this.estado != null ? Alumno.Estado.valueOf(this.estado) : null;
    return new Alumno(this.id, this.nombre, this.apellido, estadoEnum, this.edad);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }
}
