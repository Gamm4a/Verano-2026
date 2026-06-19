package com.example.GameVault.service;

import com.example.GameVault.model.Usuario;
import com.example.GameVault.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionIdChangedEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession httpSession;

    public Usuario registrar(Usuario usuario){
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        return usuarioRepository.save(usuario);

    }

    public Usuario autenticar(String email, String contrasenia){
        Optional<Usuario> usuarioOpt= usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()){
            Usuario usuario= usuarioOpt.get();
            if (passwordEncoder.matches(contrasenia, usuario.getContrasenia())){
                return usuario;
            }
        }
        return null;


    }

    public Usuario obtenerUsuarioLogueado() {
        Long usuarioID = (Long) httpSession.getAttribute("usuario_id");

        if (usuarioID != null) {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioID);
            if (usuarioOpt.isPresent()) {
                return usuarioOpt.get();
            }

        }
        return null;


    }
}
