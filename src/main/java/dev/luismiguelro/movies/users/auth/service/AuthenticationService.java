package dev.luismiguelro.movies.users.auth.service;

import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.config.JwtService;
import dev.luismiguelro.movies.users.repository.UserRepository;
import dev.luismiguelro.movies.users.user.Role;
import dev.luismiguelro.movies.users.user.User;
import lombok.RequiredArgsConstructor;
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

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        validateEmailNotInUse(request.getEmail());

        User user = buildUserFromRequest(request);
        repository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return buildAuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request.getEmail(), request.getPassword());

        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return buildAuthenticationResponse(jwtToken);
    }

    private void validateEmailNotInUse(String email) throws Exception {
        if (repository.findByEmail(email).isPresent()) {
            throw new Exception("El correo electrónico ya está en uso");
        }
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
