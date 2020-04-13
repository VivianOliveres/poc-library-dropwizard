package poc.library.dropwizard.presentation;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.presentation.domain.BookingWithBook;
import poc.library.dropwizard.presentation.domain.RatingWithBook;

import static poc.library.dropwizard.core.booking.BookingMotherObject.*;
import static poc.library.dropwizard.core.catalog.BookMotherObject.*;
import static poc.library.dropwizard.core.rating.RatingMotherObject.*;

@UtilityClass
public class PresentationUserMotherObject {

    public static BookingWithBook BOOKINGWITHBOOK_VIVIAN_DATA = new BookingWithBook(BOOKING_VIVIAN_DATA, DATA);
    public static BookingWithBook BOOKINGWITHBOOK_VIVIAN_SPARK = new BookingWithBook(BOOKING_VIVIAN_SPARK, SPARK);
    public static BookingWithBook BOOKINGWITHBOOK_ERICH_SCALA = new BookingWithBook(BOOKING_ERICH_SCALA, SCALA);

    public static RatingWithBook RATINGWITHBOOK_JAMES_DATA = new RatingWithBook(RATING_JAMES_DATA, DATA);
    public static RatingWithBook RATINGWITHBOOK_JAMES_SCALA = new RatingWithBook(RATING_JAMES_SCALA, SCALA);
    public static RatingWithBook RATINGWITHBOOK_ERICH_CONCURRENCY = new RatingWithBook(RATING_ERICH_CONCURRENCY, CONCURRENCY);
}
