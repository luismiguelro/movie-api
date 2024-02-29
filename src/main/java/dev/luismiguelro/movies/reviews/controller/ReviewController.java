package dev.luismiguelro.movies.reviews.controller;

import dev.luismiguelro.movies.reviews.Review;
import dev.luismiguelro.movies.reviews.service.ReviewService;
import dev.luismiguelro.movies.users.config.JwtAuthenticationFilter;
import dev.luismiguelro.movies.users.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, String> payload) {
        // Validar que reviewBody y imdbId estén presentes en el payload
        if (!payload.containsKey("reviewBody") || !payload.containsKey("imdbId")) {
            return new ResponseEntity<>("Se requieren 'reviewBody' e 'imdbId' en el cuerpo de la solicitud.", HttpStatus.BAD_REQUEST);
        }

        // Validar que reviewBody y imdbId no estén vacíos
        String reviewBody = payload.get("reviewBody");
        String imdbId = payload.get("imdbId");
        if (reviewBody.isEmpty() || imdbId.isEmpty()) {
            return new ResponseEntity<>("'reviewBody' e 'imdbId' no pueden estar vacíos.", HttpStatus.BAD_REQUEST);
        }

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Pasar el usuario al servicio para crear la revisión
        return new ResponseEntity<>(reviewService.createReview(reviewBody, imdbId, authentication), HttpStatus.CREATED);
    }
}
