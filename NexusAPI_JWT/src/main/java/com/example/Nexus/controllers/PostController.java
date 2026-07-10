package com.example.Nexus.controllers;

import com.example.Nexus.dto.PostRequest;
import com.example.Nexus.models.Post;
import com.example.Nexus.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 *  PostController — Controlador REST para posts
 * ============================================================
 *
 * Expone los endpoints HTTP para las operaciones sobre posts.
 * La ruta base de todos los endpoints es "/api/posts".
 *
 * @RestController → indica que esta clase es un controlador REST
 *                   (respuestas en JSON, no HTML).
 * @RequestMapping → define el prefijo de ruta para todos los métodos.
 *
 * Endpoints expuestos:
 *   POST  /api/posts       → crear un nuevo post
 *   GET   /api/posts       → obtener todos los posts
 *   GET   /api/posts/{id}  → obtener un post por ID
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    /**
     * Inyección del servicio de posts.
     * El controlador delega toda la lógica al Service.
     */
    @Autowired
    private PostService postService;

    /**
     * Crea un nuevo post.
     *
     * Método HTTP: POST
     * URL:         /api/posts
     * Body JSON:
     * {
     *   "title":   "Mi post",
     *   "content": "Contenido del post",
     *   "userId":  1
     * }
     *
     * @Valid       → valida el DTO antes de ejecutar el método.
     *               Si hay errores de validación → 400 Bad Request.
     * @RequestBody → deserializa el JSON del body a PostRequest.
     *
     * @param request los datos del post a crear
     * @return 201 Created con el post creado en el body
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostRequest request) {
        Post post = postService.createPost(request);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los posts, ordenados del más reciente al más antiguo.
     *
     * Método HTTP: GET
     * URL:         /api/posts
     *
     * @GetMapping (sin ruta adicional) → responde a GET /api/posts.
     * ResponseEntity.ok(...) → respuesta 200 OK con la lista en JSON.
     *
     * @return 200 OK con la lista de todos los posts
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * Obtiene un post específico por su ID.
     *
     * Método HTTP: GET
     * URL:         /api/posts/{id}
     * Ejemplo:     GET /api/posts/3
     *
     * @PathVariable → extrae el {id} de la URL como Long.
     *
     * Si el post no existe, el servicio lanza ResourceNotFoundException
     * que se convierte automáticamente en 404 Not Found.
     *
     * @param id el ID del post a buscar
     * @return 200 OK con el post, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // ================================================================
    //  ENDPOINT NUEVO: GET /api/posts/mine
    //  Requiere token JWT. Devuelve solo los posts del usuario autenticado.
    // ================================================================

    /**
     * Devuelve los posts del usuario autenticado, usando el subject del JWT.
     *
     * Método HTTP: GET
     * URL:         /api/posts/mine
     * Header:      Authorization: Bearer <token>   ← REQUERIDO
     *
     * ¿Cómo sabe el servidor de quién mostrar los posts?
     *
     * NO lo sabe por ningún parámetro que envíe el cliente en la URL.
     * Lo sabe porque el JwtFilter ya extrajo el username del "subject"
     * del token JWT y lo guardó como atributo del request:
     *   request.setAttribute("username", username)
     *
     * Aquí lo recuperamos con:
     *   request.getAttribute("username")
     *
     * Esto es importante: el cliente NO puede elegir de quién ver los posts.
     * Solo puede ver los suyos, porque el username viene de SU token firmado.
     *
     * Ejemplo de respuesta (200 OK):
     * [
     *   {
     *     "id": 6,
     *     "title": "React vs Angular en 2025",
     *     "content": "...",
     *     "createdAt": "2026-06-27T19:00:00",
     *     "user": { "id": 1, "username": "carlos_dev", "email": "carlos@nexus.com" }
     *   },
     *   ...
     * ]
     *
     * @param request el objeto HttpServletRequest (inyectado por Spring).
     *                Contiene el atributo "username" guardado por JwtFilter.
     * @return 200 OK con la lista de posts del usuario autenticado
     */
    @GetMapping("/mine")
    public ResponseEntity<List<Post>> getMyPosts(HttpServletRequest request) {
        // Recuperamos el username que JwtFilter extrajo del subject del JWT
        // y guardó como atributo del request. Hacemos cast a String.
        String username = (String) request.getAttribute("username");

        // Delegamos al service, pasando el username del JWT (no uno del cliente)
        return ResponseEntity.ok(postService.getMyPosts(username));
    }
}
