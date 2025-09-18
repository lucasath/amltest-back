package com.example.amltest.dto.tarefa;

import com.example.amltest.enums.CategoriaEnum;

import java.time.LocalDate;

public record TarefaRequest(String titulo, String descricao, LocalDate prazo, Boolean concluida, CategoriaEnum categoria) {
}
