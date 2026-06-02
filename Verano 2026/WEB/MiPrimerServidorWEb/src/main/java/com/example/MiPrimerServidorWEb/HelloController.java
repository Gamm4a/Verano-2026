package com.example.MiPrimerServidorWEb;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hola desde mi controlador Spring Boot";
    }

    @GetMapping("/saludo")
    public String saludo(@RequestParam String nombre){
        return "hola, "+ nombre +" este es mi servidor de aplicacion";

    }

    @PostMapping("/mensaje")
    public String recibirMensaje(@RequestBody String mensaje){
        return "Recibir tu mensaje "+ mensaje;
     }

}
