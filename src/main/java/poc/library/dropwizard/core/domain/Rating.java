package poc.library.dropwizard.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@Value
public class Rating {

    @JsonProperty
    long ratingId;

    @JsonProperty
    long userId;

    @JsonProperty
    UUID bookId;

    @With
    @JsonProperty int ratingValue;

    public Rating(@JsonProperty("ratingId") long ratingId,
                  @JsonProperty("userId") long userId,
                  @JsonProperty("bookId") UUID bookId,
                  @JsonProperty("ratingValue") int ratingValue) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.bookId = bookId;
        this.ratingValue = ratingValue;
    }
}
