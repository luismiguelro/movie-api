package dev.luismiguelro.movies.home;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Puedes agregar datos adicionales al modelo según tus necesidades
        model.addAttribute("pageTitle", "Bienvenido a la Página de Inicio");
        model.addAttribute("apiDescription", "Esta es una API de películas desarrollada con Spring Boot y desplegada en [fly.io](https://fly.io). La API permite consumir información sobre películas y añadir reseñas. Además, puedes obtener el tráiler de una película en particular.");
        return "home"; // Esto buscará un archivo llamado "home.html" en la carpeta "public"
    }
}
