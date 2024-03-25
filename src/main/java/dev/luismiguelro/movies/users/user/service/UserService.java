package dev.luismiguelro.movies.users.user.service;

import dev.luismiguelro.movies.users.user.User;
import dev.luismiguelro.movies.users.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }
    public String authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null; // Or handle unauthenticated case as needed
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername(); // Assuming username is what you want to retrieve
        } else {
            username = principal.toString(); // Fallback to principal's toString method
        }

        return username;
    }
}