# WebFlux Alumnos - GitHub Copilot Instructions

**ALWAYS reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.**

WebFlux Alumnos is a reactive Spring Boot application for student management using Spring WebFlux, R2DBC + H2 database, Liquibase migrations, and hexagonal architecture. Built with comprehensive quality tools including Spotless, SpotBugs/FindSecBugs, and JaCoCo coverage.

## Prerequisites and Setup

**CRITICAL: Ensure Java 17 is installed before proceeding.**

- Make Maven wrapper executable: `chmod +x mvnw`
- Verify Java version: `java -version` (must be Java 17+)
- Verify Maven wrapper: `./mvnw --version`

## Core Build Commands - NEVER CANCEL

**CRITICAL TIMING WARNINGS: Set timeouts of 10+ minutes for all build commands. NEVER CANCEL builds.**

### Run Tests
```bash
./mvnw clean test
```
- **Duration**: ~3-4 minutes. **NEVER CANCEL** - Set timeout to 10+ minutes.
- Runs all 98 unit and integration tests
- Tests cover domain models, services, controllers, adapters, and full integration scenarios

### Full Verification (Recommended)
```bash
./mvnw verify
```
- **Duration**: ~3-4 minutes. **NEVER CANCEL** - Set timeout to 10+ minutes.
- Runs complete build pipeline: compile → spotless format check → tests → spotbugs static analysis → jacoco coverage
- **CRITICAL**: This is the main quality gate command - always run before committing changes

### Code Formatting
```bash
./mvnw spotless:apply
```
- **Duration**: ~3-5 seconds
- Auto-formats Java, XML, and YAML files using Google Java Format
- **ALWAYS run this before committing** if Spotless check fails during verify

## Application Runtime

### Start Application
```bash
./mvnw spring-boot:run
```
- **Duration**: Starts in ~4 seconds
- Application runs on **http://localhost:8080**
- H2 database auto-initializes with Liquibase migrations and sample data
- **Stop with Ctrl+C**

### API Endpoints (Manual Validation Required)
**CRITICAL**: Always manually test these endpoints after making changes:

1. **List Active Students (paginated)**:
   ```bash
   curl "http://localhost:8080/api/alumnos/activos?page=1&size=5"
   ```
   - Expected: JSON array of 5 active students
   - Note: Page numbers start at 1, not 0

2. **Create New Student**:
   ```bash
   curl -X POST "http://localhost:8080/api/alumnos" \
     -H "Content-Type: application/json" \
     -d '{"id":999,"nombre":"Test","apellido":"Student","edad":22,"estado":"ACTIVO"}'
   ```
   - Expected: 200 response, student created
   - Required fields: id, nombre, apellido, edad, estado

3. **Test Error Handling**:
   ```bash
   curl "http://localhost:8080/api/alumnos/activos?page=0&size=5"
   ```
   - Expected: `{"codigo":"PARAMETROS_INVALIDOS","mensaje":"El número de página debe ser mayor o igual a 1"}`

## Quality and Coverage Reports

### JaCoCo Coverage Report
- Generated after `./mvnw verify`
- **Location**: `target/site/jacoco/index.html`
- Open in browser to view detailed coverage metrics

### SpotBugs Static Analysis
- Runs automatically during `./mvnw verify`
- Includes FindSecBugs security analysis
- **Configuration**: `spotbugs-include.xml`, `spotbugs-exclude.xml`

## Project Structure and Navigation

### Key Directories
```
src/main/java/rodriguez/ciro/webfluxalumnos/
├── WebfluxAlumnosApplication.java          # Main Spring Boot class
├── domain/                                  # Domain layer (business logic)
│   ├── model/Alumno.java                   # Core domain model
│   └── exception/                          # Domain exceptions
├── application/                            # Application layer (use cases)
│   ├── port/in/                           # Input ports (interfaces)
│   ├── port/out/                          # Output ports (interfaces)
│   └── service/                           # Service implementations
└── infrastructure/                         # Infrastructure layer (adapters)
    └── adapter/
        ├── in/web/                        # Web controllers and DTOs
        └── out/persistence/               # Database adapters and entities

src/main/resources/
├── application.yaml                        # Main configuration
└── db/changelog/                          # Liquibase migration files

src/test/java/                             # Mirrors main structure
└── integration/                           # Integration tests
```

