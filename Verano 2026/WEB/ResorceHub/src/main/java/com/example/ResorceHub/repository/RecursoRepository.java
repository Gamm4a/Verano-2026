package com.example.ResorceHub.repository;

import com.example.ResorceHub.model.Recurso;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository // Indica que esta clase es un componente de persistencia de Spring
public class RecursoRepository {

    // Almacenamiento puramente en memoria
    private final List<Recurso> recursos = new ArrayList<>();
    private Long contadorId = 1L;

    public List<Recurso> findAll() {
        return recursos;
    }

    public Optional<Recurso> findById(Long id) {
        return recursos.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public Recurso save(Recurso recurso) {
        if (recurso.getId() == null) {
            // Es una creación (POST)
            recurso.setId(contadorId++);
            recursos.add(recurso);
        } else {
            // Es una actualización (PUT/PATCH), buscamos el índice y reemplazamos
            int index = -1;
            for (int i = 0; i < recursos.size(); i++) {
                if (recursos.get(i).getId().equals(recurso.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                recursos.set(index, recurso);
            }
        }
        return recurso;
    }

    public boolean deleteById(Long id) {
        return recursos.removeIf(r -> r.getId().equals(id));
    }
}
