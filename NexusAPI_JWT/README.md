# 🚀 NexusAPI

> API REST construida con **Spring Boot 4** y **MySQL** como proyecto educativo para la materia de **Aplicaciones Web**.  
> Simula el backend de una red social básica donde los usuarios pueden crear posts y comentarios.

---

## 📋 Tabla de Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Arquitectura del Proyecto](#arquitectura-del-proyecto)
4. [Requisitos Previos](#requisitos-previos)
5. [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
6. [Configurar application.properties](#configurar-applicationproperties)
7. [Ejecutar el Proyecto](#ejecutar-el-proyecto)
8. [Insertar Datos de Prueba (Seeder)](#insertar-datos-de-prueba-seeder)
9. [🔐 Autenticación con JWT](#-autenticación-con-jwt)
10. [Endpoints de la API](#endpoints-de-la-api)
11. [Pruebas con Postman](#pruebas-con-postman)
12. [Estructura de Carpetas](#estructura-de-carpetas)

---

## 📖 Descripción del Proyecto

**NexusAPI** es una API REST que expone operaciones CRUD sobre tres entidades principales:

| Entidad | Descripción |
|---------|-------------|
| `User`  | Usuarios registrados en la plataforma |
| `Post`  | Publicaciones creadas por los usuarios |
| `Comment` | Comentarios en las publicaciones |

Las relaciones entre entidades son:
- Un **User** puede tener muchos **Posts** (OneToMany)
- Un **Post** puede tener muchos **Comments** (OneToMany)
- Un **Comment** pertenece a un **Post** y a un **User** (ManyToOne)

---

## 🛠 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 | Lenguaje de programación |
| Spring Boot | 4.1.0 | Framework principal del backend |
| Spring Data JPA | (incluido) | Acceso a base de datos con ORM |
| Spring Validation | (incluido) | Validación de datos de entrada |
| MySQL | 8+ | Motor de base de datos relacional |
| Lombok | (incluido) | Reducir código repetitivo (getters/setters) |
| Maven | (incluido) | Gestión de dependencias y construcción |

---

## 🏗 Arquitectura del Proyecto

El proyecto sigue la arquitectura en capas estándar de Spring Boot:

```
┌─────────────────────────────────────────────┐
│              Cliente (Postman)               │
└──────────────────────┬──────────────────────┘
                       │ HTTP Request
┌──────────────────────▼──────────────────────┐
│              CONTROLLERS                     │
│  UserController / PostController /           │
│  CommentController                           │
│  → Reciben y validan las peticiones HTTP     │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│                SERVICES                      │
│  UserService / PostService / CommentService  │
│  → Contienen la lógica de negocio            │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│              REPOSITORIES                    │
│  UserRepository / PostRepository /           │
│  CommentRepository                           │
│  → Se comunican con la base de datos         │
└──────────────────────┬──────────────────────┘
                       │ SQL (generado por JPA)
┌──────────────────────▼──────────────────────┐
│             BASE DE DATOS MySQL              │
│           (tablas: users, posts, comments)   │
└─────────────────────────────────────────────┘
```

**Flujo de una petición:**
1. El cliente envía un HTTP Request (ej: `POST /api/users`).
2. El **Controller** recibe la petición, valida el body con `@Valid` y llama al **Service**.
3. El **Service** aplica la lógica de negocio y usa el **Repository** para acceder a la BD.
4. El **Repository** ejecuta la query SQL en MySQL y devuelve el resultado.
5. El resultado sube la cadena hasta el **Controller** que lo serializa a JSON y responde.

---

## ✅ Requisitos Previos

Asegúrate de tener instalado en tu máquina:

- **Java 21** → [Descargar JDK 21](https://adoptium.net/)
- **Maven** → Incluido en el proyecto (usa `mvnw`)
- **MySQL 8+** → [Descargar MySQL](https://dev.mysql.com/downloads/installer/)
- **Postman** → [Descargar Postman](https://www.postman.com/downloads/)
- Un IDE: **IntelliJ IDEA** (recomendado) o **Eclipse/VS Code**

---

## 🗄 Configuración de la Base de Datos

### Paso 1: Crear un usuario de MySQL (opcional, puedes usar root)

Abre MySQL Workbench o la terminal de MySQL y ejecuta:

```sql
-- Crear un usuario específico para el proyecto (recomendado)
CREATE USER 'Pruebas'@'localhost' IDENTIFIED BY 'Pruebas1234';

-- Darle todos los permisos sobre la base de datos del proyecto
GRANT ALL PRIVILEGES ON nexus.* TO 'Pruebas'@'localhost';

-- Aplicar los cambios
FLUSH PRIVILEGES;
```

> ⚠️ **Nota:** Si prefieres usar tu usuario `root`, solo cambia las credenciales en `application.properties`.

### Paso 2: Verificar que MySQL está corriendo

En Windows, asegúrate de que el servicio MySQL esté activo:
- Abre **Servicios** (`Win + R` → `services.msc`) y verifica que MySQL esté en estado **En ejecución**.
- O desde la terminal: `net start MySQL80`

> La base de datos `nexus` se **creará automáticamente** cuando ejecutes el proyecto por primera vez, gracias al parámetro `createDatabaseIfNotExist=true` en la URL de conexión.

---

## ⚙️ Configurar application.properties

Abre el archivo:
```
src/main/resources/application.properties
```

Modifica las siguientes líneas con **tus credenciales de MySQL**:

```properties
# Cambia el nombre de la BD si lo deseas (también en la URL)
spring.datasource.url=jdbc:mysql://localhost:3306/nexus?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true

# ⬇ CAMBIA ESTOS DOS VALORES:
spring.datasource.username=TU_USUARIO_MYSQL
spring.datasource.password=TU_PASSWORD_MYSQL
```

**Ejemplo con usuario root:**
```properties
spring.datasource.username=root
spring.datasource.password=miPasswordDeRoot
```

> ✅ El resto de la configuración no necesita modificarse para ejecutar el proyecto en local.

---

## ▶️ Ejecutar el Proyecto

### Opción A: Desde IntelliJ IDEA
1. Abre el proyecto desde `File → Open` y selecciona la carpeta raíz `NexusAPI`.
2. Espera a que Maven descargue las dependencias automáticamente.
3. Abre `NexusApplication.java` y haz clic en el ▶️ verde junto al método `main`.
4. Verifica en la consola que aparezca:
   ```
   Started NexusApplication in X.XXX seconds
   ```

### Opción B: Desde la terminal (Maven Wrapper)
```bash
# Windows (PowerShell o CMD)
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

El servidor arrancará en: **`http://localhost:8080`**

---

## 🌱 Insertar Datos de Prueba (Seeder)

Una vez que el proyecto está corriendo y las tablas fueron creadas automáticamente por Hibernate, ejecuta los siguientes INSERTs en MySQL Workbench o tu cliente de BD:

### Usuarios
```sql
-- ================================================
-- USUARIOS
-- ================================================
INSERT INTO users (username, email, password) VALUES
('carlos_dev',    'carlos@nexus.com', '1234'),
('maria_ux',      'maria@nexus.com',  '1234'),
('juan_backend',  'juan@nexus.com',   '1234'),
('laura_design',  'laura@nexus.com',  '1234'),
('pedro_qa',      'pedro@nexus.com',  '1234');
```

### Posts
```sql
-- ================================================
-- POSTS (user_id referencia a la tabla users)
-- ================================================
INSERT INTO posts (title, content, created_at, user_id) VALUES
('Bienvenidos a Nexus',
 'Esta es la primera publicación de la plataforma. ¡Esperamos que disfruten la experiencia!',
 NOW(), 1),
('Tips de UI/UX para principiantes',
 'Hoy les comparto 5 tips esenciales para mejorar la experiencia de usuario en sus aplicaciones...',
 NOW(), 2),
('Spring Boot con MySQL paso a paso',
 'En este post explico cómo configurar Spring Boot con una base de datos MySQL correctamente.',
 NOW(), 3),
('¿Qué es el diseño minimalista?',
 'El diseño minimalista se basa en la simplicidad, eliminando lo innecesario para destacar lo esencial.',
 NOW(), 4),
('Cómo hacer pruebas unitarias en Java',
 'Las pruebas unitarias son fundamentales para garantizar la calidad del software. Aquí un ejemplo con JUnit...',
 NOW(), 5),
('React vs Angular en 2025',
 'Comparativa detallada de dos de los frameworks más populares del frontend actual.',
 NOW(), 1),
('Bases de datos NoSQL: ¿cuándo usarlas?',
 'MongoDB, Redis, Cassandra... ¿cuál elegir y cuándo? Guía práctica para developers.',
 NOW(), 3);
```

### Comentarios
```sql
-- ================================================
-- COMENTARIOS (post_id y user_id referencian sus tablas)
-- ================================================
INSERT INTO comments (text, created_at, post_id, user_id) VALUES
('¡Excelente iniciativa! Esperando más contenido.',               NOW(), 1, 2),
('Muy buena bienvenida, el equipo se ve muy profesional.',        NOW(), 1, 3),
('Justo lo que necesitaba, gracias por los tips!',                NOW(), 2, 1),
('El tip #3 me pareció el más útil, lo aplicaré en mi proyecto.', NOW(), 2, 5),
('Muy claro el tutorial, me ayudó a resolver mi error de conexión.', NOW(), 3, 2),
('¿Podrías hacer uno también con PostgreSQL?',                    NOW(), 3, 4),
('Me encanta el enfoque minimalista, menos es más!',              NOW(), 4, 1),
('Muy interesante, ¿tienes algún recurso extra recomendado?',     NOW(), 4, 3),
('JUnit es genial, ¿conoces Mockito también?',                    NOW(), 5, 1),
('Muy completo el post, gracias por compartirlo.',                NOW(), 5, 2),
('Yo sigo prefiriendo React por su ecosistema.',                  NOW(), 6, 4),
('Angular tiene ventajas en proyectos empresariales grandes.',    NOW(), 6, 5),
('MongoDB para datos no estructurados es una maravilla.',         NOW(), 7, 2),
('Redis para caché es insuperable, muy buen punto.',              NOW(), 7, 1);
```

---

## 🔐 Autenticación con JWT

Esta sección explica cómo funciona la autenticación con **JWT (JSON Web Token)** en NexusAPI.

### ¿Qué es JWT y para qué sirve?

Una API REST es **stateless** (sin estado): el servidor no recuerda quién hizo la petición anterior.
JWT resuelve esto: el cliente demuestra su identidad enviando un **token firmado** en cada petición.

Un JWT es una cadena con 3 partes separadas por puntos (`.`), todas codificadas en Base64:

```
eyJhbGciOiJIUzI1NiJ9        ← HEADER: algoritmo de firma (HS256)
.eyJzdWIiOiJjYXJsb3NfZGV2  ← PAYLOAD: datos del usuario (sub, iat, exp)
.HMAC_FIRMA                  ← SIGNATURE: garantiza que nadie modificó el token
```

Puedes pegar cualquier token en **[jwt.io](https://jwt.io)** para ver su contenido decodificado.

El **subject (`sub`)** del token es el `username` del usuario. Es el dato central:
el servidor lo usa para saber **quién** hace cada petición sin consultar la BD en cada request.

---

### ¿Por qué JJWT? ¿Qué son esos 3 artefactos Maven?

JJWT es la librería más estándar para manejar JWT en Java. Se divide en 3 artefactos:

| Artefacto | scope | ¿Para qué? |
|-----------|-------|-----------|
| `jjwt-api` | compile | Las **interfaces** que usamos en el código (`Jwts`, `Claims`, etc.) |
| `jjwt-impl` | runtime | La **implementación real**. Solo se necesita al ejecutar, no al compilar |
| `jjwt-jackson` | runtime | Parsea el **payload JSON** del token. También solo en runtime |

> `scope="runtime"` significa que Maven incluye el JAR cuando el servidor arranca,
> pero el compilador no lo ve. Es la separación clásica entre API e implementación.

---

### Archivos nuevos agregados

```
src/main/java/com/example/Nexus/
└── auth/
    ├── LoginRequest.java   ← DTO que recibe { username, password }
    ├── LoginResponse.java  ← DTO que devuelve { token }
    ├── JwtService.java     ← Genera y valida tokens JWT
    ├── JwtFilter.java      ← Intercepta cada request HTTP
    └── AuthController.java ← Endpoint POST /api/auth/login
```

Los controllers, services, models y repositories existentes **no se modificaron**.
El JWT es una capa transversal que se agrega sin tocar el código de negocio.

---

### Flujo completo de autenticación

```
PASO 1 — Login (obtener el token)
─────────────────────────────────
Cliente → POST /api/auth/login
         Body: { "username": "carlos_dev", "password": "1234" }

Servidor:
  1. Busca al usuario por username en la BD.
  2. Compara la contraseña.
  3. Genera un JWT con username como "subject".
  4. Responde: { "token": "eyJ..." }

PASO 2 — Usar el token en requests protegidos
──────────────────────────────────────────────
Cliente → GET /api/posts/mine
         Header: Authorization: Bearer eyJ...

JwtFilter (intercepta antes del Controller):
  1. Lee el header Authorization.
  2. Extrae el token (quita el prefijo "Bearer ").
  3. Valida: ¿la firma es correcta? ¿no está vencido?
  4. Extrae el subject (username) del token.
  5. Guarda el username como atributo del request.
  6. Deja pasar al Controller.

Controller:
  1. Lee el username del atributo del request.
  2. Llama al Service con ese username.
  3. Devuelve solo los posts de ese usuario.
```

---

### Endpoints protegidos vs. públicos

Base URL: `http://localhost:8080`

### 🔑 Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/auth/login` | Iniciar sesión y obtener JWT | `200 OK` | ❌ |

### 👤 Usuarios (`/api/users`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/users` | Crear nuevo usuario | `201 Created` | ❌ |
| `GET`  | `/api/users/{id}` | Obtener usuario por ID | `200 OK` | ✅ |

### 📝 Posts (`/api/posts`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/posts` | Crear nuevo post | `201 Created` | ✅ |
| `GET`  | `/api/posts` | Obtener todos los posts | `200 OK` | ❌ |
| `GET`  | `/api/posts/{id}` | Obtener post por ID | `200 OK` | ❌ |
| `GET`  | `/api/posts/mine` | Obtener **mis** posts (por subject JWT) | `200 OK` | ✅ |

### 💬 Comentarios (`/api/comments`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/comments` | Crear nuevo comentario | `201 Created` | ✅ |
| `GET`  | `/api/comments/post/{postId}` | Obtener comentarios de un post | `200 OK` | ❌ |

---

### El endpoint `/api/posts/mine` — Uso del subject JWT

Este endpoint demuestra el uso del **subject** del JWT para filtrar recursos.

El cliente **no envía su username** en la URL ni en el body. El servidor lo extrae del token:

```
GET /api/posts/mine
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
                                   ↑
             El JwtFilter extrae el subject: "carlos_dev"
             y lo pasa al Controller como atributo del request.
             El cliente NO puede cambiarlo sin invalidar el token.
```

Esto es importante: si el username viniera del body, cualquier usuario podría
enviar el username de otra persona. Al usar el subject del JWT, el servidor
garantiza que ves **tus propios posts**, sin importar lo que el cliente envíe.

---

## 🔗 Endpoints de la API

Base URL: `http://localhost:8080`

### 🔑 Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/auth/login` | Iniciar sesión y obtener JWT | `200 OK` | ❌ |

### 👤 Usuarios (`/api/users`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/users` | Crear nuevo usuario | `201 Created` | ❌ |
| `GET`  | `/api/users/{id}` | Obtener usuario por ID | `200 OK` | ✅ |

### 📝 Posts (`/api/posts`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/posts` | Crear nuevo post | `201 Created` | ✅ |
| `GET`  | `/api/posts` | Obtener todos los posts | `200 OK` | ❌ |
| `GET`  | `/api/posts/{id}` | Obtener post por ID | `200 OK` | ❌ |
| `GET`  | `/api/posts/mine` | Obtener **mis** posts (por subject JWT) | `200 OK` | ✅ |

### 💬 Comentarios (`/api/comments`)

| Método | Endpoint | Descripción | Código Éxito | ¿Token? |
|--------|----------|-------------|--------------|---------|
| `POST` | `/api/comments` | Crear nuevo comentario | `201 Created` | ✅ |
| `GET`  | `/api/comments/post/{postId}` | Obtener comentarios de un post | `200 OK` | ❌ |

### Códigos de error comunes

| Código | Significado | Cuándo ocurre |
|--------|-------------|----------------|
| `400 Bad Request` | Datos inválidos | Campo vacío, email mal formado, etc. |
| `404 Not Found` | Recurso no existe | ID de usuario o post inexistente |
| `500 Internal Server Error` | Error del servidor | Error inesperado en el backend |

---

## 🧪 Pruebas con Postman

### Configuración inicial en Postman

1. Abre Postman y crea una nueva **Collection** llamada `NexusAPI`.
2. Para todas las peticiones que envíen body, ve a la pestaña **Body** → selecciona **raw** → elige **JSON** en el dropdown.
3. Para las peticiones que requieren token, ve a la pestaña **Headers** y agrega:
   - Key: `Authorization`
   - Value: `Bearer TU_TOKEN_AQUI`

> 💡 **Consejo**: En Postman puedes crear una variable de colección `{{token}}` y usarla en todos los headers como `Bearer {{token}}`.

---

### 0️⃣ Login — Obtener el JWT

> ⚠️ **Haz esto primero.** Sin el token no podrás acceder a los endpoints protegidos.

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/auth/login`
- **Body (raw JSON):**
```json
{
  "username": "carlos_dev",
  "password": "1234"
}
```
- **Respuesta esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3NfZGV2...."
}
```
> Copia ese token. Lo necesitarás en el header `Authorization: Bearer <token>` de las siguientes peticiones.

- **Si las credenciales son incorrectas (401 Unauthorized):**
```json
{
  "error": "Credenciales inválidas"
}
```

---

### 1️⃣ Crear un Usuario

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/users`
- **Headers:** `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "username": "nuevo_alumno",
  "email": "alumno@itson.mx",
  "password": "password123"
}
```
- **Respuesta esperada (201 Created):**
```json
{
  "id": 6,
  "username": "nuevo_alumno",
  "email": "alumno@itson.mx"
}
```
> Nota: `password` no aparece en la respuesta (está protegido con `@JsonIgnore`).

---

### 2️⃣ Obtener un Usuario por ID

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/users/1`
- **Body:** ninguno
- **Respuesta esperada (200 OK):**
```json
{
  "id": 1,
  "username": "carlos_dev",
  "email": "carlos@nexus.com"
}
```
- **Si el usuario no existe (404):**
```json
{
  "error": "Usuario no encontrado con ID: 99"
}
```

---

### 3️⃣ Crear un Post

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/posts`
- **Body (raw JSON):**
```json
{
  "title": "Mi primer post desde Postman",
  "content": "Este post fue creado usando la API REST de NexusAPI.",
  "userId": 1
}
```
- **Respuesta esperada (201 Created):**
```json
{
  "id": 8,
  "title": "Mi primer post desde Postman",
  "content": "Este post fue creado usando la API REST de NexusAPI.",
  "createdAt": "2026-06-27T19:00:00",
  "user": {
    "id": 1,
    "username": "carlos_dev",
    "email": "carlos@nexus.com"
  }
}
```

---

### 4️⃣ Obtener Todos los Posts

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/posts`
- **Body:** ninguno
- **Respuesta esperada (200 OK):** lista de todos los posts ordenados del más reciente al más antiguo.

---

### 5️⃣ Obtener un Post por ID

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/posts/1`
- **Respuesta esperada (200 OK):** el post con ese ID.

---

### 6️⃣ Mis Posts — Filtrar por subject del JWT

Este endpoint es **el ejemplo de uso del subject**: el servidor obtiene el username del token
(no del cliente) y devuelve SOLO los posts de ese usuario.

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/posts/mine`
- **Headers:** `Authorization: Bearer <tu_token>`  ← **REQUERIDO**
- **Body:** ninguno
- **Respuesta esperada (200 OK)** (si hiciste login como `carlos_dev`):
```json
[
  {
    "id": 6,
    "title": "React vs Angular en 2025",
    "content": "Comparativa detallada...",
    "createdAt": "2026-06-27T19:00:00",
    "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" }
  },
  {
    "id": 1,
    "title": "Bienvenidos a Nexus",
    "content": "Esta es la primera publicación...",
    "createdAt": "2026-06-27T18:00:00",
    "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" }
  }
]
```
> Nótese que **solo aparecen posts de `carlos_dev`**, aunque existen posts de otros usuarios.
> El servidor no usa ningún parámetro del cliente: usa el **subject del JWT**.

- **Sin token (401 Unauthorized):**
```json
{
  "error": "Se requiere token de autenticación"
}
```

- **Con token vencido o modificado (401 Unauthorized):**
```json
{
  "error": "Token inválido o expirado"
}
```

---

### 6️⃣ Crear un Comentario

- **Método:** `POST`
- **URL:** `http://localhost:8080/api/comments`
- **Body (raw JSON):**
```json
{
  "text": "¡Muy buen post, aprendí bastante!",
  "postId": 1,
  "userId": 2
}
```
- **Respuesta esperada (201 Created):**
```json
{
  "id": 15,
  "text": "¡Muy buen post, aprendí bastante!",
  "createdAt": "2026-06-27T19:05:00",
  "user": {
    "id": 2,
    "username": "maria_ux",
    "email": "maria@nexus.com"
  }
}
```

---

### 7️⃣ Obtener Comentarios de un Post

- **Método:** `GET`
- **URL:** `http://localhost:8080/api/comments/post/1`
- **Body:** ninguno
- **Respuesta esperada (200 OK):** lista de comentarios del post 1, ordenados del más antiguo al más reciente.

---

### 🔴 Probar Validaciones (errores 400)

Envía este body incompleto a `POST /api/users`:
```json
{
  "username": "",
  "email": "esto-no-es-un-email",
  "password": ""
}
```
**Respuesta esperada (400 Bad Request):**
```json
{
  "username": "El nombre de usuario es obligatorio",
  "email": "El email no tiene un formato válido",
  "password": "La contraseña es obligatoria"
}
```

---

## 📁 Estructura de Carpetas

```
NexusAPI/
├── src/
│   └── main/
│       ├── java/com/example/Nexus/
│       │   ├── NexusApplication.java       ← Punto de entrada + registro del JwtFilter
│       │   ├── controllers/                ← Capa HTTP (endpoints REST)
│       │   │   ├── AuthController.java     ← 🆕 POST /api/auth/login
│       │   │   ├── UserController.java
│       │   │   ├── PostController.java     ← 🆕 Incluye GET /api/posts/mine
│       │   │   └── CommentController.java
│       │   ├── services/                   ← Lógica de negocio
│       │   │   ├── JwtService.java         ← 🆕 Genera y valida tokens JWT
│       │   │   ├── UserService.java
│       │   │   ├── PostService.java        ← 🆕 Incluye getMyPosts(username)
│       │   │   └── CommentService.java
│       │   ├── repositories/               ← Acceso a la base de datos
│       │   │   ├── UserRepository.java
│       │   │   ├── PostRepository.java     ← 🆕 Incluye findByUserUsername...
│       │   │   └── CommentRepository.java
│       │   ├── models/                     ← Entidades JPA (tablas de la BD)
│       │   │   ├── User.java
│       │   │   ├── Post.java
│       │   │   └── Comment.java
│       │   ├── dto/                        ← Objetos de transferencia de datos
│       │   │   ├── LoginRequest.java       ← 🆕 DTO { username, password }
│       │   │   ├── LoginResponse.java      ← 🆕 DTO { token }
│       │   │   ├── UserRequest.java
│       │   │   ├── PostRequest.java
│       │   │   └── CommentRequest.java
│       │   ├── security/                   ← 🆕 Seguridad de la API
│       │   │   └── JwtFilter.java          ← Intercepta y valida cada request
│       │   └── exceptions/                 ← Manejo global de errores
│       │       ├── GlobalExceptionHandler.java
│       │       └── ResourceNotFoundException.java
│       └── resources/
│           └── application.properties      ← Configuración BD + propiedades JWT
├── pom.xml                                 ← Dependencias de Maven (incluye JJWT)
└── README.md                               ← Este archivo
```
