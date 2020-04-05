package poc.library.dropwizard.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poc.library.dropwizard.domain.Book;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    Book book;
    LocalDate bookingDate;
    Optional<LocalDate> returnedDate;

}
