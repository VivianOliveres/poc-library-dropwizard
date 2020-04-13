package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;
import poc.library.dropwizard.core.domain.User;

import java.util.List;

@Value
public class UserWithRatings {

    @JsonProperty
    User user;

    @With
    @JsonProperty
    List<RatingWithBook> ratings;

    @JsonCreator
    public UserWithRatings(@JsonProperty("user") User user,
                           @JsonProperty("ratings") List<RatingWithBook> ratings) {
        this.user = user;
        this.ratings = ratings;
    }

    public UserWithRatings(User user) {
        this(user, List.of());
    }
}
