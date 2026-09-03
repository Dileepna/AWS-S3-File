# AWS S3 File Microservice

Spring Boot microservice for uploading, downloading, and listing file metadata from an S3-compatible object store.

## Features

- Upload a file to S3
- Download a file from S3
- List metadata for all files in the configured bucket
- Global error handling for file operations

## Tech Stack

- Java 17
- Spring Boot 4.0.8
- Maven
- AWS SDK v2 (S3)

## Project Structure

- `src/main/java/com/s3/microservice/file/S3FileApplication.java` - application entry point
- `src/main/java/com/s3/microservice/file/infrastructure/adapters/active/web/controller/S3FileController.java` - REST APIs
- `src/main/java/com/s3/microservice/file/infrastructure/adapters/active/web/service/FileService.java` - file business logic
- `src/main/resources/application.yaml` - runtime configuration

## Hexagonal Architecture (Ports and Adapters)

This project follows a hexagonal architecture style where the domain/service logic is isolated from delivery and infrastructure details.

- **Core / Application Logic**
  - `src/main/java/com/s3/microservice/file/infrastructure/adapters/active/web/service/FileService.java`
  - Contains use-case style operations for upload, download, and metadata retrieval.

- **Active Adapter (Inbound)**
  - `src/main/java/com/s3/microservice/file/infrastructure/adapters/active/web/controller/S3FileController.java`
  - Exposes HTTP endpoints and translates requests/responses.

- **Driven Adapter (Outbound)**
  - AWS S3 integration is consumed by the service through AWS SDK client abstractions.
  - External object storage details (endpoint, credentials, bucket) are configured in `src/main/resources/application.yaml`.

- **Configuration Layer**
  - `src/main/java/com/s3/microservice/file/config/S3ConfigurationProperties.java`
  - Centralizes infrastructure configuration (`s3.bucket-name`) used by application logic.

- **Cross-cutting Concerns**
  - `src/main/java/com/s3/microservice/file/GlobalExceptionHandler.java`
  - Provides centralized exception-to-HTTP mapping.

Why this helps:

- Keeps business behavior focused and easier to test.
- Allows changing transport (REST) or storage backend (S3-compatible vendors) with minimal impact on core logic.

## Configuration

The service reads settings from `src/main/resources/application.yaml`.

Key properties:

- `server.port`
- `s3.bucket-name`
- `spring.cloud.aws.s3.endpoint`
- `spring.cloud.aws.region.static`
- `spring.cloud.aws.credentials.access-key`
- `spring.cloud.aws.credentials.secret-key`

> Note: Do not commit real credentials for production environments.

## Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The service starts on port `8080` by default.

## API Endpoints

Base path: `/s3`

### 1) Upload File

- **Method**: `POST`
- **Path**: `/s3/file`
- **Form field**: `file`

Example:

```bash
curl -X POST "http://localhost:8080/s3/file" \
  -F "file=@C:/path/to/your/file.txt"
```

### 2) List File Metadata

- **Method**: `GET`
- **Path**: `/s3/files/metadata`

Example:

```bash
curl "http://localhost:8080/s3/files/metadata"
```

### 3) Download File

- **Method**: `GET`
- **Path**: `/s3/file`
- **Query param**: `fileName`

Example:

```bash
curl -o downloaded-file.txt "http://localhost:8080/s3/file?fileName=file.txt"
```

## Running Tests

```bash
mvn test
```

## Notes

- Ensure your S3 endpoint/bucket are reachable before running uploads/downloads.
- The current configuration appears to target an S3-compatible endpoint, not necessarily AWS public S3.
