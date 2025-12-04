package com.example.tjwproject.controller;

import com.example.tjwproject.model.Disciplina;
import com.example.tjwproject.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/disciplinas")
@PreAuthorize("hasRole('ADMIN')")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "disciplinas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("disciplina", disciplinaService.buscarPorId(id));
        return "disciplinas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("disciplina") Disciplina disciplina,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "disciplinas/form";
        }
        disciplinaService.salvar(disciplina);
        redirectAttributes.addFlashAttribute("sucesso", "Disciplina salva com sucesso!");
        return "redirect:/disciplinas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        disciplinaService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Disciplina excluída com sucesso!");
        return "redirect:/disciplinas";
    }
}
