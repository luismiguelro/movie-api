package dev.luismiguelro.movies.home;
import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndex() {
        return "index"; // Esto buscará un archivo llamado "index.html" en la carpeta "templates"
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        model.addAttribute("registerRequest",new RegisterRequest());
        return "form";
    }
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("authenticationResponse", new AuthenticationResponse());
        return "home";
    }

}
