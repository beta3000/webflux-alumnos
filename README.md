# WebFlux Alumnos

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Servicio reactivo para gestión de estudiantes construido con **Spring WebFlux** que implementa **arquitectura hexagonal**, persistencia reactiva con **R2DBC + H2**, migraciones con **Liquibase** y herramientas de calidad integradas. Incluye documentación completa generada con **AsciiDoc + PlantUML**.

## 🚀 Características Principales

- **Arquitectura Hexagonal**: Separación clara de responsabilidades en capas (dominio, aplicación, infraestructura)
- **Programación Reactiva**: Spring WebFlux con Mono/Flux para operaciones no bloqueantes
- **Persistencia Reactiva**: R2DBC con base de datos H2 en memoria
- **Control de Versiones BD**: Liquibase para migraciones automatizadas
- **Calidad de Código**: Spotless, SpotBugs/FindSecBugs, JaCoCo
- **Documentación Rica**: AsciiDoc con diagramas PlantUML integrados
- **API REST**: Endpoints reactivos con manejo de errores estandarizado

## 📋 Requisitos del Sistema

| Componente | Versión Mínima | Recomendada |
|------------|----------------|-------------|
| Java       | 17             | 17+         |
| Maven      | 3.9+           | 3.9+        |
| Memoria    | 512 MB         | 1 GB        |

## ⚡ Inicio Rápido

### 1. Clonar y Preparar
```bash
git clone <repository-url>
cd webflux-alumnos
chmod +x mvnw
```

### 2. Ejecutar Pruebas
```bash
# Ejecutar todas las pruebas (98 tests)
./mvnw clean test

# Verificación completa (tests + calidad + cobertura)
./mvnw clean verify
```

### 3. Ejecutar Aplicación
```bash
# Modo desarrollo
./mvnw spring-boot:run

# Aplicación disponible en: http://localhost:8080
```

### 4. Probar API
```bash
# Obtener alumnos activos (página 1, 5 elementos)
curl "http://localhost:8080/api/alumnos/activos?page=1&size=5"

# Crear nuevo alumno
curl -X POST "http://localhost:8080/api/alumnos" \
  -H "Content-Type: application/json" \
  -d '{"id":999,"nombre":"Test","apellido":"Student","edad":22,"estado":"ACTIVO"}'
```

## 📚 Documentación Completa

### Generar Documentación
```bash
# Generar HTML + diagramas PlantUML
./mvnw -Pdocs clean prepare-package

# Archivos generados:
# - target/docs/index.html        (documentación principal)
# - target/docs/diagrams/*.png    (diagramas)
```

### Contenido de la Documentación
- **Arquitectura Hexagonal**: Diagramas detallados de componentes y capas
- **Flujos de Operación**: Diagramas de secuencia para casos de uso principales
- **Modelo de Dominio**: Clases, validaciones y reglas de negocio
- **API REST**: Endpoints, DTOs, ejemplos y códigos de error
- **Base de Datos**: Esquema, migraciones y configuración
- **Testing y Calidad**: Estrategias, cobertura y herramientas
- **Despliegue**: Configuración, requisitos y comandos

## 🏗️ Arquitectura del Sistema

### Estructura del Proyecto
```
src/main/java/rodriguez/ciro/webfluxalumnos/
├── domain/                          # 🏛️ Capa de Dominio
│   ├── model/Alumno.java           # Modelo de negocio
│   └── exception/                   # Excepciones de dominio
├── application/                     # ⚙️ Capa de Aplicación  
│   ├── port/in/                    # Puertos de entrada (casos de uso)
│   ├── port/out/                   # Puertos de salida (repositorios)
│   └── service/                    # Implementación de casos de uso
└── infrastructure/                  # 🔌 Capa de Infraestructura
    ├── adapter/in/web/             # Adaptadores Web (REST)
    └── adapter/out/persistence/    # Adaptadores de persistencia (R2DBC)
```

### Principios de Diseño
- **Dominio Independiente**: Sin dependencias externas en la capa de dominio
- **Inversión de Dependencias**: Implementación en infraestructura, interfaces en aplicación
- **Programación Reactiva**: Uso de Mono/Flux para operaciones asíncronas
- **Testing Integral**: Pruebas unitarias y de integración por cada capa

