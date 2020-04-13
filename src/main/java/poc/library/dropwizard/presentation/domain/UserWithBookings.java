package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;
import poc.library.dropwizard.core.domain.User;

import java.util.List;

@Value
public class UserWithBookings {

    @JsonProperty
    User user;

    @With
    @JsonProperty
    List<BookingWithBook> bookings;

    @JsonCreator
    public UserWithBookings(@JsonProperty("user") User user,
                            @JsonProperty("bookings") List<BookingWithBook> bookings) {
        this.user = user;
        this.bookings = bookings;
    }

    public UserWithBookings(User user) {
        this(user, List.of());
    }

}
