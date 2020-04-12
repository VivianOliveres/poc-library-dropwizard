package poc.library.dropwizard.core.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.booking.db.BookingsRepo;
import poc.library.dropwizard.domain.Booking;
import poc.library.dropwizard.utils.Either;

import java.util.List;
import java.util.Optional;

public class BookingService {

    public static final int MAX_BOOKING_PER_USER = 3;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingsRepo dao;

    public BookingService(BookingsRepo dao) {
        this.dao = dao;
    }

    public List<Booking> getAllBookingsForUser(long userId) {
        return dao.findBookingsByUSerId(userId);
    }

    public List<Booking> getAllActiveBookingsForUser(long userId) {
        return dao.findActiveBookingByUserId(userId);
    }

    public Either<Booking, String> borrowBook(Booking booking) {
        int bookingsCount = dao.countActiveBookingsByUserId(booking.getUserId());
        if (bookingsCount >= MAX_BOOKING_PER_USER) {
            return Either.right("User[" + booking.getUserId() + "] has booked too many books [" + bookingsCount + "]");
        }

        long bookingId = dao.insertBooking(booking.getUserId(), booking.getBookId().toString(), booking.getBookingDate());
        if (bookingId <= 0) {
            return Either.right("Failed to insert a booking for User[" + booking.getUserId() + "] with book [" + booking.getBookId() + "]");
        }

        Optional<Booking> insertedBooking = dao.getBookingById(bookingId);
        return Either.left(insertedBooking);
    }

    public Either<Booking, String> returnBook(Booking booking) {
        int result = dao.updateBooking(booking.getReturnedDate().get(), booking.getBookingId());
        if (result <= 0) {
            return Either.right("Failed to update booking: " + booking);
        }

        return Either.left(booking);
    }
}
