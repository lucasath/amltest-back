package com.example.amltest.controller;
import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TarefaController {

    @Autowired
    TarefaService tarefaService;

    @Operation(summary = "Criar uma task")
    @PostMapping
    public TarefaResponse adicionarTarefa(@RequestBody TarefaRequest tarefaRequest) {
        return tarefaService.salvar((tarefaRequest));
    }

    @Operation(summary = "Listar tasks parinadas")
    @GetMapping
    public Page<TarefaResponse> listarTarefa(@PathParam("categoria") CategoriaEnum categoria, @PageableDefault Pageable pageable) {
        return tarefaService.listar(categoria, pageable);
    }

    @Operation(summary = "Concluir uma task")
    @PutMapping("/{id}/complete")
    public TarefaResponse concluirTarefa(@PathVariable("id") Long id) {
        return tarefaService.concluir(id);
    }

    @Operation(summary = "Excluir uma task")
    @DeleteMapping("/{id}")
    public void excluirTarefa(@PathVariable("id") Long id) {
        tarefaService.excluir(id);
    }
}
