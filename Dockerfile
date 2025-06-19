# Usa una imagen base de OpenJDK con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo pom.xml primero para aprovechar el cache de Docker
COPY pom.xml .

# Descarga las dependencias del proyecto (incluyendo las de test)
RUN mvn dependency:go-offline

# Copia el resto del código fuente
COPY src ./src

# Compila el proyecto (omite los tests en la fase de compilación para rapidez en desarrollo)
RUN mvn clean install -DskipTests