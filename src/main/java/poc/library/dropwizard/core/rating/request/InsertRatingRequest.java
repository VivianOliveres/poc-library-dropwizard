package poc.library.dropwizard.core.rating.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;

@Value
public class InsertRatingRequest {

    @JsonProperty
    long userId;

    @JsonProperty
    UUID bookId;

    @JsonProperty int ratingValue;

    public InsertRatingRequest(@JsonProperty("userId") long userId,
                               @JsonProperty("bookId") UUID bookId,
                               @JsonProperty("ratingValue") int ratingValue) {
        this.userId = userId;
        this.bookId = bookId;
        this.ratingValue = ratingValue;
    }
}
