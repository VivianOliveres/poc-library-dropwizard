package poc.library.dropwizard.booking.exceptions;

import java.util.UUID;

public class FailedInsertingBookingException extends RuntimeException {

    public FailedInsertingBookingException(long userId, UUID bookId) {
        super(createMessage(userId, bookId));
    }

    public FailedInsertingBookingException(long userId, UUID bookId, Throwable cause) {
        super(createMessage(userId, bookId), cause);
    }

    private static String createMessage(long userId, UUID bookId) {
        return "Failed to insert a booking from adherent [" + userId + "] for book [" + bookId + "]";
    }
}
