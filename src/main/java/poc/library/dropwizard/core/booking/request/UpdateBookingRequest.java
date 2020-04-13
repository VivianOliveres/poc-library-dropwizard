package poc.library.dropwizard.core.booking.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UpdateBookingRequest {

    @JsonProperty
    long bookingId;

    @JsonProperty
    LocalDate returnedDate;

    @JsonCreator
    public UpdateBookingRequest(@JsonProperty("bookingId") long bookingId,
                                @JsonProperty("returnedDate") LocalDate returnedDate) {
        this.bookingId = bookingId;
        this.returnedDate = returnedDate;
    }
}
