package poc.library.dropwizard.core.rating.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingsRepo {

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO ratings (user_id, book_id, rating_value) VALUES (?, ?, ?)")
    long insertRating(long userId, String bookId, int ratingValue);

    @SqlQuery("SELECT rating_id, user_id, book_id, rating_value FROM ratings WHERE rating_id = ?")
    @RegisterRowMapper(RatingMapper.class)
    Optional<Rating> getRatingById(long ratingId);

    @SqlQuery("SELECT rating_id, user_id, book_id, rating_value FROM ratings")
    @RegisterRowMapper(RatingMapper.class)
    List<Rating> getRatings();

    @SqlUpdate("UPDATE ratings SET rating_value = ? WHERE rating_id = ?")
    int updateRating(int ratingValue, long ratingId);

    @SqlUpdate("DELETE FROM ratings WHERE rating_id = ?")
    int deleteRating(long ratingId);
}
