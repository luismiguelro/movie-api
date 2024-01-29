package dev.luismiguelro.movies.movie;

import dev.luismiguelro.movies.reviews.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "movies") // represents movies collection
@Data // gets and setters
@AllArgsConstructor // constructor for the fields
@NoArgsConstructor
public class Movie {
    @Id
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String movieLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;
    @DocumentReference // reference
    private List<Review> reviewIds;

}
