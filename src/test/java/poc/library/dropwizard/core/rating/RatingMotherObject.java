package poc.library.dropwizard.core.rating;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.core.domain.Rating;

import java.util.List;

import static poc.library.dropwizard.core.catalog.BookMotherObject.*;
import static poc.library.dropwizard.core.user.UserMotherObject.*;

@UtilityClass
public class RatingMotherObject {

    public static final Rating RATING_VIVIAN_DESIGN_PATTERN = new Rating(1, VIVIAN.getUserId(), DESIGN_PATERNS.getId(), 4);
    public static final Rating RATING_ERICH_CONCURRENCY = new Rating(2, ERICH.getUserId(), CONCURRENCY.getId(), 4);
    public static final Rating RATING_JAMES_DATA = new Rating(3, JAMES.getUserId(), DATA.getId(), 3);
    public static final Rating RATING_JAMES_SCALA = new Rating(4, JAMES.getUserId(), SCALA.getId(), 4);
    public static final Rating RATING_MARGARET_SPARK = new Rating(5, MARGARET.getUserId(), SPARK.getId(), 3);

    public static final List<Rating> ALL_RATINGS = List.of(RATING_VIVIAN_DESIGN_PATTERN,
            RATING_ERICH_CONCURRENCY,
            RATING_JAMES_DATA,
            RATING_JAMES_SCALA,
            RATING_MARGARET_SPARK);
}
