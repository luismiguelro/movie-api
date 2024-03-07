package dev.luismiguelro.movies.home;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home() {
        return "index"; // Esto buscará un archivo llamado "index.html" en la carpeta "templates"
    }

}
