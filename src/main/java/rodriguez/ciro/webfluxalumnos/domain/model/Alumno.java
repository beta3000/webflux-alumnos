package rodriguez.ciro.webfluxalumnos.domain.model;

import java.util.Objects;

public class Alumno {
  private Long id;
  private String nombre;
  private String apellido;
  private Estado estado;
  private Integer edad;

  public enum Estado {
    ACTIVO,
    INACTIVO
  }

  public Alumno() {}

  public Alumno(Long id, String nombre, String apellido, Estado estado, Integer edad) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.estado = estado;
    this.edad = edad;
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

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Alumno alumno = (Alumno) o;
    return Objects.equals(id, alumno.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Alumno{"
        + "id="
        + id
        + ", nombre='"
        + nombre
        + '\''
        + ", apellido='"
        + apellido
        + '\''
        + ", estado="
        + estado
        + ", edad="
        + edad
        + '}';
  }
}
