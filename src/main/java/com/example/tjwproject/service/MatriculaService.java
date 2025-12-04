package com.example.tjwproject.service;

import com.example.tjwproject.model.Matricula;
import com.example.tjwproject.repository.MatriculaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getId() == null && matriculaRepository.existsByAlunoAndDisciplina(
                matricula.getAluno(), matricula.getDisciplina())) {
            throw new IllegalStateException("O aluno já está matriculado nesta disciplina");
        }
        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }
        return matriculaRepository.save(matricula);
    }

    public void excluir(Long id) {
        matriculaRepository.deleteById(id);
    }

    public long contar() {
        return matriculaRepository.count();
    }
}
