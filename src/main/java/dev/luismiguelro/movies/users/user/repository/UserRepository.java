package dev.luismiguelro.movies.users.user.repository;

import dev.luismiguelro.movies.users.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    // find user by email
    Optional<User> findByEmail(String email);
}
