package dev.luismiguelro.movies.users.auth.controller;


import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.auth.exceptions.EmailAlreadyInUseException;
import dev.luismiguelro.movies.users.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ModelAttribute("registerRequest")
    public RegisterRequest registerRequest() {
        return new RegisterRequest();
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") @Validated RegisterRequest request, RedirectAttributes redirectAttributes, Model model) {


        try {
            ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.ok(service.register(request));
            AuthenticationResponse response = responseEntity.getBody();

            // Agregar el token al modelo o flash attributes
            model.addAttribute("token", response.getToken());
            // O puedes usar flash attributes
            redirectAttributes.addFlashAttribute("token", response.getToken());

            redirectAttributes.addFlashAttribute("exitoMessage", "Registro exitoso");
        } catch (EmailAlreadyInUseException e) {
            // Manejar la excepción de correo electrónico ya en uso si es necesario
            redirectAttributes.addFlashAttribute("errorMessage", "El correo electrónico ya está en uso");
        }

        return "redirect:/api/v1/auth/register?exito";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "login";
    }
    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute("authenticationRequest") @Validated AuthenticationRequest request, Model model) {
        try {
            AuthenticationResponse responseEntity = service.authenticate(request);
            String response = responseEntity.getToken();

            // Agregar el token al modelo o flash attributes
            model.addAttribute("token", response);
            System.out.println(response);
            return "home"; // Redirigir a la página de inicio después de la autenticación exitosa
        } catch (Exception e) {
            // Si la autenticación falla, agrega un mensaje de error y vuelve a la página de inicio de sesión
            System.out.println(e);
            model.addAttribute("error", "Error de autenticación. Verifica tus credenciales.");
            return "redirect:/api/v1/auth/login"; // Redirigir a la página de inicio de sesión con el mensaje de error
        }
    }
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest registerRequest) {
        return null;
    }
}
