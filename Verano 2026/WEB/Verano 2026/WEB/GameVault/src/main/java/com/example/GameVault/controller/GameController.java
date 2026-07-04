package com.example.GameVault.controller;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import com.example.GameVault.service.JuegoService;
import com.example.GameVault.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {
    @Autowired
    private JuegoService juegoService;
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/fragments-demo")
    public String fragmentsDemo() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String listarJuegos(@RequestParam(name = "buscar", required = false) String buscar, Model model) {

        if (buscar == null) {
            buscar = "";
        }

        model.addAttribute("juegos", juegoService.buscarPorTitulo(buscar));
        model.addAttribute("busquedaActual", buscar);
        return "juegos";
    }

    @GetMapping("/mis-juegos")
    public String dashboard(Model model) {
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();
        model.addAttribute("juegos", juegoService.obtenerJuegoPorId(logueado.getId()));
        model.addAttribute("usuario", logueado);
        return "mis-juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("juego", new Juego());
        return "formulario";

    }

/*
    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario() {
        return "formulario";
    }
*/
    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion, @RequestParam("portada") MultipartFile portada, @RequestParam("categoria") String categoria, @RequestParam("precio") Double precio) {
        Juego nuevoJuego = new Juego();
        nuevoJuego.setUsuario(usuarioService.obtenerUsuarioLogueado());
        System.out.println(nuevoJuego.getUsuario().toString());
        nuevoJuego.setTitulo(titulo);
        nuevoJuego.setDescripcion(descripcion);
        nuevoJuego.setPrecio(precio);
        nuevoJuego.setCategoria(categoria);
        System.out.println(nuevoJuego.toString());

        juegoService.guardarJuego(nuevoJuego, portada);
        return "redirect:/juegos";
    }

    @GetMapping("/juegos/editar/{id}")
    public String editarJuego(@PathVariable long id, Model model) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();


        if (juego != null && juego.getUsuario().getId().equals(logueado.getId())) {
            model.addAttribute("juego");
            return "formulario";
        }

        return "redirect:mis-juegos";
    }

    @PostMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id, @RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion, @RequestParam("portada") MultipartFile portada, @RequestParam("categoria") String categoria, @RequestParam("precio") Double precio) {


        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();

        if (juego != null && juego.getUsuario().getId().equals(logueado.getId())) {
            juego.setTitulo(titulo);
            juego.setDescripcion(descripcion);
            juego.setPrecio(precio);
            juego.setCategoria(categoria);
            juegoService.guardarJuego(juego, portada);

            return "redirect:/mis-juegos";
        }


        return "redirect:/juegos";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String post(@PathVariable Long id) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        Usuario logueado = usuarioService.obtenerUsuarioLogueado();

        if (juego != null && juego.getUsuario().getId().equals(logueado.getId())) {

            juegoService.eliminarJuego(id);

        }
        return "redirect:/mis-juegos";


    }


}
