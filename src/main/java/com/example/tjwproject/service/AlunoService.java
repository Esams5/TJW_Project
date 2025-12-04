package com.example.tjwproject.service;

import com.example.tjwproject.model.Aluno;
import com.example.tjwproject.repository.AlunoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    public Aluno salvar(Aluno aluno) {
        boolean matriculaEmUso = aluno.getId() == null
                ? alunoRepository.findByMatricula(aluno.getMatricula()).isPresent()
                : alunoRepository.existsByMatriculaAndIdNot(aluno.getMatricula(), aluno.getId());
        if (matriculaEmUso) {
            throw new IllegalStateException("Já existe um aluno com essa matrícula");
        }
        return alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }

    public long contar() {
        return alunoRepository.count();
    }
}
