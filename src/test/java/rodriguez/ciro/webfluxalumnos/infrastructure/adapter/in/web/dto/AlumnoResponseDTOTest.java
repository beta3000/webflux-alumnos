package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.in.web.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

class AlumnoResponseDTOTest {

  @Test
  void shouldCreateAlumnoResponseDTOWithAllFields() {
    // Given
    Long id = 1L;
    String nombre = "Juan";
    String apellido = "Pérez";
    String estado = "ACTIVO";
    Integer edad = 25;

    // When
    AlumnoResponseDTO dto = new AlumnoResponseDTO(id, nombre, apellido, estado, edad);

    // Then
    assertEquals(id, dto.getId());
    assertEquals(nombre, dto.getNombre());
    assertEquals(apellido, dto.getApellido());
    assertEquals(estado, dto.getEstado());
    assertEquals(edad, dto.getEdad());
  }

  @Test
  void shouldCreateFromDomainSuccessfully() {
    // Given
    Alumno alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When
    AlumnoResponseDTO dto = AlumnoResponseDTO.fromDomain(alumno);

    // Then
    assertEquals(1L, dto.getId());
    assertEquals("Juan", dto.getNombre());
    assertEquals("Pérez", dto.getApellido());
    assertEquals("ACTIVO", dto.getEstado());
    assertEquals(25, dto.getEdad());
  }

  @Test
  void shouldCreateFromDomainWithInactivoState() {
    // Given
    Alumno alumno = new Alumno(2L, "Ana", "González", Alumno.Estado.INACTIVO, 30);

    // When
    AlumnoResponseDTO dto = AlumnoResponseDTO.fromDomain(alumno);

    // Then
    assertEquals(2L, dto.getId());
    assertEquals("Ana", dto.getNombre());
    assertEquals("González", dto.getApellido());
    assertEquals("INACTIVO", dto.getEstado());
    assertEquals(30, dto.getEdad());
  }

  @Test
  void shouldHandleNullValues() {
    // Given
    AlumnoResponseDTO dto = new AlumnoResponseDTO(null, null, null, null, null);

    // Then
    assertNull(dto.getId());
    assertNull(dto.getNombre());
    assertNull(dto.getApellido());
    assertNull(dto.getEstado());
    assertNull(dto.getEdad());
  }

  @Test
  void shouldThrowExceptionWhenDomainHasNullState() {
    // Given
    Alumno alumnoWithNullState = new Alumno(1L, "Juan", "Pérez", null, 25);

    // When & Then
    assertThrows(
        NullPointerException.class, () -> AlumnoResponseDTO.fromDomain(alumnoWithNullState));
  }
}
