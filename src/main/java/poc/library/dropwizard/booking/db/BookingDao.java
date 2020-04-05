package poc.library.dropwizard.booking.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.booking.model.Booking;
import poc.library.dropwizard.booking.model.UserWithBooking;
import poc.library.dropwizard.booking.model.UserWithBookings;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface BookingDao {

    Logger logger = LoggerFactory.getLogger(BookingDao.class);

    @SqlQuery("SELECT " + "    a.user_id, " + "    a.first_name, " + "    a.family_name, " + "    a.birth_date, "
            + "    a.membership_date, " + "    d.book_id, " + "    d.title, " + "    d.booking_date, "
            + "    d.returned_date " + "FROM " + "    adherent AS a " + "LEFT JOIN ( " + "    SELECT "
            + "        c.user_id AS user_id," + "        b.book_id AS book_id, " + "        b.title AS title, "
            + "        c.booking_date AS booking_date, " + "        c.returned_date AS returned_date "
            + "    FROM book AS b " + "    LEFT JOIN bookings AS c ON c.book_id = b.book_id "
            + "    WHERE c.returned_date IS NULL " + ") AS d ON a.user_id = d.user_id " + "WHERE "
            + "    a.user_id = :userId;")
    @RegisterRowMapper(UserWithBookingMapper.class)
    List<UserWithBooking> findActiveBookingByAdherentId(@Bind("userId") long userId);

    default Optional<UserWithBookings> findActiveBookingsByAdherentId(long userId) {
        List<UserWithBooking> bookings = findActiveBookingByAdherentId(userId);
        logger.info("findActiveBookingsByAdherentId(userId[{}]) => {}", userId, bookings);
        return reduceBooking(bookings);
    }

    @SqlQuery("SELECT " + "    a.user_id, " + "    a.first_name, " + "    a.family_name, " + "    a.birth_date, "
            + "    a.membership_date, " + "    b.book_id, " + "    b.title, " + "    c.booking_date, "
            + "    c.returned_date " + "FROM " + "    adherent AS a "
            + "LEFT JOIN bookings AS c ON a.user_id = c.user_id " + "LEFT JOIN book AS b ON c.book_id = b.book_id "
            + "WHERE " + "    a.user_id = :userId;")
    @RegisterRowMapper(UserWithBookingMapper.class)
    List<UserWithBooking> findBookingByAdherentId(@Bind("userId") long userId);

    default Optional<UserWithBookings> findBookingsByAdherentId(long userId) {
        List<UserWithBooking> bookings = findBookingByAdherentId(userId);
        return reduceBooking(bookings);
    }

    private Optional<UserWithBookings> reduceBooking(List<UserWithBooking> bookings) {
        return bookings.stream().map(uwb -> new UserWithBookings(uwb.getAdherent(), uwb.getBookings()))
                .reduce(this::reduce);
    }

    private UserWithBookings reduce(UserWithBookings left, UserWithBookings right) {
        Stream<Booking> concatBookings = Stream.concat(left.getBookings().stream(), right.getBookings().stream());
        return new UserWithBookings(left.getAdherent(), concatBookings.collect(toList()));
    }

    @SqlQuery("SELECT count(1) FROM bookings WHERE user_id = :userId AND returned_date IS NULL;")
    int countActiveBookingsByUserId(@Bind("userId") long userId);

    @SqlUpdate("INSERT INTO bookings (user_id, book_id, booking_date) VALUES (:userId, :bookId, :bookingDate);")
    int insertBooking(@Bind("userId") long userId, @Bind("bookId") String bookId,
            @Bind("bookingDate") LocalDate bookingDate);

    @SqlUpdate("UPDATE bookings SET returned_date = :returnedDate WHERE user_id = :userId AND book_id = :bookId;")
    int updateBooking(@Bind("userId") long userId, @Bind("bookId") String bookId,
            @Bind("returnedDate") LocalDate returnedDate);

    @SqlQuery("SELECT " + "    a.user_id, " + "    a.first_name, " + "    a.family_name, " + "    a.birth_date, "
            + "    a.membership_date, " + "    b.book_id, " + "    b.title, " + "    c.booking_date, "
            + "    c.returned_date " + "FROM " + "    adherent AS a "
            + "LEFT JOIN bookings AS c ON a.user_id = c.user_id " + "LEFT JOIN book AS b ON c.book_id = b.book_id "
            + "WHERE " + "    a.user_id = :userId AND b.book_id = :bookId;")
    @RegisterRowMapper(UserWithBookingMapper.class)
    UserWithBooking getBooking(@Bind("userId") long userId, @Bind("bookId") String bookId);
}
