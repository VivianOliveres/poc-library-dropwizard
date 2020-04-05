package poc.library.dropwizard.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.library.dropwizard.domain.Adherent;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithBookings {

    Adherent adherent;
    List<Booking> bookings;
}
