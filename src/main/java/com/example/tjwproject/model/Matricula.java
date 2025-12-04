package com.example.tjwproject.model;

import com.example.tjwproject.model.enums.SituacaoMatricula;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas",
       uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "disciplina_id"}))
public class Matricula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    @NotNull
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    @NotNull
    private Disciplina disciplina;

    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoMatricula status = SituacaoMatricula.CURSANDO;

    @Column(name = "nota_final")
    private Double notaFinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public SituacaoMatricula getStatus() {
        return status;
    }

    public void setStatus(SituacaoMatricula status) {
        this.status = status;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
