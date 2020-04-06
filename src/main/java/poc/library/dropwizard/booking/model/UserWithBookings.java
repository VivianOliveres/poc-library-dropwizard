package poc.library.dropwizard.booking.model;

import java.util.List;
import lombok.Value;
import poc.library.dropwizard.domain.Adherent;

@Value
public class UserWithBookings {

    Adherent adherent;
    List<Booking> bookings;
}
