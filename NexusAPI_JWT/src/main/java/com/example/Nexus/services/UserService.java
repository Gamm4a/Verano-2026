package com.example.Nexus.services;

import com.example.Nexus.models.User;
import com.example.Nexus.dto.UserRequest;
import com.example.Nexus.repositories.UserRepository;
import com.example.Nexus.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ============================================================
 *  UserService — Lógica de negocio para la entidad User
 * ============================================================
 *
 * ¿Qué es un Service?
 * Es la capa intermedia entre el Controller (que recibe la petición HTTP)
 * y el Repository (que habla con la BD). Aquí va toda la lógica de negocio:
 * validaciones, transformaciones, reglas del sistema, etc.
 *
 * Arquitectura en capas del proyecto:
 *   [Cliente] → [Controller] → [Service] → [Repository] → [BD]
 *
 * @Service → Spring registra esta clase como un "bean" de servicio.
 *           Esto permite inyectarla con @Autowired en otras clases.
 */
@Service
public class UserService {

    /**
     * @Autowired → Spring inyecta automáticamente una instancia de
     *             UserRepository. No necesitamos hacer "new UserRepository()".
     *             Esto se llama Inyección de Dependencias (DI),
     *             un principio fundamental de Spring.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * Pasos de la lógica:
     * 1. Verifica que el username no esté en uso (regla de negocio).
     * 2. Construye el objeto User con los datos del request.
     * 3. Guarda el User en la BD y retorna el objeto persistido.
     *
     * @param request el DTO con username, email y password del cliente
     * @return el User recién guardado en la BD (con su ID asignado)
     * @throws RuntimeException si el username ya está en uso
     */
    public User createUser(UserRequest request) {
        // Verificamos si ya existe un usuario con ese nombre
        // antes de intentar insertar (evita error de UNIQUE constraint)
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        // Construimos el objeto User con los datos recibidos del cliente
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        // NOTA: En producción aquí deberíamos encriptar el password:
        // user.setPassword(passwordEncoder.encode(request.getPassword()));

        // save() genera un INSERT en la BD y devuelve el objeto con el ID asignado
        return userRepository.save(user);
    }

    /**
     * Busca y devuelve un usuario por su ID.
     *
     * Usa Optional para manejar el caso en que el ID no exista,
     * lanzando una excepción personalizada en lugar de retornar null.
     *
     * @param id el ID del usuario a buscar
     * @return el User encontrado
     * @throws ResourceNotFoundException si no existe un User con ese ID
     */
    public User getUserById(Long id) {
        // findById devuelve Optional<User>
        // orElseThrow: si el Optional está vacío (usuario no existe),
        //              lanza la excepción que le indicamos
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
