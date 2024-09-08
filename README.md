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

### Sequence diagram
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
### Flow chart diagram
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

### Component design diagram
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

### Entity diagram
```mermaid
erDiagram
%% Entidad Usuario
    USER {
        UUID user_id PK
        string username
        string email
        string password
        ENUM role
        datetime created_at
        datetime updated_at
    }

%% Entidad Notificación
    NOTIFICATION {
        UUID notification_id PK
        UUID user_id FK
        string notification_type
        string message
        boolean is_read
        datetime created_at
        datetime updated_at
    }

%% Entidad Preferencias de Notificación
    NOTIFICATION_PREFERENCES {
        UUID preference_id PK
        UUID user_id FK
        boolean receive_promotions
        boolean receive_updates
        boolean receive_alerts
        datetime created_at
        datetime updated_at
    }

%% Entidad Auditoría
    AUDIT_LOG {
        UUID audit_id PK
        string entity_type
        UUID entity_id
        string action
        string username
        datetime timestamp
        text old_values
        text new_values
    }

%% Relación entre entidades
    USER ||--o{ NOTIFICATION: "has"
    USER ||--o{ NOTIFICATION_PREFERENCES: "sets"
    USER ||--o{ AUDIT_LOG: "modifies"

%% Notificación tiene una relación con Usuario
    NOTIFICATION ||--o| USER: "belongs to"
    NOTIFICATION ||--o{ AUDIT_LOG: "modifies"

%% Preferencias tienen una relación con Usuario
    NOTIFICATION_PREFERENCES ||--o| USER: "belongs to"
    NOTIFICATION_PREFERENCES ||--o{ AUDIT_LOG: "modifies"

```

### Class diagram
```mermaid
classDiagram
%% Definición del Enum Role
    class Role {
        <<enumeration>>
        ADMIN
        USER
        GUEST
    }

%% Definición de la clase User
    class User {
        +UUID userId
        +String username
        +String email
        +String password
        +Role role
        +LocalDateTime createdAt
        +LocalDateTime updatedAt
        +getUserId() UUID
        +getUsername() String
        +getEmail() String
        +getPassword() String
        +getRole() Role
        +getCreatedAt() LocalDateTime
        +getUpdatedAt() LocalDateTime
        +setUserId(UUID userId)
        +setUsername(String username)
        +setEmail(String email)
        +setPassword(String password)
        +setRole(Role role)
        +setCreatedAt(LocalDateTime createdAt)
        +setUpdatedAt(LocalDateTime updatedAt)
    }

%% Definición de la clase Notification
    class Notification {
        +UUID notificationId
        +User user
        +String notificationType
        +String message
        +boolean isRead
        +LocalDateTime createdAt
        +LocalDateTime updatedAt
        +getNotificationId() UUID
        +getUser() User
        +getNotificationType() String
        +getMessage() String
        +isRead() boolean
        +getCreatedAt() LocalDateTime
        +getUpdatedAt() LocalDateTime
        +setNotificationId(UUID notificationId)
        +setUser(User user)
        +setNotificationType(String notificationType)
        +setMessage(String message)
        +setRead(boolean isRead)
        +setCreatedAt(LocalDateTime createdAt)
        +setUpdatedAt(LocalDateTime updatedAt)
    }

%% Definición de la clase NotificationPreferences
    class NotificationPreferences {
        +UUID preferenceId
        +User user
        +boolean receivePromotions
        +boolean receiveUpdates
        +boolean receiveAlerts
        +LocalDateTime createdAt
        +LocalDateTime updatedAt
        +getPreferenceId() UUID
        +getUser() User
        +isReceivePromotions() boolean
        +isReceiveUpdates() boolean
        +isReceiveAlerts() boolean
        +getCreatedAt() LocalDateTime
        +getUpdatedAt() LocalDateTime
        +setPreferenceId(UUID preferenceId)
        +setUser(User user)
        +setReceivePromotions(boolean receivePromotions)
        +setReceiveUpdates(boolean receiveUpdates)
        +setReceiveAlerts(boolean receiveAlerts)
        +setCreatedAt(LocalDateTime createdAt)
        +setUpdatedAt(LocalDateTime updatedAt)
    }

%% Definición de la clase AuditLog
    class AuditLog {
        +UUID auditId
        +String entityType
        +UUID entityId
        +String action
        +String username
        +LocalDateTime timestamp
        +String oldValues
        +String newValues
        +getAuditId() UUID
        +getEntityType() String
        +getEntityId() UUID
        +getAction() String
        +getUsername() String
        +getTimestamp() LocalDateTime
        +getOldValues() String
        +getNewValues() String
        +setAuditId(UUID auditId)
        +setEntityType(String entityType)
        +setEntityId(UUID entityId)
        +setAction(String action)
        +setUsername(String username)
        +setTimestamp(LocalDateTime timestamp)
        +setOldValues(String oldValues)
        +setNewValues(String newValues)
    }

%% Relaciones entre las clases
    User "1" --> "0..*" Notification : "has"
    User "1" --> "1" NotificationPreferences : "sets"
    Notification --> User : "belongs to"
    NotificationPreferences --> User : "belongs to"
    User "1" --> "0..*" AuditLog : "modifies"
    Notification "1" --> "0..*" AuditLog : "changes"
    NotificationPreferences "1" --> "0..*" AuditLog : "modifies"

```

   ## Audit table

### Purpose of **`entity_id`** in the Audit Table:

The **`entity_id`** in the **`AuditLog`** table is used to uniquely identify the specific entity that was modified, created, or deleted. It stores the **ID** (unique identifier) of the entity being audited, allowing you to precisely link the audit log entry to the affected entity.

### Function of **`entity_id`**:

1. **Reference to the Modified Entity**:
    - The **`entity_id`** corresponds to the **UUID** of the entity that was affected (whether it’s a **User**, **Notification**, or **NotificationPreferences**). This helps identify exactly which record was modified.

2. **Linking with the Entity Type (`entity_type`)**:
    - Combined with the **`entity_type`** field, which indicates the type of entity (e.g., "User", "Notification"), the **`entity_id`** allows you to determine which record of which entity type was affected. For instance, you can identify that the user with `user_id = 123e4567-e89b-12d3-a456-426614174000` was updated.

3. **Change History**:
    - Using **`entity_id`**, you can retrieve the entire change history for a specific entity. For example, if you have multiple audit log entries related to the same `user_id`, you can track all changes that have been made to that user over time.

### Example:

Suppose a user with **`user_id = 123e4567-e89b-12d3-a456-426614174000`** updates their profile information. The entry in the **`AuditLog`** table could look like this:


| audit_id                              | entity_type | entity_id                            | action  | username   | timestamp              | old_values                            | new_values                            |
|---------------------------------------|-------------|---------------------------------------|---------|------------|------------------------|----------------------------------------|----------------------------------------|
| e89b4567-e89b-12d3-a456-426614174001   | User        | 123e4567-e89b-12d3-a456-426614174000 | UPDATE  | jdoe       | 2024-09-07T12:34:56     | {"email": "oldemail@example.com"}      | {"email": "newemail@example.com"}     |


- **`entity_id`** in this case is **`123e4567-e89b-12d3-a456-426614174000`**, which is the **user_id** of the modified user.
- **`entity_type`** is "User", indicating that the affected entity is a user.
- **`action`** is "UPDATE", showing that an update operation took place.
- **`old_values`** and **`new_values`** store the previous and updated values, respectively.