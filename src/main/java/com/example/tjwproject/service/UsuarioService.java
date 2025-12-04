package com.example.tjwproject.service;

import com.example.tjwproject.model.Usuario;
import com.example.tjwproject.model.enums.Perfil;
import com.example.tjwproject.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void criarUsuarioSeNaoExistir(String login, String nome, String senhaPura, Perfil perfil) {
        usuarioRepository.findByLogin(login).ifPresentOrElse(
            existente -> {},
            () -> {
                Usuario usuario = new Usuario(nome, login, passwordEncoder.encode(senhaPura), perfil);
                usuarioRepository.save(usuario);
            }
        );
    }
}
