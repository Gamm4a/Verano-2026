package com.example.Nexus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ============================================================
 *  Comment — Entidad / Modelo de la base de datos
 * ============================================================
 *
 * Representa la tabla "comments" en MySQL.
 * Un comentario pertenece a un Post Y a un User.
 * Es el extremo "muchos" en dos relaciones ManyToOne:
 *   - Muchos comentarios → un post
 *   - Muchos comentarios → un usuario
 *
 * @Data   → Lombok genera getters, setters, equals, hashCode, toString.
 * @Entity → JPA mapea la clase a una tabla de la BD.
 * @Table  → nombre de la tabla en MySQL.
 */
@Data
@Entity
@Table(name = "comments")
public class Comment {

    /** Clave primaria con autoincremento. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Texto del comentario.
     * Se define como TEXT en MySQL para soportar comentarios largos.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    /**
     * Fecha y hora en que se creó el comentario.
     * Se asigna automáticamente con @PrePersist (ver método onCreate).
     */
    private LocalDateTime createdAt;

    /**
     * Referencia al post al que pertenece el comentario.
     *
     * @ManyToOne → muchos comentarios pueden pertenecer a un post.
     * @JoinColumn → columna "post_id" en la tabla "comments" que
     *              actúa como FOREIGN KEY hacia la tabla "posts".
     *
     * @JsonIgnore → al serializar un Comment a JSON, NO incluimos
     *              el objeto Post completo para evitar referencias circulares
     *              (Comment → Post → List<Comment> → Comment → ...).
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * Referencia al usuario que escribió el comentario.
     *
     * @ManyToOne → muchos comentarios pueden pertenecer a un usuario.
     * @JoinColumn → columna "user_id" en la tabla "comments" como
     *              FOREIGN KEY hacia la tabla "users".
     *
     * Nota: aquí SÍ se incluye el User en el JSON (sin @JsonIgnore),
     * así el cliente sabe quién escribió el comentario.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * @PrePersist → JPA ejecuta este método automáticamente antes
     *              de insertar el comentario en la BD, asignando
     *              la fecha/hora de creación.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
