package com.example.amltest.mapper;

import com.example.amltest.dto.categoria.CategoriaResponse;
import com.example.amltest.enums.CategoriaEnum;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponse toCategoriaReponse(CategoriaEnum categoria) {
        return new CategoriaResponse(categoria.name(), categoria.getDescricao());
    }
}
