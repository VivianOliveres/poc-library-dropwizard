package poc.library.dropwizard.core.booking;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.domain.Booking;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class BookingMotherObject {

    public static final Booking BOOKING_VIVIAN_DATA = Booking.of(1,1, "c6708e32-89ba-4418-b70f-9f7e359e822b", LocalDate.of(2020, 4, 3));

    public static final Booking BOOKING_VIVIAN_SPARK = Booking.of(2, 1, "ee1b0b0d-256f-45d9-b018-ced809656c06", LocalDate.of(2020, 4, 3));

    public static final Booking BOOKING_ERICH_SCALA = Booking.of(3, 2, "d244089b-cdcf-49be-a792-6f040d2b6713", LocalDate.of(2020, 3, 27));

    public static final Booking BOOKING_JAMES_SCALA = Booking.of(4, 3, "d244089b-cdcf-49be-a792-6f040d2b6713", LocalDate.of(2020, 3, 27), LocalDate.of(2020, 3, 30));

    public static final List<Booking> ALL_BOOKINGS = List.of(BOOKING_VIVIAN_DATA, BOOKING_VIVIAN_SPARK, BOOKING_ERICH_SCALA, BOOKING_JAMES_SCALA);
}
