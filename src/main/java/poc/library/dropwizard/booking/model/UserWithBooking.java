package poc.library.dropwizard.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.library.dropwizard.domain.Adherent;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithBooking {

    Adherent adherent;
    Optional<Booking> booking;

    public List<Booking> getBookings() {
        return booking.map(List::of).orElseGet(List::of);
    }
}
