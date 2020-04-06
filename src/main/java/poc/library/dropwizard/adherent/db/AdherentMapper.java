package poc.library.dropwizard.adherent.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import poc.library.dropwizard.domain.Adherent;

public class AdherentMapper implements RowMapper<Adherent> {

    @Override
    public Adherent map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Adherent(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getObject(4, LocalDate.class),
                rs.getObject(5, LocalDate.class));
    }
}