## 🔧 API REST

### Endpoints Disponibles

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| POST   | `/api/alumnos` | Crear alumno | Body: AlumnoRequestDTO |
| GET    | `/api/alumnos/activos` | Listar activos | `page` (≥1), `size` (>0) |

### Ejemplo de Request/Response
```json
// POST /api/alumnos
{
  "id": 100,
  "nombre": "María",
  "apellido": "González", 
  "estado": "ACTIVO",
  "edad": 22
}

// Response 200 OK
{
  "id": 100,
  "nombre": "María",
  "apellido": "González",
  "estado": "ACTIVO", 
  "edad": 22
}
```

### Códigos de Error
| Código | HTTP | Descripción |
|--------|------|-------------|
| `ALUMNO_INVALIDO` | 400 | Validación de dominio fallida |
| `ALUMNO_YA_EXISTE` | 409 | ID duplicado en creación |
| `PARAMETROS_INVALIDOS` | 400 | Parámetros de query inválidos |
| `DATOS_INVALIDOS` | 400 | JSON malformado |
| `ERROR_INTERNO` | 500 | Error interno del servidor |

## 🧪 Testing y Calidad

### Suite de Pruebas (98 tests)
```bash
# Ejecutar pruebas con reporte detallado
./mvnw clean test

# Resultados esperados:
# - Tests run: 98, Failures: 0, Errors: 0, Skipped: 0
# - Tiempo: ~3-4 minutos
```

### Cobertura de Código
```bash
# Generar reporte de cobertura JaCoCo
./mvnw clean verify

# Ver reporte: target/site/jacoco/index.html
```

### Herramientas de Calidad
```bash
# Aplicar formato de código (Spotless)
./mvnw spotless:apply

# Análisis estático (SpotBugs + FindSecBugs)
./mvnw spotbugs:check
```

## 🗄️ Base de Datos

### Configuración H2
- **URL**: `r2dbc:h2:mem:///testdb`
- **Usuario**: `sa` (sin contraseña)
- **Inicialización**: Automática con Liquibase
- **Datos de ejemplo**: Cargados desde `alumnos-data.csv`

### Migraciones
```
src/main/resources/db/changelog/
├── db.changelog-master.yaml          # Archivo principal
├── 001-create-alumnos-table.yaml     # Creación de tabla
└── alumnos-data.csv                  # Datos de ejemplo
```

## 🚀 Despliegue

### Desarrollo
```bash
# Ejecutar en modo desarrollo con hot-reload
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```

### Producción
```bash
# Crear JAR ejecutable
./mvnw clean package -DskipTests

# Ejecutar aplicación
java -jar target/webflux-alumnos-0.0.1-SNAPSHOT.jar
```

### Configuración de Ambiente
```yaml
# application.yaml
server:
  port: 8080

spring:
  r2dbc:
    url: r2dbc:h2:mem:///testdb
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml

logging:
  level:
    rodriguez.ciro.webfluxalumnos: DEBUG
```

## 📊 Monitoreo

### Actuator Endpoints
- `/actuator/health` - Estado de salud
- `/actuator/metrics` - Métricas de la aplicación
- `/actuator/info` - Información del build

### OpenAPI/Swagger
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`

## 🤝 Contribución

1. Fork el repositorio
2. Crear branch para feature (`git checkout -b feature/nueva-funcionalidad`)
3. Ejecutar tests y verificaciones (`./mvnw verify`)
4. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
5. Push al branch (`git push origin feature/nueva-funcionalidad`)
6. Crear Pull Request

### Estándares de Código
- **Formato**: Spotless (Google Java Format)
- **Testing**: Cobertura >90%
- **Commits**: Mensajes descriptivos en español
- **Documentation**: Actualizar AsciiDoc para cambios significativos

## 📝 Notas Técnicas

- **Paginación**: Los números de página inician en 1 (no 0)
- **Reactive Patterns**: Uso de `Mono.defer`/`Flux.defer` para lazy evaluation
- **Exception Handling**: GlobalExceptionHandler maneja todos los errores
- **Database**: H2 en memoria se reinicia con cada ejecución
- **Build Time**: Verificación completa toma ~3-4 minutos

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
