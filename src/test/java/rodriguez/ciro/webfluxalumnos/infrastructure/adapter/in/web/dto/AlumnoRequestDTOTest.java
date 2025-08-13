package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

class AlumnoRequestDTOTest {

  @Test
  void shouldCreateAlumnoRequestDTOWithAllFields() {
    // Given
    Long id = 1L;
    String nombre = "Juan";
    String apellido = "Pérez";
    String estado = "ACTIVO";
    Integer edad = 25;

    // When
    AlumnoRequestDTO dto = new AlumnoRequestDTO(id, nombre, apellido, estado, edad);

    // Then
    assertEquals(id, dto.getId());
    assertEquals(nombre, dto.getNombre());
    assertEquals(apellido, dto.getApellido());
    assertEquals(estado, dto.getEstado());
    assertEquals(edad, dto.getEdad());
  }

  @Test
  void shouldConvertToDomainSuccessfully() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(1L, "Juan", "Pérez", "ACTIVO", 25);

    // When
    Alumno alumno = dto.toDomain();

    // Then
    assertEquals(1L, alumno.getId());
    assertEquals("Juan", alumno.getNombre());
    assertEquals("Pérez", alumno.getApellido());
    assertEquals(Alumno.Estado.ACTIVO, alumno.getEstado());
    assertEquals(25, alumno.getEdad());
  }

  @Test
  void shouldConvertToDomainWithInactivoState() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(2L, "Ana", "González", "INACTIVO", 30);

    // When
    Alumno alumno = dto.toDomain();

    // Then
    assertEquals(2L, alumno.getId());
    assertEquals("Ana", alumno.getNombre());
    assertEquals("González", alumno.getApellido());
    assertEquals(Alumno.Estado.INACTIVO, alumno.getEstado());
    assertEquals(30, alumno.getEdad());
  }

  @Test
  void shouldConvertToDomainWithLowercaseState() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(3L, "Carlos", "López", "activo", 28);

    // When
    Alumno alumno = dto.toDomain();

    // Then
    assertEquals(Alumno.Estado.ACTIVO, alumno.getEstado());
  }

  @Test
  void shouldConvertToDomainWithNullState() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(4L, "María", "Martín", null, 22);

    // When
    Alumno alumno = dto.toDomain();

    // Then
    assertNull(alumno.getEstado());
  }

  @Test
  void shouldConvertToDomainWithNullFields() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(null, null, null, null, null);

    // When
    Alumno alumno = dto.toDomain();

    // Then
    assertNull(alumno.getId());
    assertNull(alumno.getNombre());
    assertNull(alumno.getApellido());
    assertNull(alumno.getEstado());
    assertNull(alumno.getEdad());
  }

  @Test
  void shouldThrowExceptionWithInvalidState() {
    // Given
    AlumnoRequestDTO dto = new AlumnoRequestDTO(5L, "Pedro", "Ruiz", "ESTADO_INVALIDO", 35);

    // When & Then
    assertThrows(IllegalArgumentException.class, dto::toDomain);
  }
}
