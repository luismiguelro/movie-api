package dev.luismiguelro.movies.users.auth.controller;


import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.auth.exceptions.EmailAlreadyInUseException;
import dev.luismiguelro.movies.users.auth.service.AuthenticationService;
import dev.luismiguelro.movies.users.user.User;
import dev.luismiguelro.movies.users.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws EmailAlreadyInUseException {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute("authenticationRequest") @Validated AuthenticationRequest request, RedirectAttributes redirectAttributes) {
        try {
            AuthenticationResponse responseEntity = service.authenticate(request);
            String response = responseEntity.getToken();
            redirectAttributes.addFlashAttribute("token", response);
            redirectAttributes.addFlashAttribute("text", "Cerrar Sesion");
            redirectAttributes.addFlashAttribute("href", "/logout");

            return "redirect:/home";
        } catch (Exception e) {
            // Si la autenticación falla, agrega un mensaje de error y vuelve a la página de inicio de sesión
            System.out.println(e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error de autenticación. Verifica tus credenciales.");
            return "redirect:/login?error"; // Redirigir a la página de inicio de sesión con el mensaje de error
        }
    }
}
