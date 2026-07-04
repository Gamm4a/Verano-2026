package com.example.MiPrimerServidorWEb;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {


    //metodo get sin parametro
    @GetMapping("/hello")
    public String hello(){
        return "hola desde mi controlador Spring Boot";
    }

    //metodo get
    @GetMapping("/saludo")
    public String saludo(@RequestParam String nombre){
        return "hola, "+ nombre +" este es mi servidor de aplicacion";

    }
    //metodo post
    @PostMapping("/mensaje")
    public String recibirMensaje(@RequestBody String mensaje){
        return "Recibir tu mensaje "+ mensaje;
     }

}
