package dev.luismiguelro.movies.users.auth.service;

import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.auth.exceptions.EmailAlreadyInUseException;
import dev.luismiguelro.movies.users.config.JwtService;
import dev.luismiguelro.movies.users.user.repository.UserRepository;
import dev.luismiguelro.movies.users.user.Role;
import dev.luismiguelro.movies.users.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws EmailAlreadyInUseException {
        if (isEmailNotInUse(request.getEmail())) {
            var user = buildUserFromRequest(request);
            repository.save(user);

            String jwtToken = jwtService.generateToken(user);

            return buildAuthenticationResponse(jwtToken);
        } else {
            // Manejar el caso cuando el correo electrónico ya está en uso
            throw new EmailAlreadyInUseException("El correo electrónico ya está en uso");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        try {
            authenticateUser(request.getEmail(), request.getPassword());
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String jwtToken = jwtService.generateToken(user);
            return buildAuthenticationResponse(jwtToken);
        } catch (Exception e) {
            throw new AuthenticationException("Error al autenticar: "+e);
        }
    }

    boolean isEmailNotInUse(String email) {
        // Implementation to check if the email is not in use
        return repository.findByEmail(email).isEmpty();
    }

    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    private AuthenticationResponse buildAuthenticationResponse(String jwtToken) {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
