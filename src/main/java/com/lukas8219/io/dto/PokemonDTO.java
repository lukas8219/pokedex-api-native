package com.lukas8219.io.dto;

public class PokemonDTO {

    private final Integer id;
    private final String name;

    public PokemonDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
