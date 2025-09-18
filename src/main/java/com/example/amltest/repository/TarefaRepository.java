package com.example.amltest.repository;

import com.example.amltest.entity.Tarefa;
import com.example.amltest.enums.CategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByCategoria(CategoriaEnum categoria, Pageable pageable);
}
