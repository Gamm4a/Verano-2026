package com.example.Nexus.controllers;

import com.example.Nexus.dto.UserRequest;
import com.example.Nexus.models.User;
import com.example.Nexus.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================
 *  UserController — Controlador REST para usuarios
 * ============================================================
 *
 * ¿Qué es un Controller?
 * Es la capa que expone los endpoints HTTP de la API. Recibe las
 * peticiones del cliente, las delega al Service y devuelve la
 * respuesta HTTP adecuada.
 *
 * @RestController → combina @Controller + @ResponseBody.
 *   - @Controller: marca la clase como controlador de Spring MVC.
 *   - @ResponseBody: los métodos retornan datos directamente en el
 *     cuerpo de la respuesta (serializado a JSON), no vistas HTML.
 *
 * @RequestMapping("/api/users") → todos los endpoints de este
 *   controlador comenzarán con la ruta base "/api/users".
 *
 * Endpoints expuestos:
 *   POST   /api/users       → crear un nuevo usuario
 *   GET    /api/users/{id}  → obtener usuario por ID
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Spring inyecta automáticamente el UserService.
     * El controller SOLO habla con el service, nunca directamente
     * con el repositorio. Esto mantiene la separación de capas.
     */
    @Autowired
    private UserService userService;

    /**
     * Crea un nuevo usuario.
     *
     * Método HTTP: POST
     * URL:         /api/users
     * Body:        JSON con username, email, password
     *
     * @PostMapping → mapea las peticiones HTTP POST a este método.
     *
     * @Valid → activa las validaciones del DTO (UserRequest).
     *          Si algún campo no pasa la validación, Spring lanza
     *          MethodArgumentNotValidException antes de llegar aquí,
     *          que el GlobalExceptionHandler convierte en 400 Bad Request.
     *
     * @RequestBody → deserializa el JSON del body al objeto UserRequest.
     *
     * ResponseEntity<User> → nos permite controlar tanto el cuerpo
     *                        como el código de estado HTTP de la respuesta.
     *
     * @param request los datos del usuario a crear
     * @return 201 Created con el nuevo usuario en el body
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
        User createdUser = userService.createUser(request);
        // HttpStatus.CREATED → código 201, indica que se creó un recurso
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * Método HTTP: GET
     * URL:         /api/users/{id}
     * Ejemplo:     GET /api/users/1
     *
     * @GetMapping("/{id}") → mapea GET con variable en la ruta.
     *
     * @PathVariable → extrae el valor {id} de la URL y lo inyecta
     *                como parámetro del método.
     *
     * ResponseEntity.ok(...) → crea una respuesta con código 200 OK
     *                          y el objeto User serializado a JSON.
     *
     * Si el usuario no existe, UserService lanza ResourceNotFoundException
     * que el GlobalExceptionHandler convierte en 404 Not Found.
     *
     * @param id el ID del usuario a buscar
     * @return 200 OK con el usuario, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
