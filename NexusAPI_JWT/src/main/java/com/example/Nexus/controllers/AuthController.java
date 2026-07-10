package com.example.Nexus.controllers;

import com.example.Nexus.dto.LoginRequest;
import com.example.Nexus.dto.LoginResponse;
import com.example.Nexus.services.JwtService;
import com.example.Nexus.models.User;
import com.example.Nexus.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ============================================================
 *  AuthController — Endpoints de autenticación
 * ============================================================
 *
 * Este controller maneja todo lo relacionado con autenticación.
 * Por ahora expone UN SOLO endpoint: el login.
 *
 * Ruta base: /api/auth
 *
 * @RestController → responde con JSON (no con HTML).
 * @RequestMapping → prefijo de ruta para todos los métodos.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Repositorio de usuarios: necesitamos buscar al usuario
     * en la BD para verificar sus credenciales.
     *
     * Aquí usamos el Repository directamente (sin pasar por UserService)
     * porque la lógica de autenticación es responsabilidad de este controller,
     * no de UserService que maneja el CRUD de usuarios.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Servicio JWT: lo necesitamos para generar el token
     * una vez que las credenciales han sido validadas.
     */
    @Autowired
    private JwtService jwtService;

    // ================================================================
    //  POST /api/auth/login — Iniciar sesión y obtener un JWT
    // ================================================================

    /**
     * Autentica al usuario y devuelve un JWT si las credenciales son válidas.
     *
     * Método HTTP: POST
     * URL:         /api/auth/login
     * Body JSON:   { "username": "carlos_dev", "password": "1234" }
     *
     * Flujo interno:
     * 1. Busca al usuario por username en la BD.
     * 2. Si no existe → 401 Unauthorized.
     * 3. Compara la contraseña del request con la de la BD.
     * 4. Si no coinciden → 401 Unauthorized.
     * 5. Si todo está bien → genera un JWT con el username como subject.
     * 6. Devuelve el token al cliente.
     *
     * Respuesta exitosa (200 OK):
     * { "token": "eyJhbGciOiJIUzI1NiJ9..." }
     *
     * Respuesta fallida (401 Unauthorized):
     * { "error": "Credenciales inválidas" }
     *
     * @param request el DTO con username y password
     * @return 200 OK con el token, o 401 si las credenciales son incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // ── Paso 1: Buscar el usuario por username ──────────────────────
        //
        // findByUsername devuelve Optional<User>.
        // Si el usuario no existe, orElse(null) nos da null.
        //
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        // ── Paso 2: Verificar que el usuario existe ─────────────────────
        if (user == null) {
            // No revelamos si el error fue en el username o en el password,
            // por seguridad siempre decimos "Credenciales inválidas".
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        // ── Paso 3: Verificar la contraseña ────────────────────────────
        //
        // Comparamos directamente en texto plano porque así está
        // almacenada en la BD (proyecto académico).
        // En producción: passwordEncoder.matches(request.getPassword(), user.getPassword())
        //
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        // ── Paso 4: Generar el JWT ──────────────────────────────────────
        //
        // Usamos el USERNAME como "subject" del token.
        // El subject identifica al portador del token.
        // Cualquier endpoint puede extraer este username del token
        // con: jwtService.extractUsername(token)
        //
        String token = jwtService.generateToken(user.getUsername());

        // ── Paso 5: Devolver el token al cliente ────────────────────────
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
