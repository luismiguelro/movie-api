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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Intenta registrar al usuario
            AuthenticationResponse response = service.register(request);
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyInUseException e) {
            // Maneja el caso cuando el correo electrónico ya está en uso
            return ResponseEntity.status(HttpStatus.CONFLICT).body("e-mail is in use");
        } catch (Exception e) {
            // Maneja otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request");
        }
 }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            // Assuming service.authenticate(request) returns an AuthenticationResponse
            AuthenticationResponse authenticationResponse = service.authenticate(request);
            return ResponseEntity.ok(authenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}