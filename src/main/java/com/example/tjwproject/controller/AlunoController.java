package com.example.tjwproject.controller;

import com.example.tjwproject.model.Aluno;
import com.example.tjwproject.model.enums.AlunoStatus;
import com.example.tjwproject.service.AlunoService;
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
@RequestMapping("/alunos")
@PreAuthorize("hasAnyRole('ADMIN','SECRETARIA')")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @ModelAttribute("statusList")
    public AlunoStatus[] statusList() {
        return AlunoStatus.values();
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "alunos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscarPorId(id));
        return "alunos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("aluno") Aluno aluno,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "alunos/form";
        }
        alunoService.salvar(aluno);
        redirectAttributes.addFlashAttribute("sucesso", "Aluno salvo com sucesso!");
        return "redirect:/alunos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alunoService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Aluno excluído com sucesso!");
        return "redirect:/alunos";
    }
}
