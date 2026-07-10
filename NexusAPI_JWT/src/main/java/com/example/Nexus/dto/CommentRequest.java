package com.example.Nexus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * ============================================================
 *  CommentRequest — DTO para crear un nuevo comentario
 * ============================================================
 *
 * El cliente envía un JSON con esta estructura:
 * {
 *   "text":   "¡Excelente post!",
 *   "postId": 1,
 *   "userId": 2
 * }
 *
 * Spring mapea automáticamente ese JSON a este objeto cuando
 * el controlador usa la anotación @RequestBody.
 *
 * @Data → Lombok genera getters/setters.
 */
@Data
public class CommentRequest {

    /**
     * Texto del comentario. Campo obligatorio.
     *
     * @NotBlank → Spring valida que el campo no sea nulo ni esté vacío.
     *             Si falla, responde automáticamente con 400 Bad Request.
     */
    @NotBlank(message = "El texto es obligatorio")
    private String text;

    /**
     * ID del post al que pertenece el comentario.
     * El servicio lo usará para buscar el Post en la BD y asociarlo.
     */
    private Long postId;

    /**
     * ID del usuario que escribe el comentario.
     *
     * Nota académica: igual que en PostRequest, en un sistema real
     * con JWT este ID vendría del token de autenticación, no del body.
     */
    private Long userId; // Para simular el usuario antes de JWT
}
