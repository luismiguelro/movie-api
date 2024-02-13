package dev.luismiguelro.movies.users.repository;

import dev.luismiguelro.movies.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    // find user by email
    Optional<User> findByEmail(String email);
}
