package poc.library.dropwizard.book.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.domain.Book;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Book(UUID.fromString(rs.getString(1)), rs.getString(2));
    }
}
