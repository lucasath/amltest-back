package com.example.amltest.enums;

import lombok.Getter;

@Getter
public enum CategoriaEnum {
    PESSOAL("Pessoal"),
    TRABALHO("Trabalho"),
    ESTUDO("Estudo");

    private final String descricao;

    CategoriaEnum(String descricao) {
        this.descricao = descricao;
    }

}