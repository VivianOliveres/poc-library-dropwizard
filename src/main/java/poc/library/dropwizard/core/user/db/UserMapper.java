package poc.library.dropwizard.core.user.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.core.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new User(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getObject(4, LocalDate.class),
                        rs.getObject(5, LocalDate.class));
    }
}
