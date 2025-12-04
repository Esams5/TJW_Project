package com.example.tjwproject.repository;

import com.example.tjwproject.model.Aluno;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
}
