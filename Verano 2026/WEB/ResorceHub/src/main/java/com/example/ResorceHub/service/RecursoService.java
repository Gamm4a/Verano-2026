package com.example.ResorceHub.service;

import com.example.ResorceHub.model.Recurso;
import com.example.ResorceHub.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    public List<Recurso> obtenerTodos(){
        return recursoRepository.findAll();
    }

    public Optional<Recurso> obtenerPorId(Long id){
        return recursoRepository.findById(id);

    }

    public Recurso crearRecurso(Recurso recurso){
        return recursoRepository.save(recurso);
    }

    public Optional<Recurso> actualizarCompleto(Long id, Recurso recursoActualizado){
        return recursoRepository.findById(id).map(recursoExistente -> {
            recursoExistente.setTitulo(recursoActualizado.getTitulo());
            recursoExistente.setUrl(recursoActualizado.getUrl());
            recursoExistente.setTipo(recursoActualizado.getTipo());
            recursoExistente.setCategoria(recursoActualizado.getCategoria());
            return recursoRepository.save(recursoExistente);
        });
    }

    public Optional<Recurso> actualizarParcial(Long id, Recurso recursoActualizado){
        return recursoRepository.findById(id).map(recursoExistente -> {
            if (recursoActualizado.getTitulo() != null) recursoExistente.setTitulo(recursoActualizado.getTitulo());
            if (recursoActualizado.getUrl()!=null)recursoExistente.setUrl(recursoActualizado.getUrl());
            if (recursoActualizado.getTipo()!=null)recursoExistente.setTipo(recursoActualizado.getTipo());
            if (recursoActualizado.getCategoria()!=null)recursoExistente.setCategoria(recursoActualizado.getCategoria());
            return recursoRepository.save(recursoExistente);
        });
    }

    public boolean eliminar(Long id){
        return recursoRepository.deleteById(id);
    }


}
