package poc.library.dropwizard.booking.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.booking.model.Booking;
import poc.library.dropwizard.booking.model.UserWithBooking;
import poc.library.dropwizard.domain.Adherent;
import poc.library.dropwizard.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class UserWithBookingMapper implements RowMapper<UserWithBooking> {

    @Override
    public UserWithBooking map(ResultSet rs, StatementContext ctx) throws SQLException {
        Adherent adherent = new Adherent(rs.getLong(1), rs.getString(2), rs.getString(3),
                rs.getObject(4, LocalDate.class), rs.getObject(5, LocalDate.class));

        String bookingIdAsString = rs.getString(6);
        if (bookingIdAsString == null) {
            // There is no booking for this user
            return new UserWithBooking(adherent, Optional.empty());
        }

        Booking booking = new Booking(new Book(UUID.fromString(bookingIdAsString), rs.getString(7)),
                rs.getObject(8, LocalDate.class), Optional.ofNullable(rs.getObject(9, LocalDate.class)));

        return new UserWithBooking(adherent, Optional.of(booking));
    }
}
