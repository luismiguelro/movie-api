package dev.luismiguelro.movies.users.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
}
