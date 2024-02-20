package dev.luismiguelro.movies.users.auth.service;

import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.config.JwtService;
import dev.luismiguelro.movies.users.repository.UserRepository;
import dev.luismiguelro.movies.users.user.User;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final JwtService jwtService = Mockito.mock(JwtService.class);
    private final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    private final AuthenticationService authenticationService = new AuthenticationService(
            userRepository,
            passwordEncoder,
            jwtService,
            authenticationManager
    );
    @Test
    public void testRegisterAndGenerateToken() throws Exception {
        // Crear un objeto RegisterRequest con datos de prueba
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        AuthenticationResponse responseEntity = authenticationService.register(registerRequest);

        // Verificar que la respuesta no sea nula
        assertNotNull(responseEntity);

        // Verificar que la respuesta tenga un cuerpo (token)
        assertNotNull(responseEntity.getToken());
    }

    @Test
    public void testRegisterEmailAlreadyExists() {
        // Configuración del mock para devolver un usuario existente al buscar por email
        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(new User()));

        // Crear un objeto RegisterRequest con datos de prueba
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        // Verificar que se lance la excepción EmailAlreadyInUseException
        assertThrows(Exception.class, () -> authenticationService.register(registerRequest));
    }
    @Test
    public void testAuthenticateSuccess() {
        // Datos de prueba
        String email = "john.doe@example.com";
        String password = "password123";

        // Configurar el mock del repositorio para devolver un usuario al buscar por email
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(buildUser(email, password)));

        // Configurar el mock del AuthenticationManager para aceptar cualquier autenticación
        when(authenticationManager.authenticate(any())).thenAnswer(invocation -> {
            return null; // o cualquier valor que desees devolver en caso de éxito
        });

        // Configurar el mock del JwtService para devolver un token
        when(jwtService.generateToken(any(User.class))).thenReturn("fakeJwtToken");

        // Crear una AuthenticationRequest con datos de prueba
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);

        // Llamar al método authenticate en el servicio
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        // Verificar que la respuesta no sea nula y que tenga un token
        assertNotNull(authenticationResponse);
        assertNotNull(authenticationResponse.getToken());
    }

    private User buildUser(String email, String password) {
        return User.builder()
                .firstname("John")
                .lastname("Doe")
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}