package dev.luismiguelro.movies.home;
import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("loginText", "Iniciar Sesión");
        model.addAttribute("loginHref", "/login");
        model.addAttribute("exploreText", "Explorar");
        model.addAttribute("registerText", "Registrarse");
        return "index"; // Esto buscará un archivo llamado "index.html" en la carpeta "templates"
    }

    @RequestMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        model.addAttribute("registerRequest", new RegisterRequest());
        return "form";
    }

    @ModelAttribute
    public AuthenticationResponse authenticationResponse() {
        return new AuthenticationResponse();
    }

    @RequestMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("authenticationResponse", new AuthenticationResponse());
        return "home";
    }
}
