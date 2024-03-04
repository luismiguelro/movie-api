package dev.luismiguelro.movies.home;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Esto buscará un archivo llamado "home.html" en la carpeta "templates"
    }

}
