package poc.library.dropwizard.adherent.db;

import java.time.LocalDate;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Adherent;

public interface AdherentRepo {

    @GetGeneratedKeys
    @SqlUpdate(
            "INSERT INTO adherent (first_name, family_name, birth_date, membership_date) VALUES (?, ?, ?, ?)")
    long insert(String firstName, String familyName, LocalDate birthDate, LocalDate membershipDate);

    @SqlQuery(
            "SELECT user_id, first_name, family_name, birth_date, membership_date FROM adherent WHERE user_id = ?")
    @RegisterRowMapper(AdherentMapper.class)
    Adherent findAdherentById(long userId);

    @SqlQuery(
            "SELECT user_id, first_name, family_name, birth_date, membership_date FROM adherent WHERE first_name = ? AND family_name = ?")
    @RegisterRowMapper(AdherentMapper.class)
    Adherent findAdherentByNames(String firstName, String familyName);

    @SqlQuery("SELECT user_id, first_name, family_name, birth_date, membership_date FROM adherent")
    @RegisterRowMapper(AdherentMapper.class)
    List<Adherent> findAdherents();

    @SqlUpdate("DELETE FROM adherent WHERE user_id = ?")
    int deleteAdherentById(long userId);

    @SqlUpdate("DELETE FROM adherent WHERE first_name = ? AND family_name = ?")
    int deleteAdherentByNames(String firstName, String familyName);
}
