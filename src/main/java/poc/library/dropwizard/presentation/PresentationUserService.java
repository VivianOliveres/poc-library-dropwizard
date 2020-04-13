package poc.library.dropwizard.presentation;

import poc.library.dropwizard.core.booking.BookingService;
import poc.library.dropwizard.core.catalog.BookService;
import poc.library.dropwizard.core.domain.Booking;
import poc.library.dropwizard.core.domain.Rating;
import poc.library.dropwizard.core.domain.User;
import poc.library.dropwizard.core.rating.RatingService;
import poc.library.dropwizard.core.user.UserService;
import poc.library.dropwizard.presentation.domain.*;
import poc.library.dropwizard.utils.Try;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class PresentationUserService {

    private final UserService userService;
    private final BookingService bookingService;
    private final RatingService ratingService;
    private final BookService bookService;

    public PresentationUserService(UserService userService, BookingService bookingService, RatingService ratingService, BookService bookService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.ratingService = ratingService;
        this.bookService = bookService;
    }

    public Try<UserWithRatings> getRatings(long userId) {
        Try<User> maybeUser = userService.getUserById(userId);
        Try<List<RatingWithBook>> ratingWithBooks = getRatingWithBook(maybeUser);
        return maybeUser.zip(ratingWithBooks, UserWithRatings::new);
    }

    private Try<List<RatingWithBook>> getRatingWithBook(Try<User> maybeUser) {
        Try<List<Rating>> ratings = maybeUser.map(user -> ratingService.getRatings(user.getUserId()));
        Try<Books> books = ratings.map(list -> list.stream().map(Rating::getBookId).collect(toSet()))
                .map(bs -> Books.of(bookService.getBooks(bs)));
        return ratings.zip(books, this::zipInRatingWithBook);
    }

    private List<RatingWithBook> zipInRatingWithBook(List<Rating> ratings, Books books) {
        return ratings.stream()
                .map(rating -> new RatingWithBook(rating.getRatingId(), rating.getUserId(), books.get(rating.getBookId()), rating.getRatingValue()))
                .collect(Collectors.toList());
    }

    public Try<UserWithBookings> getBookings(long userId) {
        Try<User> maybeUser = userService.getUserById(userId);
        Try<List<BookingWithBook>> bookingWithBooks = getBookingWithBook(maybeUser);
        return maybeUser.zip(bookingWithBooks, UserWithBookings::new);
    }

    private Try<List<BookingWithBook>> getBookingWithBook(Try<User> maybeUser) {
        Try<List<Booking>> bookings = maybeUser.map(user -> bookingService.getAllActiveBookingsForUser(user.getUserId()));
        Try<Books> books = bookings.map(list -> list.stream().map(Booking::getBookId).collect(toSet()))
                .map(bs -> Books.of(bookService.getBooks(bs)));
        return bookings.zip(books, this::zipInBookingWithBook);
    }

    private List<BookingWithBook> zipInBookingWithBook(List<Booking> bookings, Books books) {
        return bookings.stream()
                .map(booking -> new BookingWithBook(booking, books))
                .collect(Collectors.toList());
    }

    public Try<UserWithRatingsAndBookings> getUser(long userId) {
        Try<User> maybeUser = userService.getUserById(userId);
        Try<List<RatingWithBook>> ratingWithBooks = getRatingWithBook(maybeUser);
        Try<List<BookingWithBook>> bookingWithBooks = getBookingWithBook(maybeUser);
        return maybeUser.zip(ratingWithBooks, UserWithRatingsAndBookings::new)
                        .zip(bookingWithBooks, UserWithRatingsAndBookings::withBookings);
    }
}
