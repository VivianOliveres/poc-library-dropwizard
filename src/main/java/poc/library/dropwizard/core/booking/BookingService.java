package poc.library.dropwizard.core.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.booking.db.BookingsRepo;
import poc.library.dropwizard.core.booking.request.InsertBookingRequest;
import poc.library.dropwizard.core.booking.request.UpdateBookingRequest;
import poc.library.dropwizard.core.domain.Booking;
import poc.library.dropwizard.utils.Try;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class BookingService {

    public static final int MAX_BOOKING_PER_USER = 3;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingsRepo repo;

    public BookingService(BookingsRepo repo) {
        this.repo = repo;
    }

    public List<Booking> getAllBookingsForUser(long userId) {
        return repo.findBookingsByUSerId(userId);
    }

    public List<Booking> getAllActiveBookingsForUser(long userId) {
        return repo.findActiveBookingByUserId(userId);
    }

    public Try<Booking> borrowBook(@NotNull InsertBookingRequest booking) {
        int bookingsCount = repo.countActiveBookingsByUserId(booking.getUserId());
        if (bookingsCount >= MAX_BOOKING_PER_USER) {
            return Try.right("User[" + booking.getUserId() + "] has booked too many books [" + bookingsCount + "]");
        }

        long bookingId = repo.insertBooking(booking.getUserId(), booking.getBookId().toString(), booking.getBookingDate());
        if (bookingId <= 0) {
            return Try.right("Failed to insert a booking for User[" + booking.getUserId() + "] with book [" + booking.getBookId() + "]");
        }

        Optional<Booking> insertedBooking = repo.getBookingById(bookingId);
        return Try.left(insertedBooking);
    }

    public Try<Booking> returnBook(@NotNull UpdateBookingRequest request) {
        int result = repo.updateBooking(request.getReturnedDate(), request.getBookingId());
        if (result <= 0) {
            return Try.right("Failed to update booking: " + request);
        }

        Optional<Booking> maybeBooking = repo.getBookingById(request.getBookingId());
        if (maybeBooking.isEmpty()) {
            Try.right("Insert of " + request + " has failed");
        }

        return Try.left(maybeBooking);
    }
}
