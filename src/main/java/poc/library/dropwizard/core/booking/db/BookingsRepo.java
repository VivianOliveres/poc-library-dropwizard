package poc.library.dropwizard.core.booking.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingsRepo {

    @SqlQuery("SELECT booking_id, user_id, book_id, booking_date, returned_date FROM bookings WHERE user_id = ? AND returned_date IS NULL")
    @RegisterRowMapper(BookingMapper.class)
    List<Booking> findActiveBookingByUserId(long userId);

    @SqlQuery("SELECT booking_id, user_id, book_id, booking_date, returned_date FROM bookings WHERE user_id = ?")
    @RegisterRowMapper(BookingMapper.class)
    List<Booking> findBookingsByUSerId(long userId);

    @SqlQuery("SELECT count(1) FROM bookings WHERE user_id = ? AND returned_date IS NULL")
    int countActiveBookingsByUserId(long userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO bookings (user_id, book_id, booking_date) VALUES (?, ?, ?)")
    long insertBooking(long userId, String bookId, LocalDate bookingDate);

    @SqlUpdate("UPDATE bookings SET returned_date = ? WHERE booking_id = ?")
    int updateBooking(LocalDate returnedDate, long bookingId);

    @SqlQuery("SELECT booking_id, user_id, book_id, booking_date, returned_date FROM bookings WHERE booking_id = ?")
    @RegisterRowMapper(BookingMapper.class)
    Optional<Booking> getBookingById(long bookingId);
}
