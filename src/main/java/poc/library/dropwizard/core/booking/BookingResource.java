package poc.library.dropwizard.core.booking;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.domain.Booking;
import poc.library.dropwizard.utils.Either;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/core/booking")
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
    public List<Booking> getAllBookingsForUser(@PathParam("id") @NotNull long userId,
                                               @QueryParam("isActive") Boolean isActive) {
        logger.info("getAllBookingsForUser(userId[{}], isActive[{}])", userId, isActive);
        if (isActive == null || isActive) {
            return service.getAllActiveBookingsForUser(userId);
        }

        return service.getAllBookingsForUser(userId);
    }

    @Timed
    @POST
    public Response insertBooking(@NotNull Booking booking) {
        logger.info("insertBooking({})", booking);
        Either<Booking, String> result = service.borrowBook(booking);
        if (result.isLeft()) {
            return Response.status(Response.Status.CREATED).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }

    @Timed
    @PUT
    public Response updateBooking(@NotNull Booking booking) {
        logger.info("updateBooking({})", booking);
        Either<Booking, String> result = service.returnBook(booking);
        if (result.isLeft()) {
            return Response.status(Response.Status.OK).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }
}
