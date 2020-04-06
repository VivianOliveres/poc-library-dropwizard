package poc.library.dropwizard.booking;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.booking.db.BookingDao;
import poc.library.dropwizard.booking.exceptions.FailedInsertingBookingException;
import poc.library.dropwizard.booking.exceptions.TooManyBookingsException;
import poc.library.dropwizard.booking.model.UserWithBooking;
import poc.library.dropwizard.booking.model.UserWithBookings;

public class BookingService {

    public static final int MAX_BOOKING_PER_USER = 3;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingDao dao;

    public BookingService(BookingDao dao) {
        this.dao = dao;
    }

    public Optional<UserWithBookings> getAllBookingsForUser(long userId) {
        return dao.findBookingsByAdherentId(userId);
    }

    public Optional<UserWithBookings> getAllActiveBookingsForUser(long userId) {
        logger.info("getAllActiveBookingsForUser(userId[{}])", userId);
        return dao.findActiveBookingsByAdherentId(userId);
    }

    public Optional<UserWithBookings> borrowBook(long userId, UUID bookId, LocalDate bookingDate)
            throws TooManyBookingsException, FailedInsertingBookingException {
        int bookingsCount = dao.countActiveBookingsByUserId(userId);
        if (bookingsCount >= MAX_BOOKING_PER_USER) {
            throw new TooManyBookingsException(userId, bookId, bookingsCount);
        }

        int result = dao.insertBooking(userId, bookId.toString(), bookingDate);
        if (result <= 0) {
            throw new FailedInsertingBookingException(userId, bookId);
        }

        return getAllActiveBookingsForUser(userId);
    }

    public void returnBook(long userId, UUID bookId, LocalDate returnedDate) {
        dao.updateBooking(userId, bookId.toString(), returnedDate);
    }

    public Optional<UserWithBooking> getBooking(long userId, UUID bookId) {
        return Optional.ofNullable(dao.getBooking(userId, bookId.toString()));
    }
}
