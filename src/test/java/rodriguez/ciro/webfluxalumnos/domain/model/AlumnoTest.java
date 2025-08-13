package rodriguez.ciro.webfluxalumnos.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlumnoTest {

  @Test
  void shouldCreateAlumnoWithAllFields() {
    // Given
    Long id = 1L;
    String nombre = "Juan";
    String apellido = "Pérez";
    Alumno.Estado estado = Alumno.Estado.ACTIVO;
    Integer edad = 25;

    // When
    Alumno alumno = new Alumno(id, nombre, apellido, estado, edad);

    // Then
    assertEquals(id, alumno.getId());
    assertEquals(nombre, alumno.getNombre());
    assertEquals(apellido, alumno.getApellido());
    assertEquals(estado, alumno.getEstado());
    assertEquals(edad, alumno.getEdad());
  }

  @Test
  void shouldCreateAlumnoWithDefaultConstructor() {
    // When
    Alumno alumno = new Alumno();

    // Then
    assertNull(alumno.getId());
    assertNull(alumno.getNombre());
    assertNull(alumno.getApellido());
    assertNull(alumno.getEstado());
    assertNull(alumno.getEdad());
  }

  @Test
  void shouldSetAndGetId() {
    // Given
    Alumno alumno = new Alumno();
    Long expectedId = 1L;

    // When
    alumno.setId(expectedId);

    // Then
    assertEquals(expectedId, alumno.getId());
  }

  @Test
  void shouldSetAndGetNombre() {
    // Given
    Alumno alumno = new Alumno();
    String expectedNombre = "Ana";

    // When
    alumno.setNombre(expectedNombre);

    // Then
    assertEquals(expectedNombre, alumno.getNombre());
  }

  @Test
  void shouldSetAndGetApellido() {
    // Given
    Alumno alumno = new Alumno();
    String expectedApellido = "González";

    // When
    alumno.setApellido(expectedApellido);

    // Then
    assertEquals(expectedApellido, alumno.getApellido());
  }

  @Test
  void shouldSetAndGetEstado() {
    // Given
    Alumno alumno = new Alumno();
    Alumno.Estado expectedEstado = Alumno.Estado.INACTIVO;

    // When
    alumno.setEstado(expectedEstado);

    // Then
    assertEquals(expectedEstado, alumno.getEstado());
  }

  @Test
  void shouldSetAndGetEdad() {
    // Given
    Alumno alumno = new Alumno();
    Integer expectedEdad = 30;

    // When
    alumno.setEdad(expectedEdad);

    // Then
    assertEquals(expectedEdad, alumno.getEdad());
  }

  @Test
  void shouldTestEqualsWithEqualObjects() {
    // Given
    Alumno alumno1 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    Alumno alumno2 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // Then
    assertEquals(alumno1, alumno2);
  }

  @Test
  void shouldTestEqualsWithDifferentObjects() {
    // Given
    Alumno alumno1 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    Alumno alumno2 = new Alumno(2L, "Ana", "González", Alumno.Estado.INACTIVO, 30);

    // Then
    assertNotEquals(alumno1, alumno2);
  }

  @Test
  void shouldTestEqualsWithNull() {
    // Given
    Alumno alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // Then
    assertNotEquals(null, alumno);
  }

  @Test
  void shouldTestEqualsWithDifferentClass() {
    // Given
    Alumno alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    String otherObject = "Different class";

    // Then
    assertNotEquals(otherObject, alumno);
  }

  @Test
  void shouldTestHashCodeConsistency() {
    // Given
    Alumno alumno1 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);
    Alumno alumno2 = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // Then
    assertEquals(alumno1.hashCode(), alumno2.hashCode());
  }

  @Test
  void shouldTestToString() {
    // Given
    Alumno alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When
    String toStringResult = alumno.toString();

    // Then
    assertNotNull(toStringResult);
    assertTrue(toStringResult.contains("Juan"));
    assertTrue(toStringResult.contains("Pérez"));
    assertTrue(toStringResult.contains("ACTIVO"));
  }

  @Test
  void shouldTestEstadoEnum() {
    // Test ACTIVO state
    assertEquals("ACTIVO", Alumno.Estado.ACTIVO.name());

    // Test INACTIVO state
    assertEquals("INACTIVO", Alumno.Estado.INACTIVO.name());

    // Test valueOf
    assertEquals(Alumno.Estado.ACTIVO, Alumno.Estado.valueOf("ACTIVO"));
    assertEquals(Alumno.Estado.INACTIVO, Alumno.Estado.valueOf("INACTIVO"));
  }
}
