package com.example.Nexus.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ============================================================
 *  User — Entidad / Modelo de la base de datos
 * ============================================================
 *
 * Esta clase representa la tabla "users" en MySQL.
 * Cada instancia de User equivale a una fila en esa tabla.
 *
 * Anotaciones importantes:
 *
 * @Data      (Lombok) → genera automáticamente getters, setters,
 *            toString, equals y hashCode. Evita escribir ese código
 *            manualmente y mantiene la clase limpia.
 *
 * @Entity    → le dice a JPA (Hibernate) que esta clase es una
 *            entidad de base de datos. JPA la mapeará a una tabla.
 *
 * @Table     → especifica el nombre exacto de la tabla en MySQL.
 *            Si no se pusiera, JPA usaría el nombre de la clase ("User").
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Clave primaria de la tabla.
     *
     * @Id                → marca este campo como la PRIMARY KEY.
     * @GeneratedValue    → el valor se genera automáticamente
     *                     (equivalente a AUTO_INCREMENT en MySQL).
     * IDENTITY           → delega la generación al motor de BD.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único del perfil.
     *
     * unique = true  → crea un índice UNIQUE en la columna (no pueden
     *                  existir dos usuarios con el mismo username).
     * nullable = false → la columna es NOT NULL en MySQL.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Correo electrónico del usuario. Debe ser único en la práctica,
     * aunque aquí solo se valida en el DTO (no a nivel de BD).
     */
    @Column(nullable = false)
    private String email;

    /**
     * Contraseña del usuario.
     *
     * @JsonIgnore → este campo NO se incluye en la respuesta JSON.
     *              Así evitamos exponer la contraseña al cliente.
     *              ¡Importante para la seguridad!
     *
     * NOTA ACADÉMICA: En un proyecto real, la contraseña debe estar
     * encriptada con BCrypt. Aquí se guarda en texto plano por
     * simplicidad didáctica.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /**
     * Lista de posts que ha creado este usuario.
     *
     * @OneToMany → relación "uno a muchos": un usuario puede tener
     *             muchos posts.
     * mappedBy   → indica que la columna de unión (foreign key) está
     *             en la tabla "posts", en el campo "user".
     * cascade    → si eliminamos el usuario, sus posts también se
     *             eliminan automáticamente (CascadeType.ALL).
     *
     * @JsonIgnore → evitamos la referencia circular al serializar a JSON.
     *              Sin esto, User → Post → User → Post → ... entraría
     *              en un bucle infinito.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
}
