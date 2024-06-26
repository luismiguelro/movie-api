package dev.luismiguelro.movies.reviews;

import dev.luismiguelro.movies.users.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;
    private String createdBy;
    public Review(String body, String createdBy) {
        this.body = body;
        this.createdBy = createdBy;
    }

}
