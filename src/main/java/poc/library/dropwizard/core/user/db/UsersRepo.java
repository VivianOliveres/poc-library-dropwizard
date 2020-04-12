package poc.library.dropwizard.core.user.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsersRepo {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO users (first_name, family_name, birth_date, membership_date) VALUES (?, ?, ?, ?)")
    long insert(String firstName, String familyName, LocalDate birthDate, LocalDate membershipDate);

    @SqlQuery("SELECT user_id, first_name, family_name, birth_date, membership_date FROM users WHERE user_id = ?")
    @RegisterRowMapper(UserMapper.class)
    Optional<User> findUserById(long userId);

    @SqlQuery("SELECT user_id, first_name, family_name, birth_date, membership_date FROM users WHERE first_name = ? AND family_name = ?")
    @RegisterRowMapper(UserMapper.class)
    Optional<User> findUserByNames(String firstName, String familyName);

    @SqlQuery("SELECT user_id, first_name, family_name, birth_date, membership_date FROM users")
    @RegisterRowMapper(UserMapper.class)
    List<User> findUsers();

    @SqlUpdate("DELETE FROM users WHERE user_id = ?")
    int deleteUserById(long userId);

}
