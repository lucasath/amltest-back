package com.example.amltest.service;
import com.example.amltest.dto.categoria.CategoriaResponse;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.mapper.CategoriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {
    @Mock
    private CategoriaMapper mapper;

    @Autowired
    @InjectMocks
    private CategoriaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodasASCategorias() {
        for (CategoriaEnum categoriaEnum : CategoriaEnum.values()) {
            when(mapper.toCategoriaReponse(categoriaEnum))
                    .thenReturn(new CategoriaResponse(categoriaEnum.name(), categoriaEnum.getDescricao()));
        }

        List<CategoriaResponse> resultado = service.listarCategoria();

        assertEquals(CategoriaEnum.values().length, resultado.size());

        for (int i = 0; i < CategoriaEnum.values().length; i++) {
            assertEquals(CategoriaEnum.values()[i].name(), resultado.get(i).nome());
        }

        for (CategoriaEnum categoriaEnum : CategoriaEnum.values()) {
            verify(mapper, times(1)).toCategoriaReponse(categoriaEnum);
        }

    }
}