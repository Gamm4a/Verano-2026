package com.example.Nexus.services;

import com.example.Nexus.dto.CommentRequest;
import com.example.Nexus.models.Comment;
import com.example.Nexus.models.Post;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ============================================================
 *  CommentService — Lógica de negocio para la entidad Comment
 * ============================================================
 *
 * Maneja las operaciones sobre comentarios:
 *   - Crear un comentario asociado a un post y un usuario.
 *   - Obtener todos los comentarios de un post específico.
 *
 * Este servicio demuestra cómo colaborar con múltiples servicios
 * (PostService y UserService) para validar y obtener las entidades
 * relacionadas antes de crear el comentario.
 *
 * @Service → Spring registra esta clase como bean de servicio
 *           y la administra en su contenedor (Application Context).
 */
@Service
public class CommentService {

    /**
     * Repositorio de comentarios: accede directamente a la tabla "comments".
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Servicio de posts: usado para validar que el post exista
     * y para obtener la entidad Post que se asociará al comentario.
     */
    @Autowired
    private PostService postService;

    /**
     * Servicio de usuarios: usado para validar que el usuario exista
     * y para obtener la entidad User que se asociará al comentario.
     */
    @Autowired
    private UserService userService;

    /**
     * Crea y guarda un nuevo comentario en la base de datos.
     *
     * Pasos:
     * 1. Verifica que el Post existe (lanza 404 si no).
     * 2. Verifica que el User existe (lanza 404 si no).
     * 3. Construye el objeto Comment y establece las relaciones.
     * 4. Guarda el comentario en la BD.
     *
     * La fecha (createdAt) se asigna automáticamente por @PrePersist.
     *
     * @param request DTO con text, postId y userId del cliente
     * @return el Comment guardado con ID y createdAt asignados
     */
    public Comment createComment(CommentRequest request) {
        // Buscamos el post (si no existe, se lanza ResourceNotFoundException → 404)
        Post post = postService.getPostById(request.getPostId());

        // Buscamos el usuario (si no existe, se lanza ResourceNotFoundException → 404)
        User user = userService.getUserById(request.getUserId());

        // Construimos el objeto Comment y establecemos sus relaciones
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setPost(post);   // Asociamos el Comment al Post (foreign key post_id)
        comment.setUser(user);   // Asociamos el Comment al User (foreign key user_id)

        // Guardamos en la BD; JPA ejecuta INSERT INTO comments (...)
        return commentRepository.save(comment);
    }

    /**
     * Devuelve todos los comentarios de un post dado, ordenados
     * cronológicamente (del más antiguo al más reciente).
     *
     * Primero valida que el post exista. Si no existe, lanza 404.
     * Luego consulta la BD por los comentarios de ese post.
     *
     * @param postId el ID del post del que se quieren los comentarios
     * @return lista de comentarios en orden ascendente de fecha
     */
    public List<Comment> getCommentsByPost(Long postId) {
        // Validar que el post existe antes de buscar sus comentarios
        // Si no existe, getPostById lanzará ResourceNotFoundException
        postService.getPostById(postId);

        // Retornamos los comentarios del post, ordenados por fecha ASC
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }
}
