package rodriguez.ciro.webfluxalumnos.infrastructure.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import rodriguez.ciro.webfluxalumnos.domain.model.Alumno;

class AlumnoEntityTest {

  @Test
  void shouldCreateAlumnoEntityWithAllFields() {
    // Given
    Long id = 1L;
    String nombre = "Juan";
    String apellido = "Pérez";
    String estado = "ACTIVO";
    Integer edad = 25;

    // When
    AlumnoEntity entity = new AlumnoEntity(id, nombre, apellido, estado, edad);

    // Then
    assertEquals(id, entity.getId());
    assertEquals(nombre, entity.getNombre());
    assertEquals(apellido, entity.getApellido());
    assertEquals(estado, entity.getEstado());
    assertEquals(edad, entity.getEdad());
  }

  @Test
  void shouldCreateAlumnoEntityWithDefaultConstructor() {
    // When
    AlumnoEntity entity = new AlumnoEntity();

    // Then
    assertNull(entity.getId());
    assertNull(entity.getNombre());
    assertNull(entity.getApellido());
    assertNull(entity.getEstado());
    assertNull(entity.getEdad());
  }

  @Test
  void shouldCreateFromDomainSuccessfully() {
    // Given
    Alumno alumno = new Alumno(1L, "Juan", "Pérez", Alumno.Estado.ACTIVO, 25);

    // When
    AlumnoEntity entity = AlumnoEntity.fromDomain(alumno);

    // Then
    assertEquals(1L, entity.getId());
    assertEquals("Juan", entity.getNombre());
    assertEquals("Pérez", entity.getApellido());
    assertEquals("ACTIVO", entity.getEstado());
    assertEquals(25, entity.getEdad());
  }

  @Test
  void shouldCreateFromDomainWithInactivoState() {
    // Given
    Alumno alumno = new Alumno(2L, "Ana", "González", Alumno.Estado.INACTIVO, 30);

    // When
    AlumnoEntity entity = AlumnoEntity.fromDomain(alumno);

    // Then
    assertEquals(2L, entity.getId());
    assertEquals("Ana", entity.getNombre());
    assertEquals("González", entity.getApellido());
    assertEquals("INACTIVO", entity.getEstado());
    assertEquals(30, entity.getEdad());
  }

  @Test
  void shouldConvertToDomainSuccessfully() {
    // Given
    AlumnoEntity entity = new AlumnoEntity(1L, "Juan", "Pérez", "ACTIVO", 25);

    // When
    Alumno alumno = entity.toDomain();

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
    AlumnoEntity entity = new AlumnoEntity(2L, "Ana", "González", "INACTIVO", 30);

    // When
    Alumno alumno = entity.toDomain();

    // Then
    assertEquals(2L, alumno.getId());
    assertEquals("Ana", alumno.getNombre());
    assertEquals("González", alumno.getApellido());
    assertEquals(Alumno.Estado.INACTIVO, alumno.getEstado());
    assertEquals(30, alumno.getEdad());
  }

  @Test
  void shouldSetAndGetId() {
    // Given
    AlumnoEntity entity = new AlumnoEntity();
    Long expectedId = 1L;

    // When
    entity.setId(expectedId);

    // Then
    assertEquals(expectedId, entity.getId());
  }

  @Test
  void shouldSetAndGetNombre() {
    // Given
    AlumnoEntity entity = new AlumnoEntity();
    String expectedNombre = "Carlos";

    // When
    entity.setNombre(expectedNombre);

    // Then
    assertEquals(expectedNombre, entity.getNombre());
  }

  @Test
  void shouldSetAndGetApellido() {
    // Given
    AlumnoEntity entity = new AlumnoEntity();
    String expectedApellido = "López";

    // When
    entity.setApellido(expectedApellido);

    // Then
    assertEquals(expectedApellido, entity.getApellido());
  }

  @Test
  void shouldSetAndGetEstado() {
    // Given
    AlumnoEntity entity = new AlumnoEntity();
    String expectedEstado = "ACTIVO";

    // When
    entity.setEstado(expectedEstado);

    // Then
    assertEquals(expectedEstado, entity.getEstado());
  }

  @Test
  void shouldSetAndGetEdad() {
    // Given
    AlumnoEntity entity = new AlumnoEntity();
    Integer expectedEdad = 28;

    // When
    entity.setEdad(expectedEdad);

    // Then
    assertEquals(expectedEdad, entity.getEdad());
  }

  @Test
  void shouldHandleNullValuesInFromDomain() {
    // Given
    Alumno alumnoWithNulls = new Alumno(null, null, null, null, null);

    // When
    AlumnoEntity entity = AlumnoEntity.fromDomain(alumnoWithNulls);

    // Then
    assertNull(entity.getId());
    assertNull(entity.getNombre());
    assertNull(entity.getApellido());
    assertNull(entity.getEstado());
    assertNull(entity.getEdad());
  }

  @Test
  void shouldThrowExceptionWhenToDomainWithInvalidState() {
    // Given
    AlumnoEntity entity = new AlumnoEntity(1L, "Juan", "Pérez", "ESTADO_INVALIDO", 25);

    // When & Then
    assertThrows(IllegalArgumentException.class, entity::toDomain);
  }
}
