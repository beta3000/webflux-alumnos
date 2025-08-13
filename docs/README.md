# Documentación

Este directorio contiene la documentación del proyecto en AsciiDoc y los diagramas PlantUML.

- Edita `index.adoc` para actualizar contenidos.
- Los diagramas se definen inline en AsciiDoc con bloques `plantuml`.
- Generación:
  - HTML: `./mvnw -Pdocs prepare-package`
  - Salida: `target/docs/index.html` y recursos en `target/docs/diagrams/`.
