package com.example.Nexus.exceptions;

/**
 * ============================================================
 *  ResourceNotFoundException — Excepción personalizada 404
 * ============================================================
 *
 * ¿Para qué creamos excepciones personalizadas?
 * En lugar de retornar null o lanzar excepciones genéricas cuando
 * un recurso no existe en la BD, creamos esta excepción específica
 * para que el GlobalExceptionHandler la capture y responda con
 * el código HTTP correcto (404 Not Found).
 *
 * Extiende RuntimeException → es una excepción no verificada
 * (unchecked), lo que significa que no hay que declararla con
 * "throws" en los métodos que la usan.
 *
 * Flujo cuando se lanza esta excepción:
 *   Service lanza ResourceNotFoundException
 *     → GlobalExceptionHandler la captura con @ExceptionHandler
 *       → Responde al cliente con 404 Not Found + mensaje de error JSON
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     *
     * Llama a super(message) para que RuntimeException almacene
     * el mensaje, que luego se puede recuperar con getMessage().
     *
     * Ejemplo de uso:
     *   throw new ResourceNotFoundException("Usuario no encontrado con ID: 5");
     *
     * @param message descripción del recurso que no se encontró
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
