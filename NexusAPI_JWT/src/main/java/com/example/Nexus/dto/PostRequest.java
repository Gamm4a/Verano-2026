package com.example.Nexus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ============================================================
 *  PostRequest — DTO para crear un nuevo post
 * ============================================================
 *
 * El cliente envía un JSON con esta estructura:
 * {
 *   "title":   "Mi primer post",
 *   "content": "Contenido del post...",
 *   "userId":  1
 * }
 *
 * El DTO sirve como "formulario de entrada". Spring lo valida
 * automáticamente gracias a las anotaciones de jakarta.validation
 * y al uso de @Valid en el controlador.
 *
 * @Data → Lombok genera getters/setters automáticamente.
 */
@Data
public class PostRequest {

    /**
     * Título del post. Campo obligatorio.
     *
     * @NotBlank → rechaza el campo si es null, "" o solo espacios.
     *             Spring responde con 400 Bad Request si no se cumple.
     */
    @NotBlank(message = "El título es obligatorio")
    private String title;

    /**
     * Contenido del post. Campo obligatorio.
     *
     * @NotBlank → mismo comportamiento que en "title".
     */
    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    /**
     * ID del usuario que crea el post.
     *
     * Nota académica: En esta versión del proyecto, el ID del usuario
     * se envía manualmente desde el cliente para simular que hay una
     * sesión activa. En un sistema con autenticación JWT real, este
     * campo no existiría; el ID se extraería del token de seguridad.
     */
    private Long userId; // Para simular el usuario antes de JWT
}
