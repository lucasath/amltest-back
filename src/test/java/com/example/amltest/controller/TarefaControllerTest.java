package com.example.amltest.controller;
import com.example.amltest.dto.error.ErrorResponse;
import com.example.amltest.dto.tarefa.TarefaRequest;
import com.example.amltest.dto.tarefa.TarefaResponse;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    TarefaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void adicionarTarefaTest() throws Exception {
        LocalDate date = LocalDate.of(2025, 12, 31);
        TarefaRequest request = new TarefaRequest("teste1","Task de teste numero 3", date, false, CategoriaEnum.ESTUDO);
        TarefaResponse response = new TarefaResponse(1L,"teste1","Task de teste numero 3",  date, false, CategoriaEnum.ESTUDO);

        Mockito.when(service.salvar(Mockito.any(TarefaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void listarTarefaSemFiltro() throws Exception {
        LocalDate date = LocalDate.of(2025, 12, 31);
        List<TarefaResponse> list = List.of(new TarefaResponse(1L,"teste1","Task de teste numero 3",  date, false, CategoriaEnum.ESTUDO));
        Page<TarefaResponse> response = new PageImpl<TarefaResponse>(list, PageRequest.of(0, 10),
                1);

        Mockito.when(service.listar(Mockito.any(),
                Mockito.any())).thenReturn(response);

        mockMvc.perform(get("/api/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void listarTarefaComFiltroCategoria() throws Exception {
        LocalDate date = LocalDate.of(2025, 12, 31);
        List<TarefaResponse> list = List.of(new TarefaResponse(1L,"teste1","Task de teste numero 3",  date, false, CategoriaEnum.ESTUDO));
        Page<TarefaResponse> response = new PageImpl<TarefaResponse>(list, PageRequest.of(0, 10),
                1);

        Mockito.when(service.listar(Mockito.any(),
                Mockito.any())).thenReturn(response);

        mockMvc.perform(get("/api/tasks").param("categoria", String.valueOf(CategoriaEnum.ESTUDO)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void listarTarefaComFiltroCategoriaNaoExiste() throws Exception {
        LocalDate date = LocalDate.of(2025, 12, 31);
        List<TarefaResponse> list = List.of();
        Page<TarefaResponse> response = new PageImpl<TarefaResponse>(list, PageRequest.of(0, 10),
                1);

        Mockito.when(service.listar(Mockito.any(),
                Mockito.any())).thenReturn(response);

        mockMvc.perform(get("/api/tasks").param("categoria", String.valueOf(CategoriaEnum.TRABALHO)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void listarTarefaComFiltroCategoriaEPaginacao() throws Exception {
        LocalDate date = LocalDate.of(2025, 12, 31);
        List<TarefaResponse> list = List.of();
        Page<TarefaResponse> response = new PageImpl<TarefaResponse>(list, PageRequest.of(0, 10),
                1);

        Mockito.when(service.listar(Mockito.any(),
                Mockito.any())).thenReturn(response);

        mockMvc.perform(get("/api/tasks").param("page", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void deveConcluirTarefa() throws Exception {
        TarefaResponse response = new TarefaResponse(1L,"teste1","Task de teste numero 3",  LocalDate.of(2025, 12 ,12), false, CategoriaEnum.ESTUDO);
        Mockito.when(service.concluir(1L)).thenReturn(response);
        mockMvc.perform(put("/api/tasks/1/complete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveConcluirRetornar404QuandoTarefaNaoExiste() throws Exception {
        TarefaResponse response = new TarefaResponse(1L,"teste1","Task de teste numero 3",  LocalDate.of(2025, 12 ,12), false, CategoriaEnum.ESTUDO);
        ErrorResponse errorResponse = new ErrorResponse(404, "Tarefa não encontrada");
        Mockito.when(service.concluir(1L)).thenReturn(response);
        Mockito.when(service.concluir(2L))
                .thenThrow(new EntityNotFoundException("Tarefa não encontrada"));

        mockMvc.perform(put("/api/tasks/2/complete").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));
    }


    @Test
    void deveExcluirTarefa() throws Exception {
        TarefaResponse response = new TarefaResponse(1L,"teste1","Task de teste numero 3",  LocalDate.of(2025, 12 ,12), false, CategoriaEnum.ESTUDO);

        Mockito.doNothing().when(service).excluir(1L);
        mockMvc.perform(delete("/api/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveExcluirRetornar404QuandoTarefaNaoExiste() throws Exception {
        TarefaResponse response = new TarefaResponse(1L,"teste1","Task de teste numero 3",  LocalDate.of(2025, 12 ,12), false, CategoriaEnum.ESTUDO);
        ErrorResponse errorResponse = new ErrorResponse(404, "Tarefa não encontrada");
        Mockito.doThrow(new EntityNotFoundException("Tarefa não encontrada")).when(service).excluir(2L);

        mockMvc.perform(delete("/api/tasks/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));
    }
}