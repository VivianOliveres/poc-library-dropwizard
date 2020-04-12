package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Value
public class Booking {

    @JsonProperty long bookingId;
    @JsonProperty long userId;
    @JsonProperty UUID bookId;
    @JsonProperty LocalDate bookingDate;

    @With
    @JsonProperty Optional<LocalDate> returnedDate;

    public static Booking of(long bookingId, long userId, UUID bookId, LocalDate bookingDate) {
        return new Booking(bookingId, userId, bookId, bookingDate, Optional.empty());
    }

    public static Booking of(long bookingId, long userId, String bookId, LocalDate bookingDate) {
        return new Booking(bookingId, userId, UUID.fromString(bookId), bookingDate, Optional.empty());
    }

    public static Booking of(long bookingId, long userId, String bookId, LocalDate bookingDate, LocalDate returnedDate) {
        return new Booking(bookingId, userId, UUID.fromString(bookId), bookingDate, Optional.ofNullable(returnedDate));
    }

    @JsonCreator
    public Booking(@JsonProperty("bookingId") long bookingId,
                   @JsonProperty("userId") long userId,
                   @JsonProperty("bookId") UUID bookId,
                   @JsonProperty("bookingDate") LocalDate bookingDate,
                   @JsonProperty("returnedDate") Optional<LocalDate> returnedDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bookId = bookId;
        this.bookingDate = bookingDate;
        this.returnedDate = returnedDate;
    }
}
