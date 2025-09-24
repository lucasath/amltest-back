package com.example.amltest.mapper;

import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.entity.Categoria;
import com.example.amltest.entity.Tarefa;
import com.example.amltest.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TarefaMapperTest {

    private TarefaMapper tarefaMapper;

    @BeforeEach
    void setUp() {
        tarefaMapper = new TarefaMapper();
    }

    @Test
    void toResponse() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Estudar Spring Boot");
        tarefa.setDescricao("Fazer exercícios com JPA");
        tarefa.setPrazo(LocalDate.of(2025, 9, 30));
        tarefa.setConcluida(false);
        tarefa.setCategoria(CategoriaEnum.ESTUDO);

        TarefaResponse response = tarefaMapper.toResponse(tarefa);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Estudar Spring Boot", response.titulo());
        assertEquals("Fazer exercícios com JPA", response.descricao());
        assertEquals(LocalDate.of(2025, 9, 30), response.prazo());
        assertFalse(response.concluida());
        assertEquals(CategoriaEnum.ESTUDO, response.categoria());
    }

    @Test
    void toEntityConcluidaNull() {
        TarefaRequest request = new TarefaRequest(
                "Aprender Next.js",
                "Criar um projeto com React e Tailwind",
                LocalDate.of(2025, 10, 5),
                null,
                CategoriaEnum.TRABALHO
        );

        Tarefa entity = tarefaMapper.toEntity(request);

        assertNotNull(entity);
        assertEquals("Aprender Next.js", entity.getTitulo());
        assertEquals("Criar um projeto com React e Tailwind", entity.getDescricao());
        assertEquals(LocalDate.of(2025, 10, 5), entity.getPrazo());
        assertFalse(entity.getConcluida(), "Concluída deve ser false por padrão");
        assertEquals(CategoriaEnum.TRABALHO, entity.getCategoria());
    }

    @Test
    void toEntityConcluidaTrue() {
        TarefaRequest request = new TarefaRequest(
                "Deploy no Heroku",
                "Publicar aplicação Spring Boot",
                LocalDate.of(2025, 11, 1),
                true,
                CategoriaEnum.PESSOAL
        );

        Tarefa entity = tarefaMapper.toEntity(request);

        assertTrue(entity.getConcluida(), "Concluída deve ser true quando informado");
        assertEquals(CategoriaEnum.PESSOAL, entity.getCategoria());
    }
}