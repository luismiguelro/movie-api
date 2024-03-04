package dev.luismiguelro.movies.users.auth;

import dev.luismiguelro.movies.users.auth.controller.AuthenticationController;
import dev.luismiguelro.movies.users.auth.service.AuthenticationService;
import dev.luismiguelro.movies.users.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authService;
    @Mock
    private UserRepository repository;


    @InjectMocks
    private AuthenticationController authController;
    @Test
    public void testRegister() throws Exception {
        // Mock the repository behavior
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<AuthenticationResponse> responseEntity = authController.register(new RegisterRequest());

        // Assert the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(Objects.requireNonNull(responseEntity.getBody()).getToken());
    }


}