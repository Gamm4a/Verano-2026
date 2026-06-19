package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    // Select j FROM juego WHERE genero LIKE "%filtro%"
    //List<Juego> findbyGenero(String genero);


    //List<Juego> findbyPriceLessThan(double precio);

    //@Query("SELECT j FROM Juego WHERE lower(j.descripcion) LIKE lower(concat('%',:filtro,'%') ) ")
    //List<Juego> buscarPorDescripcion(@Param("filtro") String filtro);



    List<Juego> findByUsuarioId(Long usuario);
}
