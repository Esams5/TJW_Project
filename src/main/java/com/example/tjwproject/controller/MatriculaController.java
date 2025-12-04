package com.example.tjwproject.controller;

import com.example.tjwproject.model.Matricula;
import com.example.tjwproject.model.enums.SituacaoMatricula;
import com.example.tjwproject.service.AlunoService;
import com.example.tjwproject.service.DisciplinaService;
import com.example.tjwproject.service.MatriculaService;
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
@RequestMapping("/matriculas")
@PreAuthorize("hasAnyRole('ADMIN','SECRETARIA')")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaController(MatriculaService matriculaService,
                               AlunoService alunoService,
                               DisciplinaService disciplinaService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @ModelAttribute("statusMatriculas")
    public SituacaoMatricula[] statusMatriculas() {
        return SituacaoMatricula.values();
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.listarTodos());
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "matriculas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "matriculas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("matricula", matriculaService.buscarPorId(id));
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodos());
        return "matriculas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("matricula") Matricula matricula,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodos());
            return "matriculas/form";
        }
        try {
            matriculaService.salvar(matricula);
        } catch (IllegalStateException ex) {
            bindingResult.reject("duplicidade", ex.getMessage());
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodos());
            return "matriculas/form";
        }
        redirectAttributes.addFlashAttribute("sucesso", "Matrícula salva com sucesso!");
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        matriculaService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Matrícula excluída com sucesso!");
        return "redirect:/matriculas";
    }
}
