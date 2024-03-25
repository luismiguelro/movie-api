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

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final JwtService jwtService;
    private final UserService userService;

    public Review createReview(String reviewBody, String imdbId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String fullName = user.getFirstname() + " " + user.getLastname();

        Review review = new Review();
        review.setBody(reviewBody);
        review.setCreatedBy(fullName);

        Review insertReview = reviewRepository.insert(review);

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        mongoTemplate.update(User.class)
                .matching(Criteria.where("id").is(user.getId()))
                .apply(new Update().push("reviews").value(review))
                .first();
        return insertReview;
    }
}
