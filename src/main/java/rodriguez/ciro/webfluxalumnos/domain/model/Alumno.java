package rodriguez.ciro.webfluxalumnos.domain.model;

import java.util.Objects;
import rodriguez.ciro.webfluxalumnos.domain.exception.AlumnoInvalidoException;

public class Alumno {
  private Long id;
  private String nombre;
  private String apellido;
  private Estado estado;
  private Integer edad;

  public enum Estado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String descripcion;

    Estado(String descripcion) {
      this.descripcion = descripcion;
    }

    public String getDescripcion() {
      return descripcion;
    }
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
    validarId(id);
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    validarNombre(nombre);
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    validarApellido(apellido);
    this.apellido = apellido;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    validarEstado(estado);
    this.estado = estado;
  }

  public Integer getEdad() {
    return edad;
  }

  public void setEdad(Integer edad) {
    validarEdad(edad);
    this.edad = edad;
  }

  public boolean estaActivo() {
    return Estado.ACTIVO.equals(this.estado);
  }

  public void activar() {
    this.estado = Estado.ACTIVO;
  }

  public void desactivar() {
    this.estado = Estado.INACTIVO;
  }

  public void actualizarEdad(Integer nuevaEdad) {
    validarEdad(nuevaEdad);
    this.edad = nuevaEdad;
  }

  public void validar() {
    validarId(this.id);
    validarNombre(this.nombre);
    validarApellido(this.apellido);
    validarEdad(this.edad);
    validarEstado(this.estado);
  }

  private void validarId(Long id) {
    if (id == null || id <= 0) {
      throw new AlumnoInvalidoException("El ID del alumno debe ser un número positivo");
    }
  }

  private void validarNombre(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
      throw new AlumnoInvalidoException("El nombre es obligatorio");
    }
  }

  private void validarApellido(String apellido) {
    if (apellido == null || apellido.trim().isEmpty()) {
      throw new AlumnoInvalidoException("El apellido es obligatorio");
    }
  }

  private void validarEdad(Integer edad) {
    if (edad == null || edad <= 0 || edad > 150) {
      throw new AlumnoInvalidoException(
          "La edad del alumno debe ser un número positivo menor a 150");
    }
  }

  private void validarEstado(Estado estado) {
    if (estado == null) {
      throw new AlumnoInvalidoException("El estado del alumno es obligatorio");
    }
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
