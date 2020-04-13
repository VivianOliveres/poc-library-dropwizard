package poc.library.dropwizard.core.booking.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
public class InsertBookingRequest {

    @JsonProperty
    long userId;

    @JsonProperty
    UUID bookId;

    @JsonProperty
    LocalDate bookingDate;

    @JsonCreator
    public InsertBookingRequest(@JsonProperty("userId") long userId,
                                @JsonProperty("bookId") UUID bookId,
                                @JsonProperty("bookingDate") LocalDate bookingDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookingDate = bookingDate;
    }

}
