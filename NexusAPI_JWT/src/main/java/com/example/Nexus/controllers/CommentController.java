package com.example.Nexus.controllers;

import com.example.Nexus.dto.CommentRequest;
import com.example.Nexus.models.Comment;
import com.example.Nexus.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 *  CommentController — Controlador REST para comentarios
 * ============================================================
 *
 * Expone los endpoints HTTP para las operaciones sobre comentarios.
 * La ruta base de todos los endpoints es "/api/comments".
 *
 * @RestController → controlador REST que responde en JSON.
 * @RequestMapping → prefijo de ruta "/api/comments".
 *
 * Endpoints expuestos:
 *   POST  /api/comments              → crear un comentario
 *   GET   /api/comments/post/{postId} → obtener comentarios de un post
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    /**
     * Inyección del servicio de comentarios.
     * El controlador no contiene lógica de negocio; la delega al Service.
     */
    @Autowired
    private CommentService commentService;

    /**
     * Crea un nuevo comentario en un post.
     *
     * Método HTTP: POST
     * URL:         /api/comments
     * Body JSON:
     * {
     *   "text":   "¡Muy buen post!",
     *   "postId": 1,
     *   "userId": 2
     * }
     *
     * @Valid       → activa las validaciones del DTO (CommentRequest).
     *               Si "text" está vacío → 400 Bad Request automático.
     * @RequestBody → convierte el JSON del body al objeto CommentRequest.
     *
     * @param request los datos del comentario a crear
     * @return 201 Created con el comentario creado en el body
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentRequest request) {
        Comment comment = commentService.createComment(request);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * Obtiene todos los comentarios de un post específico.
     *
     * Método HTTP: GET
     * URL:         /api/comments/post/{postId}
     * Ejemplo:     GET /api/comments/post/1
     *
     * La URL incluye "post/" antes del ID para diferenciar este endpoint
     * de un hipotético GET /api/comments/{id} (que buscaría por ID de comentario).
     *
     * @PathVariable → extrae el {postId} de la URL.
     *
     * Si el post no existe, el servicio lanza ResourceNotFoundException → 404.
     *
     * @param postId el ID del post del que se quieren los comentarios
     * @return 200 OK con la lista de comentarios, ordenados por fecha ASC
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
}
