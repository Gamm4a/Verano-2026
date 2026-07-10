package com.example.Nexus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ============================================================
 *  UserRequest — DTO (Data Transfer Object) para crear un usuario
 * ============================================================
 *
 * ¿Qué es un DTO?
 * Es un objeto que se usa para recibir datos del cliente (el cuerpo
 * del request HTTP en formato JSON). Es diferente a la entidad (User)
 * porque:
 *   - Puede tener validaciones específicas para la entrada.
 *   - Protege la entidad de recibir campos no deseados (ej: el "id").
 *   - Permite que la estructura del JSON sea diferente a la de la tabla.
 *
 * El cliente envía un JSON como este:
 * {
 *   "username": "juan_backend",
 *   "email":    "juan@nexus.com",
 *   "password": "miPassword123"
 * }
 *
 * Spring deserializa automáticamente ese JSON a este objeto.
 *
 * @Data → Lombok genera getters y setters para que Spring pueda
 *         asignar los valores del JSON a los campos.
 */
@Data
public class UserRequest {

    /**
     * Nombre de usuario deseado.
     *
     * @NotBlank → la validación de Spring falla si el campo es null,
     *             vacío ("") o solo contiene espacios en blanco.
     *             Si falla, se responde con 400 Bad Request y el mensaje
     *             indicado en "message".
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    /**
     * Correo electrónico del usuario.
     *
     * @NotBlank → no puede estar vacío.
     * @Email    → verifica que el formato sea válido (ej: usuario@dominio.com).
     *             Si no lo es, responde 400 con el mensaje de error.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    /**
     * Contraseña en texto plano.
     *
     * @NotBlank → no puede estar vacía.
     *
     * NOTA: En producción esta contraseña debe encriptarse con BCrypt
     * antes de guardarla en la base de datos. Aquí se omite por
     * simplicidad académica.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
