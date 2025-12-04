package com.example.tjwproject.config;

import com.example.tjwproject.model.enums.Perfil;
import com.example.tjwproject.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public DataLoader(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        usuarioService.criarUsuarioSeNaoExistir("admin", "Administrador", "admin123", Perfil.ROLE_ADMIN);
        usuarioService.criarUsuarioSeNaoExistir("secretaria", "Secretaria", "secret123", Perfil.ROLE_SECRETARIA);
    }
}
