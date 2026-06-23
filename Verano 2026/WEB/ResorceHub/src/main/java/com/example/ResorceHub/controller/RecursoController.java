package com.example.ResorceHub.controller;


import com.example.ResorceHub.model.Recurso;
import com.example.ResorceHub.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurso")
public class RecursoController {
    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public List<Recurso> listar() {
        return recursoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Recurso porId(@PathVariable long id) {
        return recursoService.obtenerPorId(id).orElse(null);
    }

    @PostMapping
    public Recurso crear(@RequestBody Recurso recurso) {
        return recursoService.crearRecurso(recurso);
    }

    @PutMapping("/{id}")
    public Recurso actualizar(@PathVariable Long id, @RequestBody Recurso recurso) {
        return recursoService.actualizarCompleto(id, recurso).orElse(null);
    }

    @PatchMapping("/{id}")
    public Recurso actualizarParcial(@PathVariable Long id, @RequestBody Recurso recurso) {
        return recursoService.actualizarParcial(id, recurso).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        recursoService.eliminar(id);
    }

}
