# Documentación del Proyecto

Este directorio contiene la documentación completa del proyecto WebFlux Alumnos en formato AsciiDoc con diagramas PlantUML integrados.

## 📁 Estructura de Archivos

```
docs/
├── README.md           # Este archivo (guía de documentación)
└── index.adoc          # Documentación principal en AsciiDoc
```

## 📖 Contenido de la Documentación

La documentación principal (`index.adoc`) incluye:

### 1. **Visión General del Proyecto**
- Características principales y tecnologías utilizadas
- Tabla comparativa de versiones de tecnologías
- Requisitos del sistema

### 2. **Arquitectura Detallada**
- **Diagrama de Arquitectura Hexagonal**: Visión completa de todas las capas
- **Flujo de Arquitectura**: Explicación de la separación de responsabilidades
- Principios de diseño y patrones implementados

### 3. **Modelo de Dominio**
- **Diagrama de Clases Detallado**: Todas las clases, métodos y validaciones
- **Reglas de Negocio**: Validaciones y comportamientos del dominio
- Excepciones y manejo de errores de dominio

### 4. **Casos de Uso y Flujos**
- **Diagrama de Secuencia - Crear Alumno**: Flujo completo con validaciones
- **Diagrama de Secuencia - Obtener Alumnos Activos**: Flujo de consulta paginada
- **Diagrama de Flujo de Errores**: Manejo de excepciones y códigos de error

### 5. **Base de Datos y Persistencia**
- **Esquema de Base de Datos**: Estructura de tablas y relaciones
- Configuración de Liquibase y migraciones
- Configuración de R2DBC para acceso reactivo

### 6. **API REST Detallada**
- **Diagrama de Endpoints**: Visualización de la API
- Documentación completa de DTOs con ejemplos JSON
- Ejemplos de uso con curl
- Códigos de error estandarizados

### 7. **Configuración y Despliegue**
- Configuración de la aplicación
- Requisitos del sistema
- Comandos de build y ejecución
- Generación de documentación

### 8. **Testing y Calidad**
- Estrategia de testing (98 pruebas)
- Configuración de cobertura JaCoCo
- Herramientas de calidad (Spotless, SpotBugs)

### 9. **Monitoreo y Observabilidad**
- Endpoints de Actuator
- Configuración de logs
- Consideraciones de rendimiento

## 🎨 Diagramas Incluidos

La documentación incluye **7 diagramas PlantUML** que se generan automáticamente:

1. **arquitectura-hexagonal.png** - Arquitectura completa con todas las capas
2. **dominio-detallado.png** - Modelo de dominio con métodos y validaciones
3. **secuencia-crear-alumno.png** - Flujo detallado de creación de alumno
4. **secuencia-obtener-activos.png** - Flujo de consulta de alumnos activos
5. **flujo-errores.png** - Diagrama de manejo de errores
6. **esquema-bd.png** - Esquema de base de datos
7. **api-endpoints.png** - Visualización de endpoints de la API

## 🔨 Generar Documentación

### Comando Básico
```bash
./mvnw -Pdocs clean prepare-package
```

### Archivos Generados
```
target/docs/
├── index.html              # Documentación HTML principal
├── diagrams/               # Diagramas generados como PNG
│   ├── arquitectura-hexagonal.png
│   ├── dominio-detallado.png
│   ├── secuencia-crear-alumno.png
│   ├── secuencia-obtener-activos.png
│   ├── flujo-errores.png
│   ├── esquema-bd.png
│   └── api-endpoints.png
└── .asciidoctor/          # Archivos temporales de AsciiDoctor
```

## ⚙️ Configuración Técnica

### Plugin de AsciiDoctor
El proyecto incluye un perfil Maven (`docs`) que configura:

- **AsciiDoctor Maven Plugin 3.0.0**
- **AsciiDoctor Diagram 2.3.1** para soporte de PlantUML
- Generación de HTML5 con tema por defecto
- Índice lateral navegable (`toc: left`)
- Numeración automática de secciones
- Iconos Font Awesome

### Atributos de AsciiDoc
```asciidoc
:toc: left                  # Índice a la izquierda
:sectnums:                  # Numeración de secciones
:icons: font                # Iconos Font Awesome
:source-highlighter: highlightjs  # Resaltado de sintaxis
:doctype: book              # Tipo de documento libro
:imagesdir: diagrams        # Directorio de imágenes
```

## 📝 Editar Documentación

### Estructura del Archivo Principal
El archivo `index.adoc` está organizado en secciones:

```asciidoc
= WebFlux Alumnos - Documentación Completa
== Resumen del Proyecto
== Arquitectura del Sistema
== Modelo de Dominio Detallado
== Casos de Uso y Flujos de Operación
== Base de Datos y Persistencia
== API REST Detallada
== Configuración y Despliegue
== Testing y Calidad
== Monitoreo y Observabilidad
```

### Agregar Nuevos Diagramas
Para agregar un nuevo diagrama PlantUML:

```asciidoc
[plantuml, nombre-diagrama, png]
----
@startuml
' Tu código PlantUML aquí
@enduml
----
```

### Sintaxis Útil
```asciidoc
// Tabla
[cols="2,3,2"]
|===
|Columna 1 |Columna 2 |Columna 3
|Valor 1   |Valor 2   |Valor 3
|===

// Código con resaltado
[source,json]
----
{
  "example": "json"
}
----

// Nota/Callout
NOTE: Esto es una nota importante

// Lista con íconos
- ✅ Elemento completado
- 🔄 Elemento en progreso
- ❌ Elemento pendiente
```

## 🚀 Mejores Prácticas

### 1. **Mantener Sincronización**
- Actualizar documentación cuando se modifique código
- Revisar que los diagramas reflejen la arquitectura actual
- Verificar que los ejemplos de código funcionen

### 2. **Claridad y Consistencia**
- Usar terminología consistente en toda la documentación
- Incluir ejemplos prácticos y casos de uso reales
- Mantener diagrams simples pero informativos

### 3. **Generación Regular**
- Ejecutar generación de documentos antes de commits importantes
- Verificar que todos los diagramas se generen correctamente
- Revisar el HTML generado para asegurar el formato correcto

### 4. **Versionado**
- Documentar cambios significativos en la arquitectura
- Mantener ejemplos actualizados con las versiones de dependencias
- Incluir notas de migración para cambios breaking

## 🔗 Referencias

- [AsciiDoc User Manual](https://asciidoc.org/userguide.html)
- [PlantUML Documentation](https://plantuml.com/)
- [AsciiDoctor Maven Plugin](https://docs.asciidoctor.org/maven-tools/latest/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
