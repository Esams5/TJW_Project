package com.example.tjwproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "disciplinas")
public class Disciplina extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "Código deve conter exatamente 4 dígitos numéricos")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Min(1)
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @NotBlank
    @Pattern(regexp = "\\d{4}\\.(1|2)", message = "Semestre deve seguir o padrão YYYY.1 ou YYYY.2")
    @Column(nullable = false)
    private String semestre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}