### Key Files to Monitor
- **After changing domain models**: Always check `AlumnoEntity.java` and related adapters
- **After changing API contracts**: Always check `AlumnoController.java` and DTO classes
- **After changing business logic**: Always run full test suite with `./mvnw verify`

## Validation Scenarios (MANDATORY)

**CRITICAL**: After making any changes, ALWAYS run through these complete validation scenarios:

### 1. Build Validation
```bash
./mvnw verify
```
- **Must pass**: All 98 tests, Spotless format check, SpotBugs analysis
- **Duration**: ~3-4 minutes. **NEVER CANCEL**.

### 2. Application Functionality Validation
```bash
# Start application
./mvnw spring-boot:run

# In another terminal, test endpoints:
curl "http://localhost:8080/api/alumnos/activos?page=1&size=3"
curl -X POST "http://localhost:8080/api/alumnos" \
  -H "Content-Type: application/json" \
  -d '{"id":888,"nombre":"Validation","apellido":"Test","edad":25,"estado":"ACTIVO"}'

# Stop application with Ctrl+C
```

### 3. Code Quality Validation
```bash
# Format code
./mvnw spotless:apply

# Verify quality gates pass
./mvnw verify

# Check coverage report exists
ls -la target/site/jacoco/index.html
```

## Common Development Workflows

### Making Code Changes
1. **Always start with**: `./mvnw clean test` to ensure baseline works
2. **Make minimal changes**
3. **Run formatting**: `./mvnw spotless:apply`
4. **Run full verification**: `./mvnw verify` (NEVER CANCEL - wait 10+ minutes)
5. **Test application manually** using validation scenarios above
6. **Check coverage report** if coverage requirements exist

### Working with Database
- **H2 Console**: Not properly exposed (gives internal server error)
- **Database initialization**: Automatic via Liquibase on startup
- **Sample data**: Loaded from `src/main/resources/db/changelog/alumnos-data.csv`
- **Schema changes**: Add new Liquibase changesets in `src/main/resources/db/changelog/`

### Hexagonal Architecture Guidelines
- **Domain layer**: Pure business logic, no external dependencies
- **Application layer**: Use case implementations, coordinates domain and infrastructure
- **Infrastructure layer**: External adapters (web, database), depends on application/domain

## Error Codes and Handling
- **ALUMNO_INVALIDO**: Domain validation failures
- **ALUMNO_YA_EXISTE**: Duplicate ID during creation
- **PARAMETROS_INVALIDOS**: Invalid request parameters (e.g., page < 1)
- **DATOS_INVALIDOS**: JSON binding errors
- **ERROR_INTERNO**: Internal server errors

## Important Notes
- **Page numbers start at 1**, not 0 (validated with API testing)
- **All reactive operations use Mono/Flux** - understand reactive patterns when making changes
- **Documentation generation**: `./mvnw -Pdocs prepare-package` does not work (profile doesn't exist)
- **File permissions**: Maven wrapper needs executable permissions (`chmod +x mvnw`)

## Repository File Overview
```
mvnw, mvnw.cmd              # Maven wrapper (make executable)
pom.xml                     # Build configuration with quality tools
.prettierrc.yml             # Prettier configuration for YAML files
spotbugs-include.xml        # SpotBugs analysis inclusion rules
spotbugs-exclude.xml        # SpotBugs analysis exclusion rules
docs/index.adoc             # AsciiDoc documentation source
README.md                   # Project overview and quick start
```

**Remember**: ALWAYS run the validation scenarios after changes and NEVER CANCEL long-running build commands.