package poc.library.dropwizard.booking;

import com.codahale.metrics.annotation.Timed;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.booking.model.UserWithBooking;
import poc.library.dropwizard.booking.model.UserWithBookings;

@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    private static final Logger logger = LoggerFactory.getLogger(BookingResource.class);

    private final BookingService service;

    public BookingResource(BookingService service) {
        this.service = service;
    }

    @Timed
    @GET
    @Path("/{id}")
    @RegisterBeanMapper(UserWithBookings.class)
    public Response getAllBookingsForUser(
            @PathParam("id") @NotNull long userId, @QueryParam("isActive") Boolean isActive) {
        logger.info("getAllBookingsForUser(userId[{}], isActive[{}])", userId, isActive);
        Optional<UserWithBookings> maybeBooking;
        if (isActive == null || isActive) {
            maybeBooking = service.getAllActiveBookingsForUser(userId);
        } else {
            maybeBooking = service.getAllBookingsForUser(userId);
        }

        logger.info(
                "getAllBookingsForUser(userId[{}], isActive[{}]): service returned => {}",
                userId,
                isActive,
                maybeBooking);
        return maybeBooking
                .map(booking -> Response.ok(booking).build())
                .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
    }

    @Timed
    @POST
    @Path("/{userId}/{bookId}")
    public Response insertBooking(
            @PathParam("userId") @NotNull long userId, @PathParam("bookId") @NotNull UUID bookId) {
        logger.info("insertBooking({}, {})", userId, bookId);
        try {
            Optional<UserWithBookings> result = service.borrowBook(userId, bookId, LocalDate.now());
            return Response.status(Response.Status.CREATED).entity(result).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage())
                    .build();
        }
    }

    @Timed
    @PUT
    @Path("/{userId}/{bookId}")
    @RegisterBeanMapper(UserWithBooking.class)
    public Response updateBooking(
            @PathParam("userId") @NotNull long userId, @PathParam("bookId") @NotNull UUID bookId) {
        logger.info("updateBooking({}, {})", userId, bookId);
        // LocalDate returnedDate = paramReturnedDate == null ? LocalDate.now() :
        // paramReturnedDate;
        LocalDate returnedDate = LocalDate.now();
        try {
            service.returnBook(userId, bookId, returnedDate);

            Optional<UserWithBooking> booking = service.getBooking(userId, bookId);
            return Response.ok(booking).build();

        } catch (Exception e) {
            logger.error("updateBooking({}, {})", userId, bookId, e);
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage())
                    .build();
        }
    }
}
