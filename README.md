# URL Shortener API

A Spring Boot REST API that creates short URLs, stores URL mappings in PostgreSQL, and redirects requests from a short URL to the original URL.

## Requirements

- Java 17 or newer
- Docker and Docker Compose for the containerized setup
- PostgreSQL for running the application directly on the host

Java 17 must be installed and available to Maven. Configure `JAVA_HOME` to point to the Java 17 installation if `./mvnw test` or `./mvnw spring-boot:run` reports that Java cannot be found. Verify the setup with:

```bash
java -version
```

## Configuration

The application requires these environment variables:

| Variable | Description |
| --- | --- |
| `POSTGRES_DB` | PostgreSQL database name |
| `POSTGRES_USER` | PostgreSQL username |
| `POSTGRES_PASSWORD` | PostgreSQL password |
| `SPRING_DATASOURCE_URL` | JDBC URL for the PostgreSQL instance when running outside Compose |

Do not commit real credentials. Use local environment variables or a local `.env` file that is not committed to version control.

## Testing

Run the test suite from the project root:

```bash
./mvnw test
```

On Windows, the Maven wrapper can also be run with:

```powershell
.\mvnw.cmd test
```

The tests use the project's test configuration and H2 where applicable, so a separate PostgreSQL instance is not required for the test suite.

## Run With Docker Compose

1. Create a local `.env` file in the project root. Docker Compose automatically loads this file:

```dotenv
POSTGRES_DB=urlshortener
POSTGRES_USER=urlshortener_user
POSTGRES_PASSWORD=change-this-local-password
```

2. Start PostgreSQL and the API:

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`. PostgreSQL is exposed to the host on port `5433` and remains available to the API inside the Compose network on port `5432`.

You can also inject the variables directly instead of using `.env`:

```bash
POSTGRES_DB=urlshortener POSTGRES_USER=urlshortener_user POSTGRES_PASSWORD=change-this-local-password docker compose up --build
```

On PowerShell:

```powershell
$env:POSTGRES_DB = "urlshortener"
$env:POSTGRES_USER = "urlshortener_user"
$env:POSTGRES_PASSWORD = "change-this-local-password"
docker compose up --build
```

Stop the services with `Ctrl+C`. To remove the containers and the persisted database volume:

```bash
docker compose down -v
```

## Run Directly With a Separate PostgreSQL Database

Start or provision your own PostgreSQL database outside Docker, then provide your own database credentials and JDBC URL to the application. The URL must point to the PostgreSQL host and port reachable from the machine running Spring Boot.

Bash:

```bash
export POSTGRES_DB=urlshortener
export POSTGRES_USER=urlshortener_user
export POSTGRES_PASSWORD=change-this-local-password
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/urlshortener
./mvnw spring-boot:run
```

PowerShell:

```powershell
$env:POSTGRES_DB = "urlshortener"
$env:POSTGRES_USER = "urlshortener_user"
$env:POSTGRES_PASSWORD = "change-this-local-password"
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/urlshortener"
.\mvnw.cmd spring-boot:run
```

Use your own database name, username, password, host, and port as appropriate. The database user must have permission to create and update the application's tables because Hibernate is configured with `ddl-auto: update`.

## API Endpoints

### Create a short URL

```bash
curl -X POST http://localhost:8080/ \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://example.com"}'
```

### List URL mappings

```bash
curl "http://localhost:8080/?page=0&size=5"
```

### Get, delete, or redirect a mapping

```bash
curl http://localhost:8080/1
curl -X DELETE http://localhost:8080/1
curl -i http://localhost:8080/url/abc12
```

The generated short URL is a five-character alphanumeric value. The API listens on port `8080` by default.
