package dev.luismiguelro.movies.users.auth.service;

import dev.luismiguelro.movies.users.auth.AuthenticationController;
import dev.luismiguelro.movies.users.auth.AuthenticationRequest;
import dev.luismiguelro.movies.users.auth.AuthenticationResponse;
import dev.luismiguelro.movies.users.auth.RegisterRequest;
import dev.luismiguelro.movies.users.auth.exceptions.EmailAlreadyInUseException;
import dev.luismiguelro.movies.users.config.JwtService;
import dev.luismiguelro.movies.users.repository.UserRepository;
import dev.luismiguelro.movies.users.user.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @MockBean
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Autowired
    private AuthenticationController authenticationController1;
    @Mock
    private AuthenticationRequest authenticationRequest;

    public void AuthenticationControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterAndGenerateToken() throws Exception {
        // Crear un objeto RegisterRequest con datos de prueba
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password123");

        // Mockear el comportamiento de AuthenticationService para que devuelva una respuesta simulada
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockedAuthenticationResponse().getBody());

        // Act
        AuthenticationResponse responseEntity = authenticationController.register(registerRequest).getBody();

        // Verificar que la respuesta no sea nula
        assertNotNull(responseEntity);

        // Verificar que la respuesta tenga un cuerpo (token)
        assertNotNull(responseEntity.getToken());
    }

    @Test
    public void testEmailAlreadyExists() throws EmailAlreadyInUseException{
        // Crear un objeto RegisterRequest con datos de prueba
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setPassword("password123");

        // Mockear el comportamiento de findByEmail para simular que el correo electrónico ya está en uso
        when(repository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(new User()));

        // Act y Assert
        assertThrows(EmailAlreadyInUseException.class, () -> {
            authenticationController1.register(registerRequest);
        });
    }


    @Test
    public void testAuthenticateSuccessfully() {
        authenticationRequest.setEmail("john.doe@example.com");
        authenticationRequest.setPassword("password123");

        // Mockear el comportamiento de AuthenticationService para que devuelva una respuesta simulad
        doReturn(ResponseEntity.ok(mockedAuthenticationResponse().getBody()))
                .when(authenticationService)
                .authenticate(any(AuthenticationRequest.class));
        // Act
        ResponseEntity<?> responseEntity = authenticationController.authenticate(authenticationRequest);

        // Verificar que la respuesta no sea nula
        assertNotNull(responseEntity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar que el cuerpo no sea nulo
        assertNotNull(responseEntity.getBody());


        // Verificar que el método authenticate de AuthenticationService fue invocado con los argumentos correctos
        verify(authenticationService, times(1)).authenticate(eq(authenticationRequest));
    }

    @Test
    public void testAuthenticateFailure() {
        // Create an AuthenticationRequest object with incorrect test data
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("usuarioIncorrecto@example.com");
        authenticationRequest.setPassword("claveIncorrecta");

        // Mock the behavior of AuthenticationService to simulate a failed authentication
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        // Act
        ResponseEntity<?> responseEntity = authenticationController.authenticate(authenticationRequest);

        // Verify that the status code is as expected for authentication failure
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCodeValue());
    }


    /* Metodos de utilidad*/

    // Método de utilidad para simular una respuesta de AuthenticationService para autenticación exitosa
    private ResponseEntity<AuthenticationResponse> mockedAuthenticationResponse() {
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("mytoken")
                .build();
        return ResponseEntity.ok(authenticationResponse);
    }
}