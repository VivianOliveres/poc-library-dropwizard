package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;
import poc.library.dropwizard.core.domain.Book;
import poc.library.dropwizard.core.domain.Rating;

import java.util.Objects;

@Value
public class RatingWithBook {

    @JsonProperty
    long ratingId;

    @JsonProperty
    long userId;

    @JsonProperty
    Book book;

    @With
    @JsonProperty
    int ratingValue;

    @JsonCreator
    public RatingWithBook(@JsonProperty("ratingId") long ratingId,
                          @JsonProperty("userId") long userId,
                          @JsonProperty("book") Book book,
                          @JsonProperty("ratingValue") int ratingValue) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.book = book;
        this.ratingValue = ratingValue;
    }

    public RatingWithBook(Rating rating, Book book) {
        this(rating.getRatingId(), rating.getUserId(), book, rating.getRatingValue());
        Objects.requireNonNull(book);
    }
}
