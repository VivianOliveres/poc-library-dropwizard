package poc.library.dropwizard.booking.exceptions;

import java.util.UUID;

public class TooManyBookingsException extends RuntimeException {

    public TooManyBookingsException(long userId, UUID bookId, int bookingsCount) {
        super("Adherent[" + userId + "] cannot book [" + bookId + "] because it has already " + bookingsCount
                + " booked");
    }
}