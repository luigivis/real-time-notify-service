# RealTimeNotificationSystem

**RealTimeNotificationSystem** is a microservices-based application that provides real-time notifications to users. It utilizes **Apache Kafka** for event streaming, **Redis** for caching unread notifications, and a **relational database** (PostgreSQL or MySQL) to store user preferences and notification history. The system is designed to handle high-throughput notification events and deliver them instantly to users via a web or mobile client.

#### Features:
- **Real-time Notifications**: Uses Kafka to publish and consume notification events.
- **User Preferences**: Allows users to subscribe to different types of notifications.
- **Redis Caching**: Stores unread notifications for quick access and delivery.
- **Database Integration**: Stores user preferences and historical data in a relational database.
- **Scalable Architecture**: Built with microservices to scale individual components independently.

#### Technologies:
- **Spring Boot**: For building microservices.
- **Apache Kafka**: For event-driven messaging.
- **Redis**: For caching unread notifications.
- **PostgreSQL/MySQL**: For relational database storage.
- **Docker**: For containerized deployment.

## Diagrams
```mermaid
sequenceDiagram
    participant Usuario
    participant API Gateway
    participant ServicioUsuarios
    participant BaseDatosRelacional
    participant PublicadorKafka
    participant TópicoKafka
    participant ConsumidorKafka
    participant DistribuidorNotificaciones
    participant Redis
    participant ClienteUsuario

    Usuario->>API Gateway: Registrarse / Actualizar Preferencias
    API Gateway->>ServicioUsuarios: Enviar datos de usuario
    ServicioUsuarios->>BaseDatosRelacional: Guardar/Actualizar preferencias
    BaseDatosRelacional-->>ServicioUsuarios: Confirmación
    ServicioUsuarios-->>API Gateway: Confirmación
    API Gateway-->>Usuario: Confirmación de suscripción

    Note over Usuario, ClienteUsuario: Evento de notificación ocurre

    ServicioPublicador->>PublicadorKafka: Publicar notificación
    PublicadorKafka->>TópicoKafka: Enviar mensaje de notificación

    ConsumidorKafka->>TópicoKafka: Consumir mensaje de notificación
    ConsumidorKafka->>DistribuidorNotificaciones: Procesar notificación
    DistribuidorNotificaciones->>Redis: Almacenar notificación no leída
    DistribuidorNotificaciones->>BaseDatosRelacional: Guardar historial de notificación
    DistribuidorNotificaciones->>ClienteUsuario: Enviar notificación en tiempo real

    ClienteUsuario-->>DistribuidorNotificaciones: Confirmación de recepción

```

```mermaid
flowchart TD
    A[Inicio] --> B[Usuario se registra o actualiza preferencias]
    B --> C[Guardar preferencias en Base de Datos Relacional]
    C --> D[Evento de notificación ocurre]
    D --> E[Publicador Kafka envía mensaje al Tópico Kafka]
    E --> F[Consumidor Kafka recibe el mensaje]
    F --> G[Distribuidor de Notificaciones procesa la notificación]
    G --> H{Usuario está activo?}
    H -->|Sí| I[Enviar notificación en tiempo real al Cliente]
    H -->|No| J[Almacenar notificación no leída en Redis]
    I --> K[Fin]
    J --> K
    G --> L[Guardar historial de notificación en Base de Datos Relacional]
    L --> K
```

```mermaid
graph TB
    %% Definir los componentes principales
    subgraph API Gateway
        A1[API Gateway] -->|HTTP Requests| B1[Servicio de Usuarios]
        A1 -->|HTTP Requests| B2[Servicio de Notificaciones]
        A1 -->|HTTP Requests| B3[Servicio de Reportes]
    end

    %% Definir servicios internos
    subgraph Microservicios
        B1 -->|Consulta| C1[(Base de Datos Relacional)]
        B2 -->|Producción de Mensajes| D1[Kafka Publicador]
        B3 -->|Consulta| C1[(Base de Datos Relacional)]
        B2 -->|Lectura| F1[Redis Cache]
        D2[Kafka Consumidor] -->|Procesar Mensajes| B2[Servicio de Notificaciones]
        D2 -->|Distribuir| B4[Servicio de Distribución de Notificaciones]
        B4 -->|Almacenar No Leídas| F1[Redis Cache]
        B4 -->|Guardar Historial| C1[(Base de Datos Relacional)]
    end

    %% Otros componentes externos
    subgraph External Components
        D1 -->|Enviar Mensajes| E1[(Tópico Kafka)]
        E1 -->|Leer Mensajes| D2[Kafka Consumidor]
    end

    %% Interacción con el cliente
    subgraph Cliente
        G1[Cliente Usuario] -->|Solicitar Notificaciones| A1[API Gateway]
        B4 -->|Enviar Notificación en Tiempo Real| G1[Cliente Usuario]
    end
```
