package com.example.Nexus.dto;

import lombok.Data;

/**
 * ============================================================
 *  LoginRequest — DTO para la petición de login
 * ============================================================
 *
 * ¿Qué es este objeto?
 * Es el "molde" que representa el JSON que el cliente envía
 * cuando quiere iniciar sesión.
 *
 * El cliente envía una petición así:
 * POST /api/auth/login
 * Body (JSON):
 * {
 *   "username": "carlos_dev",
 *   "password": "1234"
 * }
 *
 * Spring deserializa ese JSON automáticamente a este objeto
 * gracias a @RequestBody en el AuthController.
 *
 * ¿Por qué no usamos UserRequest?
 * Porque UserRequest también tiene el campo "email", que en el
 * login no es necesario. Cada operación tiene su propio DTO,
 * con solo los campos que esa operación necesita.
 *
 * @Data (Lombok) → genera getters y setters para que Spring pueda
 *                  asignar los valores del JSON a los campos.
 */
@Data
public class LoginRequest {

    /**
     * Nombre de usuario con el que se quiere autenticar.
     * Debe coincidir exactamente con un username existente en la BD.
     */
    private String username;

    /**
     * Contraseña en texto plano que se va a comparar con la
     * contraseña almacenada en la base de datos.
     */
    private String password;
}
