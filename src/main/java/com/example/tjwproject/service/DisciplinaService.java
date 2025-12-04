package com.example.tjwproject.service;

import com.example.tjwproject.model.Disciplina;
import com.example.tjwproject.repository.DisciplinaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
    }

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void excluir(Long id) {
        disciplinaRepository.deleteById(id);
    }

    public long contar() {
        return disciplinaRepository.count();
    }
}
