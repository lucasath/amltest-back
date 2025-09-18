package com.example.amltest.service;
import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.entity.Tarefa;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.mapper.TarefaMapper;
import com.example.amltest.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {
    final private TarefaRepository tarefaRepository;
    final private TarefaMapper tarefaMapper;

    public TarefaService(TarefaRepository tarefaRepository, TarefaMapper tarefaMapper) {
        this.tarefaRepository = tarefaRepository;
        this.tarefaMapper = tarefaMapper;
    }

    public TarefaResponse salvar(TarefaRequest tarefaRequest) {
        Tarefa tarefa = tarefaMapper.toEntity(tarefaRequest);
        return tarefaMapper.toResponse(tarefaRepository.save(tarefa));
    }

    public Page<TarefaResponse> listar(CategoriaEnum categoria, Pageable pageable) {
        Page<Tarefa> tarefaPage;

        if(categoria != null) {
            tarefaPage = tarefaRepository.findByCategoria(categoria, pageable);
        } else {
            tarefaPage = tarefaRepository.findAll(pageable);
        }

        return tarefaPage.map(tarefaMapper::toResponse);
    }

    public TarefaResponse concluir(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id do usuário não encontrato"));
        tarefa.setConcluida(true);

        return tarefaMapper.toResponse(tarefaRepository.save((tarefa)));
    }

    public void excluir(Long id) {
        if(!tarefaRepository.existsById((id))) {
            throw new EntityNotFoundException(("Id do usuário não encontrato"));
        }

        tarefaRepository.deleteById(id);
    }


}
