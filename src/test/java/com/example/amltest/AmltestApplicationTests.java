package com.example.amltest;

import com.example.amltest.controller.CategoriaController;
import com.example.amltest.controller.TarefaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AmltestApplicationTests {
    @Autowired
    private TarefaController tarefaController;

    @Autowired
    private CategoriaController categoriaController;

	@Test
	void contextLoads() {
        assertThat(categoriaController).isNotNull();
        assertThat(tarefaController).isNotNull();
	}
}
