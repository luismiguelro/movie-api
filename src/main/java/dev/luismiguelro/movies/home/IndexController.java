package dev.luismiguelro.movies.home;
import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home() {
        return "index"; // Esto buscará un archivo llamado "index.html" en la carpeta "templates"
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        model.addAttribute("registerRequest",new RegisterRequest());
        return "form";
    }


    @ModelAttribute("authenticationRequest")
    public AuthenticationRequest authenticationRequest() {
        return new AuthenticationRequest();
    }
    @ModelAttribute("registerRequest")
    public RegisterRequest registerRequest() {
        return new RegisterRequest();
    }
}
