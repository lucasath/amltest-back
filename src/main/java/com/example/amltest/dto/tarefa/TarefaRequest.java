package com.example.amltest.dto.tarefa;
import com.example.amltest.enums.CategoriaEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;


@Getter
@Setter
public class TarefaRequest{
    @NotBlank(message = "Titulo é obrigatório")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 25, message = "Descrição é de no minimo 25 caracteres")
    private String descricao;

    @NotBlank(message = "O campo prazo é obrigatório")
    @FutureOrPresent(message = "O prazo deve ser hoje ou futuro")
    private LocalDate prazo;

    private Boolean concluida;

    private CategoriaEnum categoria;

    public TarefaRequest(String titulo, String descricao, LocalDate prazo, Boolean concluida, CategoriaEnum categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.concluida = concluida;
        this.categoria = categoria;
    }


}