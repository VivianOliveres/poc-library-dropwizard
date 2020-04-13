package poc.library.dropwizard.core.booking.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.core.domain.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingMapper implements RowMapper<Booking> {

    @Override
    public Booking map(ResultSet rs, StatementContext ctx) throws SQLException {
        return Booking.of(rs.getLong(1),
                          rs.getLong(2),
                          rs.getString(3),
                          rs.getObject(4, LocalDate.class),
                          rs.getObject(5, LocalDate.class));
    }
}
