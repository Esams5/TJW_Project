package com.example.tjwproject.service;

import com.example.tjwproject.model.Aluno;
import com.example.tjwproject.model.Disciplina;
import com.example.tjwproject.model.Matricula;
import com.example.tjwproject.model.enums.AlunoStatus;
import com.example.tjwproject.model.enums.SituacaoMatricula;
import com.example.tjwproject.repository.AlunoRepository;
import com.example.tjwproject.repository.DisciplinaRepository;
import com.example.tjwproject.repository.MatriculaRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatriculaService {

    private static final Pattern SEMESTRE_PATTERN = Pattern.compile("^(\\d{4})\\.(1|2)$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoRepository alunoRepository,
                            DisciplinaRepository disciplinaRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));
    }

    public Matricula salvar(Matricula matricula) {
        Aluno aluno = alunoRepository.findById(matricula.getAluno().getId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno inválido"));
        if (aluno.getStatus() == AlunoStatus.INATIVO) {
            throw new IllegalStateException("Alunos inativos não podem receber novas matrículas");
        }
        Disciplina disciplina = disciplinaRepository.findById(matricula.getDisciplina().getId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina inválida"));

        if (matricula.getId() == null && matriculaRepository.existsByAlunoAndDisciplina(aluno, disciplina)) {
            throw new IllegalStateException("O aluno já está matriculado nesta disciplina");
        }

        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        if (matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }
        validarJanelaMatricula(matricula.getDataMatricula(), disciplina.getSemestre());
        validarNotaParaAprovacao(matricula);
        return matriculaRepository.save(matricula);
    }

    public void excluir(Long id) {
        matriculaRepository.deleteById(id);
    }

    public long contar() {
        return matriculaRepository.count();
    }

    private void validarJanelaMatricula(LocalDate dataMatricula, String semestre) {
        Matcher matcher = SEMESTRE_PATTERN.matcher(semestre);
        if (!matcher.matches()) {
            throw new IllegalStateException("Semestre da disciplina em formato inválido");
        }
        int ano = Integer.parseInt(matcher.group(1));
        int periodo = Integer.parseInt(matcher.group(2));

        Month mesReferencia = periodo == 1 ? Month.FEBRUARY : Month.AUGUST;
        LocalDate inicio = LocalDate.of(ano, mesReferencia, 1);
        LocalDate fim = LocalDate.of(ano, mesReferencia, 5);

        if (dataMatricula.isBefore(inicio) || dataMatricula.isAfter(fim)) {
            throw new IllegalStateException(String.format(
                "Matrículas para o semestre %s só são permitidas entre %s e %s",
                semestre,
                inicio.format(DATE_FORMATTER),
                fim.format(DATE_FORMATTER)
            ));
        }
    }

    private void validarNotaParaAprovacao(Matricula matricula) {
        if (matricula.getStatus() == SituacaoMatricula.APROVADO) {
            Double notaFinal = matricula.getNotaFinal();
            if (notaFinal == null || notaFinal < 5.0) {
                throw new IllegalStateException("Para aprovar o aluno é necessário informar nota final mínima 5.0");
            }
        }
    }
}
