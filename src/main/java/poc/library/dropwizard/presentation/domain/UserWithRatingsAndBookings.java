package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;
import poc.library.dropwizard.core.domain.User;

import java.util.List;

@Value
public class UserWithRatingsAndBookings {

    @JsonProperty
    User user;

    @With
    @JsonProperty
    List<RatingWithBook> ratings;

    @With
    @JsonProperty
    List<BookingWithBook> bookings;

    @JsonCreator
    public UserWithRatingsAndBookings(@JsonProperty("user") User user,
                                      @JsonProperty("ratings") List<RatingWithBook> ratings,
                                      @JsonProperty("bookings") List<BookingWithBook> bookings) {
        this.user = user;
        this.ratings = ratings;
        this.bookings = bookings;
    }

    public UserWithRatingsAndBookings(User user) {
        this(user, List.of(), List.of());
    }

    public UserWithRatingsAndBookings(User user, List<RatingWithBook> ratings) {
        this(user, ratings, List.of());
    }

}
