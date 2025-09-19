package com.example.amltest.controller;

import com.example.amltest.dto.categoria.CategoriaResponse;
import com.example.amltest.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categories")
    public List<CategoriaResponse> listarCategorias() {
        return categoriaService.listarCategoria();
    }
}
