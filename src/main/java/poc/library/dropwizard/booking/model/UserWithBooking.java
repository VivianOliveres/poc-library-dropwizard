package poc.library.dropwizard.booking.model;

import java.util.List;
import java.util.Optional;
import lombok.Value;
import poc.library.dropwizard.domain.Adherent;

@Value
public class UserWithBooking {

    Adherent adherent;
    Optional<Booking> booking;

    public List<Booking> getBookings() {
        return booking.map(List::of).orElseGet(List::of);
    }
}
