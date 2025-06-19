
# Calculadora de Pedidos

Proyecto Java para calcular el total de un pedido con descuentos y diferentes tipos de envío. Incluye pruebas unitarias y está preparado para ejecutarse con Docker y Docker Compose.

---

## Pasos para ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://tu-repositorio.git
cd calculadora-pedidos
```

### 2. Compilar el proyecto y ejecutar tests con Maven

```bash
mvn clean test
```

### 3. Construir la imagen Docker

```bash
docker build -t calculadora-pedidos .
```

### 4. Ejecutar tests dentro del contenedor Docker

```bash
docker run --rm calculadora-pedidos
```

### 5. Levantar el proyecto con Docker Compose

```bash
docker-compose up --build
```

## Código fuente principal

### DescuentoRepository.java

```java
package com.ejemplo;

public class DescuentoRepository {
    public double obtenerPorcentaje(String codigo) {
        if ("PROMO10".equals(codigo)) {
            return 0.10;
        }
        return 0.0;
    }
}
```

### PedidoService.java

```java
package com.ejemplo;

public class PedidoService {
    private DescuentoRepository repository;

    public PedidoService(DescuentoRepository repository) {
        this.repository = repository;
    }

    public double calcularTotal(double subtotal, String codigoDescuento, boolean envioExpress) {
        double descuento = repository.obtenerPorcentaje(codigoDescuento);
        double envio = envioExpress ? 20.0 : 10.0;
        return (subtotal * (1 - descuento)) + envio;
    }
}
```

### PedidoServiceTest.java

```java
package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    DescuentoRepository repo = new DescuentoRepository();
    PedidoService service = new PedidoService(repo);

    @Test
    void testSinDescuentoYEnvioNormal() {
        double total = service.calcularTotal(100, "", false);
        assertEquals(110.0, total);
    }

    @Test
    void testConDescuentoYEnvioExpress() {
        double total = service.calcularTotal(100, "PROMO10", true);
        assertEquals(110.0, total);
    }

    @Test
    void testConDescuentoYEnvioNormal() {
        double total = service.calcularTotal(200, "PROMO10", false);
        assertEquals(190.0, total);
    }

    @Test
    void testSinDescuentoYEnvioExpress() {
        double total = service.calcularTotal(150, "", true);
        assertEquals(170.0, total);
    }
}
```

## Dockerfile

```dockerfile
FROM maven:3.8.7-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
```

## docker-compose.yml

```yaml
version: '3.8'

services:
  calculadora-pedidos:
    build: .
    container_name: calculadora-pedidos
    ports:
      - "8080:8080"
    volumes:
      - ./:/app
    command: mvn test
```
