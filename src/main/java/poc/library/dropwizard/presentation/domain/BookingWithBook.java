package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;
import poc.library.dropwizard.core.domain.Book;
import poc.library.dropwizard.core.domain.Booking;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Value
public class BookingWithBook {

    @JsonProperty
    long bookingId;

    @JsonProperty
    long userId;

    @JsonProperty
    Book book;

    @JsonProperty
    LocalDate bookingDate;

    @With
    @JsonProperty
    Optional<LocalDate> returnedDate;

    @JsonCreator
    public BookingWithBook(@JsonProperty("bookingId") long bookingId,
                           @JsonProperty("userId") long userId,
                           @JsonProperty("book") Book book,
                           @JsonProperty("bookingDate") LocalDate bookingDate,
                           @JsonProperty("returnedDate") Optional<LocalDate> returnedDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.book = book;
        this.bookingDate = bookingDate;
        this.returnedDate = returnedDate;
    }

    public BookingWithBook(Booking booking, Books books) {
        this(booking, books.get(booking.getBookId()));
    }

    public BookingWithBook(Booking booking, Book book) {
        this(booking.getBookingId(), booking.getUserId(), book, booking.getBookingDate(), booking.getReturnedDate());
        Objects.requireNonNull(book);
    }
}
