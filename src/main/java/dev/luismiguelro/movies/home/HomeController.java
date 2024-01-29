package dev.luismiguelro.movies.home;
import dev.luismiguelro.movies.movie.Movie;
import dev.luismiguelro.movies.movie.controller.MovieController;
import dev.luismiguelro.movies.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Puedes agregar datos adicionales al modelo según tus necesidades
        model.addAttribute("pageTitle", "Movie API");
        model.addAttribute("apiDescription", "Esta es una API de películas desarrollada con Spring Boot y desplegada en [fly.io](https://fly.io). La API permite consumir información sobre películas y añadir reseñas. Además, puedes obtener el tráiler de una película en particular.");
        return "home"; // Esto buscará un archivo llamado "home.html" en la carpeta "templates"
    }
}
