# WebFlux Alumnos

Servicio reactivo (Spring WebFlux) para gestionar alumnos con arquitectura hexagonal, persistencia R2DBC + H2 y migraciones con Liquibase. Calidad integrada con Spotless, SpotBugs/FindSecBugs y cobertura JaCoCo. Documentación generada con Asciidoctor + PlantUML.

## Requisitos
- Java 17
- Maven 3.9+

## Ejecutar rápidamente
- Compilar y ejecutar pruebas unitarias
  ```bash
  ./mvnw clean test
  ```

- Ejecutar toda la verificación del proyecto (format, análisis estático, pruebas, cobertura, empaquetado)

  ```bash
  ./mvnw verify
  ```

- Arrancar la aplicación en modo local

  ```bash
  ./mvnw spring-boot:run
  ```

La base H2 se inicializa con Liquibase al inicio (datos de ejemplo incluidos).

## Endpoints principales (resumen)
- POST `/api/alumnos` — Crea un alumno.
- GET `/api/alumnos/activos?page=&size=` — Lista alumnos activos paginados.

Códigos/errores estandarizados expuestos por el `GlobalExceptionHandler` (p. ej.: `ALUMNO_INVALIDO`, `ALUMNO_YA_EXISTE`, `PARAMETROS_INVALIDOS`).

## Cobertura
Se genera con JaCoCo en `target/site/jacoco/index.html` tras `mvn verify`.

## Documentación (AsciiDoc + PlantUML)
Genera HTML + diagramas:

```bash
./mvnw -Pdocs prepare-package
```

Salida en:
- `target/docs/index.html`
- `target/docs/diagrams/*.png`

Fuentes en `docs/`.

## Calidad y estilo
- Formato: Spotless (Java + XML).
- Análisis estático: SpotBugs + FindSecBugs.
- Lint/Javac: `-Xlint:deprecation` y `-Xlint:unchecked`.

## Arquitectura (breve)
- Dominio: modelo y reglas de negocio (arquitectura hexagonal).
- Aplicación: casos de uso (servicios reactivamente con Mono/Flux).
- Infraestructura: adaptadores Web (Spring WebFlux) y persistencia (R2DBC).
- Migraciones: Liquibase (archivos en `src/main/resources/db/`).

## Estructura del proyecto
```
src/
  main/
    java/rodriguez/...         # domain, application, infrastructure
    resources/
      application.yaml
      db/                      # liquibase changelogs y datos
  test/
    java/                      # pruebas unitarias e integración

docs/                          # AsciiDoc + PlantUML
```

## Notas
- Las operaciones reactivas usan `Mono.defer`/`Flux.defer` cuando aplica para alinear la emisión con la suscripción.
- Los diagramas de arquitectura, dominio y endpoints se renderizan automáticamente al generar la documentación.
