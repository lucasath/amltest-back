package com.example.amltest.mapper;
import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.entity.Tarefa;
import org.springframework.stereotype.Component;

@Component
public class TarefaMapper {

    public TarefaResponse toResponse(Tarefa tarefa) {
        return new TarefaResponse(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getPrazo(), tarefa.getConcluida(), tarefa.getCategoria());
    }

    public Tarefa toEntity(TarefaRequest tarefaRequest) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tarefaRequest.titulo());
        tarefa.setDescricao((tarefaRequest.descricao()));
        tarefa.setPrazo(tarefaRequest.prazo());
        if(tarefaRequest.concluida() == null) {
            tarefa.setConcluida(false);
        } else {
            tarefa.setConcluida(tarefaRequest.concluida());
        }
        tarefa.setCategoria(tarefaRequest.categoria());
        return tarefa;
    }
}
