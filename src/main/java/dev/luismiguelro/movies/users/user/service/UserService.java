package dev.luismiguelro.movies.users.user.service;

import dev.luismiguelro.movies.users.user.User;
import dev.luismiguelro.movies.users.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        User currentUser = (User) authentication.getPrincipal();

        return currentUser.getFirstname();
    }
}