package com.example.amltest.mapper;

import com.example.amltest.dto.categoria.CategoriaResponse;
import com.example.amltest.enums.CategoriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaMapperTest {

    private CategoriaMapper categoriaMapper;

    @BeforeEach
    void setUp() {
        categoriaMapper = new CategoriaMapper();
    }

    @Test
    void deveConverterEnumParaResponse() {

        CategoriaEnum categoriaEnum = CategoriaEnum.ESTUDO;
        CategoriaResponse response = categoriaMapper.toCategoriaReponse(categoriaEnum);
        assertNotNull(response);
        assertEquals("ESTUDO", response.nome());
        assertEquals("Estudo", response.descricao());
    }

    @Test
    void deveConverterOutroEnumParaResponse() {
        CategoriaEnum categoriaEnum = CategoriaEnum.TRABALHO;
        CategoriaResponse response = categoriaMapper.toCategoriaReponse(categoriaEnum);
        assertEquals("TRABALHO", response.nome());
        assertEquals("Trabalho", response.descricao());
    }
}