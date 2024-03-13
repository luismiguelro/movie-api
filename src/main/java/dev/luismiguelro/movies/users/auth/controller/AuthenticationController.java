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
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") @Validated RegisterRequest request, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.ok(service.register(request));
            AuthenticationResponse response = responseEntity.getBody();

            assert response != null;
            redirectAttributes.addFlashAttribute("token", response.getToken());

            redirectAttributes.addFlashAttribute("exitoMessage", "Registro exitoso. Ahora inicia sesión!");
            return "redirect:/login?exito";

        } catch (EmailAlreadyInUseException e) {
            // Manejar la excepción de correo electrónico ya en uso si es necesario
            redirectAttributes.addFlashAttribute("errorMessage", "El correo electrónico ya está en uso");
        }

        return "redirect:/login?error";
    }

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("authenticationResponse", new AuthenticationResponse());
        return "home";
    }
    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute("authenticationRequest") @Validated AuthenticationRequest request, RedirectAttributes redirectAttributes) {
        try {
            AuthenticationResponse responseEntity = service.authenticate(request);
            String response = responseEntity.getToken();
            /*
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            ObjectId userId = new ObjectId(((User) authentication.getPrincipal()).getId());

            System.out.println(userId)*/
            redirectAttributes.addFlashAttribute("token", response);
            return "redirect:/api/v1/auth/home"; // Redirigir a la página de inicio después de la autenticación exitosa
        } catch (Exception e) {
            // Si la autenticación falla, agrega un mensaje de error y vuelve a la página de inicio de sesión
            System.out.println(e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error de autenticación. Verifica tus credenciales.");
            return "redirect:/login?error"; // Redirigir a la página de inicio de sesión con el mensaje de error
        }
    }
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest registerRequest) {
        return null;
    }
}
