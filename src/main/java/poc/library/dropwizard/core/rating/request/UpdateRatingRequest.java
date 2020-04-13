package poc.library.dropwizard.core.rating.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UpdateRatingRequest {

    @JsonProperty
    long ratingId;

    @JsonProperty
    int ratingValue;

    public UpdateRatingRequest(@JsonProperty("ratingId") long ratingId,
                               @JsonProperty("ratingValue") int ratingValue) {
        this.ratingId = ratingId;
        this.ratingValue = ratingValue;
    }
}
