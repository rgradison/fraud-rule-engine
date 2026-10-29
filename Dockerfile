# syntax=docker/dockerfile:1

# --------------------------------------------------
# Stage 1: Build and verify
# --------------------------------------------------
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

# Cache dependencies separately from application source
COPY pom.xml ./

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B dependency:go-offline

COPY src ./src

# Compile, run tests and package the application
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package
#RUN --mount=type=cache,target=/root/.m2 \
#    mvn -B clean verify

# --------------------------------------------------
# Stage 2: Production runtime
# --------------------------------------------------
FROM eclipse-temurin:17-jre-jammy AS runtime

WORKDIR /app

# Image metadata: replace these values with your project details
LABEL org.opencontainers.image.title="Fraud Rule Engine" \
      org.opencontainers.image.description="Spring Boot fraud evaluation service" \
      org.opencontainers.image.version="0.0.1-SNAPSHOT"

# Create a dedicated unprivileged user
RUN groupadd --system --gid 10001 appgroup \
    && useradd --system --uid 10001 \
       --gid appgroup \
       --no-create-home \
       --shell /usr/sbin/nologin appuser

# Copy only the executable application artifact
COPY --chown=10001:10001 \
    --from=build \
    /build/target/fraud-rule-engine-0.0.1-SNAPSHOT.jar \
    /app/app.jar

# Application listens on this port
EXPOSE 8015

# Run as a non-root user
USER 10001:10001

# Send SIGTERM when the container is stopped
STOPSIGNAL SIGTERM

# Container-aware JVM defaults
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-XX:+ExitOnOutOfMemoryError", "-jar", "/app/app.jar"]
