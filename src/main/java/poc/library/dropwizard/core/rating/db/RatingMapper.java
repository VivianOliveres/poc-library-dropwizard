package poc.library.dropwizard.core.rating.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.core.domain.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RatingMapper implements RowMapper<Rating> {

    @Override
    public Rating map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Rating(rs.getLong(1),
                          rs.getLong(2),
                          UUID.fromString(rs.getString(3)),
                          rs.getInt(4));
    }
}
