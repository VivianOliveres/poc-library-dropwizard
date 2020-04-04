package poc.library.dropwizard.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Adherent;

import java.time.LocalDate;
import java.util.List;

public interface AdherentDao {

    @GetGeneratedKeys
    @SqlUpdate("insert into adherent (first_name, family_name, birth_date, membership_date) values (:firstName, :familyName, :birthDate, :membershipDate)")
    long insert(@Bind("firstName") String firstName, @Bind("familyName") String familyName,
            @Bind("birthDate") LocalDate birthDate, @Bind("membershipDate") LocalDate membershipDate);

    @SqlQuery("select user_id, first_name, family_name, birth_date, membership_date from adherent where user_id = :userId")
    @RegisterRowMapper(AdherentMapper.class)
    Adherent findAdherentById(@Bind("userId") long userId);

    @SqlQuery("select user_id, first_name, family_name, birth_date, membership_date from adherent where first_name = :firstName and family_name = :familyName")
    @RegisterRowMapper(AdherentMapper.class)
    Adherent findAdherentByNames(@Bind("firstName") String firstName, @Bind("familyName") String familyName);

    @SqlQuery("select user_id, first_name, family_name, birth_date, membership_date from adherent")
    @RegisterRowMapper(AdherentMapper.class)
    List<Adherent> findAdherents();

    @SqlUpdate("delete from adherent where user_id = :userId")
    int deleteAdherentById(@Bind("userId") long userId);

    @SqlUpdate("delete from adherent where first_name = :firstName and family_name = :familyName")
    int deleteAdherentByNames(@Bind("firstName") String firstName, @Bind("familyName") String familyName);

}
