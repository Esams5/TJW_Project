package com.example.tjwproject.controller;

import com.example.tjwproject.service.AlunoService;
import com.example.tjwproject.service.DisciplinaService;
import com.example.tjwproject.service.MatriculaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final MatriculaService matriculaService;

    public HomeController(AlunoService alunoService, DisciplinaService disciplinaService, MatriculaService matriculaService) {
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.matriculaService = matriculaService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("totalAlunos", alunoService.contar());
        model.addAttribute("totalDisciplinas", disciplinaService.contar());
        model.addAttribute("totalMatriculas", matriculaService.contar());
        model.addAttribute("usuario", authentication != null ? authentication.getName() : "Visitante");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
