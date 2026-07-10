package com.example.Nexus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 *  GlobalExceptionHandler — Manejador global de excepciones
 * ============================================================
 *
 * ¿Qué problema resuelve esta clase?
 * Sin esta clase, cuando un Service lanza una excepción (como
 * ResourceNotFoundException), Spring respondería con un JSON
 * de error genérico de Whitelabel Error Page poco amigable.
 * Con esta clase, interceptamos esas excepciones y devolvemos
 * respuestas JSON limpias con el código HTTP apropiado.
 *
 * @ControllerAdvice → indica a Spring que esta clase actúa como
 *   un "interceptor global" de excepciones para TODOS los controladores.
 *   Funciona como un controlador especial que solo maneja errores.
 *
 * Patrón utilizado: Centralized Exception Handling.
 * Ventaja: no necesitamos try-catch en cada Controller o Service;
 * toda la gestión de errores está en un solo lugar.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja el caso en que un recurso no se encuentra en la BD.
     *
     * @ExceptionHandler(ResourceNotFoundException.class) → Spring llama
     *   a este método automáticamente cuando cualquier Controller o Service
     *   lanza una ResourceNotFoundException.
     *
     * Respuesta enviada al cliente:
     * HTTP 404 Not Found
     * {
     *   "error": "Usuario no encontrado con ID: 5"
     * }
     *
     * @param ex la excepción capturada con su mensaje
     * @return ResponseEntity con 404 y el mensaje de error en JSON
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage()); // El mensaje que pasamos al constructor
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja los errores de validación de los DTOs.
     *
     * Cuando un campo falla @NotBlank, @Email u otra validación
     * (anotaciones de jakarta.validation), Spring lanza
     * MethodArgumentNotValidException antes de llegar al Controller.
     *
     * Este método extrae todos los errores de validación y los
     * convierte en un Map con el nombre del campo → mensaje de error.
     *
     * Respuesta enviada al cliente:
     * HTTP 400 Bad Request
     * {
     *   "username": "El nombre de usuario es obligatorio",
     *   "email":    "El email no tiene un formato válido"
     * }
     *
     * @param ex la excepción con los detalles de los errores de validación
     * @return ResponseEntity con 400 y un mapa de errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Iteramos sobre todos los errores de validación encontrados
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Obtenemos el nombre del campo que falló (ej: "username", "email")
            String fieldName = ((FieldError) error).getField();
            // Obtenemos el mensaje de error definido en la anotación (ej: "El email es obligatorio")
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de último recurso: captura CUALQUIER excepción no controlada.
     *
     * Si ocurre un error inesperado en la aplicación (NullPointerException,
     * error de BD, etc.) que nadie más captura, este método evita que
     * el servidor responda con un stack trace o un error 500 genérico feo.
     *
     * Respuesta enviada al cliente:
     * HTTP 500 Internal Server Error
     * {
     *   "error": "Ocurrió un error interno en el servidor"
     * }
     *
     * Nota: el mensaje es genérico intencionalmente, para no exponer
     * detalles internos del sistema al cliente.
     *
     * @param ex la excepción genérica capturada
     * @return ResponseEntity con 500 y mensaje de error genérico
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error interno en el servidor");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
