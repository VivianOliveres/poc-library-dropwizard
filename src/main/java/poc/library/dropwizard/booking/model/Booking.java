package poc.library.dropwizard.booking.model;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Value;
import poc.library.dropwizard.domain.Book;

@Value
public class Booking {

    Book book;
    LocalDate bookingDate;
    Optional<LocalDate> returnedDate;
}
