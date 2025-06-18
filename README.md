# Backend Microservice

Este es un microservicio para la gestión de pedidos en una plataforma e-commerce que maneja:
- Múltiples vendedores
- Centros de distribución
- Inventarios
- Procesamiento de pagos
- Integración asíncrona mediante eventos Kafka

Construido con **Java 17** y **Spring Boot 3.3.x** siguiendo principios de **arquitectura hexagonal**, **buenas prácticas de ingeniería de software** y **documentación profesional**.

---

## Tecnologías utilizadas

- Java 17.
- Spring Boot 3.3.12
- PostgreSQL como base de datos relacional.
- Kafka para integración asíncrona.
- Maven para gestión de dependencias.
- JWT para autenticación.
- JUnit & Mockito para pruebas unitarias.
- Spring Web + Validation.
- Arquitectura hexagonal (Ports & Adapters).
- Logging con SLF4J.

---

## Arquitectura hexagonal aplicada

Este proyecto sigue el patrón de arquitectura hexagonal (Ports and Adapters), organizando el código por responsabilidad y permitiendo desacoplamiento entre:
- La lógica de dominio
- La infraestructura
- Los casos de uso

```
├── domain
│   ├── model          -> Entidades de dominio puras
│   ├── service        -> Servicios con lógica de negocio pura
│   ├── gateway        -> Interfaces para persistencia y Kafka
│   └── util            -> clase de utilidad no instanciable
├── application
│   ├── dto            -> Objetos de transferencia de datos.
│   ├── usecase        -> Casos de uso con orquestación de lógica
│   ├── mapper         -> Mappers entre entidades y DTOs
│   └── exception      -> Manejo de errores y validaciones
├── infrastructure
│   ├── entity         -> Entidades JPA para persistencia
│   ├── adapter        -> Adaptadores para base de datos y Kafka
│   ├── controller     -> REST Controllers
│   ├── config         -> Seguridad, Logging, APIResponse
│   └── security       -> JWT utils y filtros
└── resources
    └── application.properties
```

---

## Objetivo de cada capa

### Domain
- Define el modelo del negocio con clases puras sin dependencias externas.
- Aplica principios DDD: solo datos y reglas propias del dominio.

### Application
- Contiene los casos de uso de negocio (ej. crear orden, cancelar orden).
- Define los DTO, mappers y exception handlers.
- Aplica el patrón Use Case Driven.

### Infrastructure
- Implementa la lógica técnica para que el dominio funcione:
  - Repositorios JPA
  - Controladores REST
  - Configuraciones de seguridad y Kafka
- Aplica el patrón Adapter.

---

## Funcionalidades implementadas

### CRUD de órdenes
- Crear orden (`POST /orders`)
- Obtener orden por ID (`GET /orders/{id}`)
- Obtener por cliente (`GET /orders/customer/{customerId}`)
- Actualizar items (`PUT /orders/{id}/items`)
- Cancelar orden (`DELETE /orders/{id}`)
- Buscar por filtros (`GET /orders/search?status=...&date=...`)

### Integración con Kafka
- Al crear una orden, se envía un evento `OrderToPaymentEvent` al `topic: order-payment-events`.
- Existe un Consumer que escucha el mismo topic y procesa el evento para simulación de pagos.

### Seguridad JWT
- Se genera token JWT al hacer login (AuthController opcional).
- Todos los endpoints están protegidos (excepto `/auth/**`).

### Validaciones y errores
- Se usa `@Valid`, `@NotNull`, `@NotEmpty` en DTOs.
- `@RestControllerAdvice` centraliza los errores: devuelve estructura clara con `ApiResponse`.

### Logging estructurado
- Cada capa tiene trazabilidad: controllers, usecases, Kafka, errores.

---

## Evidencia de funcionalidad de Kafka

- Mensajes enviados correctamente por el producer
- Mensajes recibidos correctamente por el consumer
![Captura de pantalla 2025-06-17 213056](https://github.com/user-attachments/assets/348eac1b-e97f-4c36-866c-b7df7b8606f6)
- Logs del procesamiento exitoso del evento de orden:
![Captura de pantalla 2025-06-17 213146](https://github.com/user-attachments/assets/c18fe59d-ee4c-4451-99b3-09893a368b94)
![Captura de pantalla 2025-06-17 213204](https://github.com/user-attachments/assets/6af73ae2-cfac-4214-8a34-0aeccdcf22d4)

---

## Pruebas implementadas

### Unitarias con JUnit + Mockito
- Se implementaron pruebas para:
  - `CreateOrderUseCase`
  - `CancelOrderUseCase`

### Integración con MockMvc
- `OrderControllerTest` cubre:
  - Escenarios de éxito (200 OK)
  - Escenarios de error (400, 404)
  - Validaciones de entrada

---

## Documentación de la API

### Con Postman
- Se documentaron todos los endpoints de forma interactiva.
- Incluye:
  - Headers JWT
  - Ejemplos de peticiones/respuestas
  - 
---

## Buenas prácticas aplicadas

- Principios SOLID.
- Separación de responsabilidades por capas.
- Arquitectura limpia con Ports & Adapters.
- Lógica de negocio centralizada.
- Evitar lógica en entidades JPA.
- Manejo global de excepciones y validaciones claras.
- Pruebas automáticas y estructura de nombres estándar.
- Variables, métodos y clases con nombres descriptivos.
