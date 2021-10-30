CREATE TABLE pokedex.pokemons (
                          id int PRIMARY KEY,
                          weight int,
                          height int,
                          name VARCHAR(50),
                          health_points int,
                          attack int,
                          defense int,
                          special_attack int,
                          special_defense int,
                          speed int,
                          front_sprite TEXT,
                          back_sprite TEXT
);

CREATE TABLE pokedex.evolutions (
    pokemon_id int,
    evolves_to int,
    FOREIGN KEY (pokemon_id) REFERENCES pokemons(id),
    FOREIGN KEY (evolves_to) REFERENCES pokemons(id),
    PRIMARY KEY (pokemon_id, evolves_to)
);

CREATE TABLE pokedex.pokemon_types (
    pokemon_id int,
    type VARCHAR(50),
    FOREIGN KEY (pokemon_id) REFERENCES pokemons(id),
    PRIMARY KEY(pokemon_id, type)
);