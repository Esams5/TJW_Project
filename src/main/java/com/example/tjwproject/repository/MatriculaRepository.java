package com.example.tjwproject.repository;

import com.example.tjwproject.model.Aluno;
import com.example.tjwproject.model.Disciplina;
import com.example.tjwproject.model.Matricula;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    boolean existsByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);
    Optional<Matricula> findByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);
}
