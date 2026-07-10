package com.example.Nexus.repositories;

import com.example.Nexus.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * ============================================================
 *  CommentRepository — Repositorio de acceso a datos de Comment
 * ============================================================
 *
 * Extiende JpaRepository<Comment, Long> para obtener los métodos
 * CRUD estándar. Los métodos personalizados se declaran aquí
 * y Spring genera el SQL automáticamente por convención de nombres.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Obtiene todos los comentarios de un post específico,
     * ordenados por fecha de creación de más antiguo a más reciente.
     *
     * Spring JPA interpreta el nombre del método así:
     *   findBy            → SELECT * FROM comments WHERE
     *   PostId            → post_id = ?
     *   OrderBy           → ORDER BY
     *   CreatedAt         → created_at
     *   Asc               → ASC
     *
     * Query generada:
     *   SELECT * FROM comments WHERE post_id = ? ORDER BY created_at ASC;
     *
     * El orden ascendente (ASC) es natural para comentarios, ya que
     * queremos ver primero el comentario más antiguo (como en un hilo
     * de conversación).
     *
     * @param postId el ID del post del que se quieren los comentarios
     * @return lista de comentarios ordenados cronológicamente
     */
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
}
