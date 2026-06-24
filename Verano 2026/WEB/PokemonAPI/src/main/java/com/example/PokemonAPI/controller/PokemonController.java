package com.example.PokemonAPI.controller;

import com.example.PokemonAPI.model.Pokemon;
import com.example.PokemonAPI.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pokemones")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;


    @GetMapping()
    public List<Pokemon> obtenerTodos() {
        return pokemonService.obtenerTodos();
    }

    @GetMapping("{id}")
    public Pokemon obtenerPorId(@PathVariable Long id) {
        return pokemonService.obtenerPorId(id);

    }

    @PostMapping
    public Pokemon crearPokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.crearPokemon(pokemon);
    }

    @PutMapping("/actualizar/{id}")
    public Pokemon actualizar(@PathVariable Long id, @RequestBody Pokemon pokemon) {
        return pokemonService.actualizarPokemonCompleto(pokemon.getId(), pokemon);
    }

    @PatchMapping("/actualizarParcial/{id}")
    public Pokemon actualizarParcial(@PathVariable Long id,@RequestBody Pokemon pokemon) {
        return pokemonService.actualizarPokemonParcial(pokemon.getId(), pokemon);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        pokemonService.eliminarPokemon(id);
    }

}
