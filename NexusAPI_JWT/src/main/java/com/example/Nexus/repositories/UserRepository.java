package com.example.Nexus.repositories;

import com.example.Nexus.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * ============================================================
 *  UserRepository — Repositorio de acceso a datos de User
 * ============================================================
 *
 * ¿Qué es un Repository?
 * Es la capa que se comunica directamente con la base de datos.
 * Gracias a Spring Data JPA, solo necesitamos definir una interfaz
 * y Spring genera automáticamente toda la implementación SQL.
 * ¡No se escribe ninguna query manualmente!
 *
 * JpaRepository<User, Long> nos hereda métodos listos para usar:
 *   - save(user)        → INSERT o UPDATE
 *   - findById(id)      → SELECT por ID
 *   - findAll()         → SELECT *
 *   - deleteById(id)    → DELETE por ID
 *   - existsById(id)    → SELECT COUNT(*) > 0
 *   ... y muchos más.
 *
 * El segundo tipo genérico (Long) es el tipo de la clave primaria (@Id).
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * Spring Data JPA interpreta el nombre del método y genera
     * automáticamente esta query:
     *   SELECT * FROM users WHERE username = ?
     *
     * Retorna Optional<User> porque el usuario puede no existir.
     * Optional evita los NullPointerException al forzar al llamador
     * a manejar el caso de "no encontrado".
     *
     * @param username el nombre de usuario a buscar
     * @return Optional con el User si existe, vacío si no
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica si ya existe un usuario con ese nombre de usuario.
     *
     * Spring genera automáticamente:
     *   SELECT COUNT(*) > 0 FROM users WHERE username = ?
     *
     * Se usa en UserService para evitar duplicados antes de crear
     * un nuevo usuario.
     *
     * @param username el nombre de usuario a verificar
     * @return true si ya existe, false si está disponible
     */
    boolean existsByUsername(String username);
}
