package com.example.amltest.entity;

import com.example.amltest.enums.CategoriaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate prazo;

    private Boolean concluida = false;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
}