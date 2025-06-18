# Backend Microservice

Este es un microservicio para la gestión de pedidos en una plataforma e-commerce que maneja:
- Múltiples vendedores.
- Centros de distribución.
- Inventarios.
- Procesamiento de pagos.
- Integración asíncrona mediante eventos Kafka.

Funcionalidad Principal: Gestión de Órdenes.
- El núcleo funcional del microservicio es la gestión de órdenes de compra dentro de una plataforma e-commerce. Permite crear, consultar, actualizar, cancelar y buscar pedidos realizados por los clientes.

- Aunque el enfoque principal del sistema es el manejo de órdenes, la arquitectura implementada deja abierta la posibilidad de ampliar la solución hacia otras funcionalidades, como inventario, pagos o envíos, integrando nuevos casos de uso y microservicios si el dominio lo requiere.

---

## Tecnologías utilizadas

- Java 17.
- Spring Boot 3.3.12
- PostgreSQL como base de datos.
- JPA para la persistencia de datos para la base de datos.
- Base de datos relacional.
- Kafka para integración asíncrona.
- Maven para gestión de dependencias.
- JWT para autenticación.
- JUnit & Mockito para pruebas unitarias.
- Spring Web + Validation.
- Arquitectura hexagonal.
- Logging con SLF4J.

---

## Buenas prácticas aplicadas

- Principios SOLID.
- Separación de responsabilidades por capas.
- Arquitectura hexagonal.
- Lógica de negocio centralizada.
- Evitar lógica en entidades JPA.
- Manejo global de excepciones y validaciones claras.
- Pruebas automáticas y estructura de nombres estándar.
- Variables, métodos y clases con nombres descriptivos.

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
│   ├── adapter        -> Adaptadores para base de datos y Kafka -> Aquí se encuentra los topic de Kafka
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

## Funcionalidades implementadas
---

### CRUD de órdenes
- Crear orden (`POST /orders`)
- Obtener orden por ID (`GET /orders/{id}`)
- Obtener por cliente (`GET /orders/customer/{customerId}`)
- Actualizar items (`PUT /orders/{id}/items`)
- Cancelar orden (`DELETE /orders/{id}`)
- Buscar por filtros (`GET /orders/search?status=...&date=...`)

---

### Integración con Kafka
- Al crear una orden, se envía un evento `OrderToPaymentEvent` al `topic: order-payment-events`.
- Las clases en el proyecto se encuentran en infrastructure/adapter/kafka.
- Existe un Consumer que escucha el mismo topic y procesa el evento para simulación de pagos.
- Como Kafka trabaja de forma asincrónica y no se puede probar con Postman, la verificación se realizó usando comandos desde un cmd en windows:

## Creación de tópicos manualmente con:

kafka-topics.bat --create --topic OrderToPayment --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics.bat --create --topic orderStatus --bootstrap-server localhost:9092 --partitions 1 --r

## Envío y recepción con:

Producer:
kafka-console-producer.bat --broker-list localhost:9092 --topic orderStatus

Consumer:
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic OrderToPayment --from-beginning

## Evidencia de funcionalidad de Kafka

- Mensajes enviados correctamente por el producer
- Mensajes recibidos correctamente por el consumer
![Captura de pantalla 2025-06-17 213056](https://github.com/user-attachments/assets/348eac1b-e97f-4c36-866c-b7df7b8606f6)

- Logs del procesamiento exitoso del evento de orden:
![Captura de pantalla 2025-06-17 213146](https://github.com/user-attachments/assets/c18fe59d-ee4c-4451-99b3-09893a368b94)

- Cambio en la base de datos:
![Captura de pantalla 2025-06-17 213204](https://github.com/user-attachments/assets/6af73ae2-cfac-4214-8a34-0aeccdcf22d4)

---

### Seguridad JWT
- Se genera token JWT al hacer login (AuthController opcional).
- Todos los endpoints están protegidos (excepto `/auth/**`).

### Validaciones y errores
- Se usa `@Valid`, `@NotNull`, `@NotEmpty` en DTOs.
- `@RestControllerAdvice` centraliza los errores: devuelve estructura clara con `ApiResponse`.

### Logging estructurado
- Cada capa tiene trazabilidad: controllers, usecases, Kafka, errores.

---

## Pruebas implementadas

### Unitarias con JUnit + Mockito
- Se implementaron pruebas para dos clases que se pedía como requerimiento:
  - `CreateOrderUseCase`
  - `CancelOrderUseCase`
    
## Casos cubiertos:
- CreateOrderUseCase:
Verifica que una orden se cree correctamente y se envíe al tópico OrderToPayment.
Simula fallos como null en respuesta del gateway.

- CancelOrderUseCase:
Verifica que una orden existente sea cancelada.
Simula el caso donde la orden no existe (lanzando excepción).

## Prueba de integración con MockMvc
### Flujo cubierto: OrderController, funcionalidad principal de gestión de órdenes.
- POST /orders:
Flujo exitoso con datos válidos.
Flujo fallido con body incompleto (400 Bad Request).
- GET /orders/{id}
Orden encontrada (200 OK).
Orden inexistente (404 Not Found).
- GET /orders/customer/{customerId}
Devuelve todas las órdenes asociadas a un cliente.
Devuelve lista vacía si el cliente no tiene órdenes.
- GET /orders/search?status=...&customerId=...&date=...
Filtra correctamente órdenes por uno o más criterios.
Devuelve error 400 si el formato de fecha es incorrecto.
Lanza error si el estado no coincide con el enum (PAID, CANCELLED, etc).
- PUT /orders/{id}/items
Items actualizados correctamente.
Lista vacía provoca error (400 o 500 según validación).
- DELETE /orders/{id}
Orden cancelada correctamente.

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

## Documentación Swagger

Puedes abrir la documentación interactiva en:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

También está disponible el archivo .json en:

- `docs/api.json`
---

## Importante:

- Podrá encontrar el archivo de la documentación de los endpoints con Postman de la API en la carpeta /postman dentro del proyecto.
- Podrá encontrar el archivo de la base de datos usada en la carpeta /database dentro del proyecto.

