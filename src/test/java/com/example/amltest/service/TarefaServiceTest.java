package com.example.amltest.service;
import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.entity.Tarefa;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.mapper.TarefaMapper;
import com.example.amltest.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @Mock
    private TarefaMapper mapper;

    @InjectMocks
    private TarefaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarTarefa() {
        String titulo = "Teste1";
        TarefaRequest request = new TarefaRequest(titulo,"Teste descrição 1",  Mockito.mock(java.time.LocalDate.class), false, CategoriaEnum.ESTUDO);

        Tarefa tarefaEntity = new Tarefa();
        tarefaEntity.setTitulo(request.getTitulo());

        TarefaResponse response = new TarefaResponse(1L,titulo,"Teste descrição 1",  Mockito.mock(java.time.LocalDate.class), false, CategoriaEnum.ESTUDO);

        when(mapper.toEntity(request)).thenReturn(tarefaEntity);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefaEntity);
        when(mapper.toResponse(tarefaEntity)).thenReturn(response);

        TarefaResponse resultado = service.salvar(request);

        assertNotNull(resultado);
        assertEquals(titulo, resultado.titulo());
        verify(repository, times(1)).save(tarefaEntity);
        verify(mapper, times(1)).toResponse(tarefaEntity);
    }

    @Test
    void deveListarTarefas() {
        String titulo = "Teste1";
        Pageable pageable = PageRequest.of(0, 10);
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(titulo);
        Page<Tarefa> page = new PageImpl<>(List.of(tarefa));

        when(repository.findAll(pageable)).thenReturn(page);
        when(repository.findByCategoria(CategoriaEnum.TRABALHO, pageable)).thenReturn(page);
        when(mapper.toResponse(any(Tarefa.class))).thenAnswer(invocation -> {
            Tarefa t = invocation.getArgument(0);
            return new TarefaResponse(
                    t.getId(),
                    t.getTitulo(),
                    t.getDescricao(),
                    t.getPrazo(),
                    t.getConcluida(),
                    t.getCategoria()
            );
        });

        Page<TarefaResponse> resultado = service.listar(CategoriaEnum.TRABALHO, pageable);
        Page<TarefaResponse> resultadoSemCategoria = service.listar(null, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals(titulo, resultado.getContent().getFirst().titulo());
        assertEquals(1, resultadoSemCategoria.getTotalElements());
        assertEquals(titulo, resultadoSemCategoria.getContent().getFirst().titulo());
    }


    @Test
    void deveConcluirTarefaExistente() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setConcluida(false);

        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(mapper.toResponse(any(Tarefa.class))).thenAnswer(invocation -> {
            Tarefa t = invocation.getArgument(0);
            return new TarefaResponse(
                    t.getId(),
                    t.getTitulo(),
                    t.getDescricao(),
                    t.getPrazo(),
                    t.getConcluida(),
                    t.getCategoria()
            );
        });

        TarefaResponse resultado = service.concluir(1L);

        verify(repository).save(tarefa);
        assertTrue(resultado.concluida());
    }

    @Test
    void deveLancarExceptionAoConcluirTarefaInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.concluir(1L));
    }

    @Test
    void deveExcluirTarefaExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        service.excluir(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExceptionAoExcluirTarefaInexistente() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.excluir(1L));
    }
}
