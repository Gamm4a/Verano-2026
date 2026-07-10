package com.example.Nexus.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ============================================================
 *  Post — Entidad / Modelo de la base de datos
 * ============================================================
 *
 * Representa la tabla "posts" en MySQL.
 * Un Post pertenece a un User y puede tener muchos Comments.
 *
 * @Data   → Lombok genera automáticamente getters y setters.
 * @Entity → JPA mapea esta clase a la tabla de BD.
 * @Table  → nombre explícito de la tabla en MySQL.
 */
@Data
@Entity
@Table(name = "posts")
public class Post {

    /** Clave primaria con autoincremento gestionado por MySQL. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título del post. No puede ser vacío (NOT NULL en la BD). */
    @Column(nullable = false)
    private String title;

    /**
     * Contenido del post.
     * columnDefinition = "TEXT" → en MySQL se crea como tipo TEXT,
     * que permite almacenar textos largos (hasta ~65,535 caracteres),
     * a diferencia de VARCHAR que está limitado a 255 por defecto.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * Fecha y hora de creación del post.
     * Se asigna automáticamente al guardar por primera vez
     * mediante el método onCreate() anotado con @PrePersist.
     */
    private LocalDateTime createdAt;

    /**
     * Relación muchos-a-uno: muchos posts pertenecen a un solo usuario.
     *
     * @ManyToOne → define la relación de multiplicidad.
     * @JoinColumn → especifica la columna de la FOREIGN KEY en la tabla
     *              "posts" que referencia a la tabla "users".
     *              En MySQL la columna se llama "user_id".
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Lista de comentarios de este post.
     *
     * @OneToMany → un post puede tener muchos comentarios.
     * mappedBy   → la foreign key está en la entidad Comment, campo "post".
     * cascade    → si se elimina el post, sus comentarios también se eliminan.
     *
     * @JsonIgnore → previene la referencia circular al serializar a JSON.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    /**
     * Método del ciclo de vida de JPA.
     *
     * @PrePersist → Spring llama automáticamente a este método
     *              ANTES de insertar el registro en la base de datos.
     *              Aquí asignamos la fecha/hora actual a "createdAt",
     *              así no tenemos que enviarlo desde el cliente.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
