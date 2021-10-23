package com.lukas8219.io.dao;

import com.lukas8219.io.dto.PokemonDTO;
import com.lukas8219.io.managers.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class PokedexDAO {

    private static final Logger log = LoggerFactory.getLogger(PokedexDAO.class);

    public static PokemonDTO getById(int id){
        var connection = ConnectionManager.getConnection();
        try {
            var statement = connection.createStatement();
            var query = String.format("SELECT * FROM pokemons WHERE id = %d", id);
            log.debug("Executing query {}", query);
            var resultSet = statement.executeQuery(query);
            resultSet.next();
            var name = resultSet.getString("name");
            var pokemonId = resultSet.getInt("id");
            return new PokemonDTO(pokemonId, name);
        } catch (SQLException e) {
            log.error("An error occurred when trying to execute the query", e);
            throw new RuntimeException("An exception occurred when executing query");
        }
    }

}
