package com.example.amltest.service;
import com.example.amltest.dto.categoria.CategoriaResponse;
import com.example.amltest.enums.CategoriaEnum;
import com.example.amltest.mapper.CategoriaMapper;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoriaService {
    final private CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }

    public List<CategoriaResponse> listarCategoria() {
        return Arrays.stream(CategoriaEnum.values()).map(categoriaMapper::toCategoriaReponse).toList();
    }
}
