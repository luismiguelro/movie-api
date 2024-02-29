package dev.luismiguelro.movies.reviews.service;

import dev.luismiguelro.movies.movie.Movie;
import dev.luismiguelro.movies.reviews.Review;
import dev.luismiguelro.movies.reviews.repository.ReviewRepository;
import dev.luismiguelro.movies.users.config.JwtService;
import dev.luismiguelro.movies.users.user.User;
import dev.luismiguelro.movies.users.user.repository.UserRepository;
import dev.luismiguelro.movies.users.user.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    public Review createReview(String reviewBody, String imdbId, Authentication authentication) {
        // Obtener el ID del usuario autenticado
        ObjectId userId = new ObjectId(((User) authentication.getPrincipal()).getId());

        // Crear la revisión con el ID del usuario
        Review review = new Review();
        review.setBody(reviewBody);
        review.setCreatedBy(userId);

        // Guardar la revisión en la base de datos
        Review insertReview = reviewRepository.insert(review);

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        mongoTemplate.update(User.class)
                .matching(Criteria.where("id").is(userId))
                .apply(new Update().push("reviews").value(review))
                .first();
        return insertReview;
    }
}
