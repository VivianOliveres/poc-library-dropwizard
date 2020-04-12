package poc.library.dropwizard.core.booking;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.domain.Booking;
import poc.library.dropwizard.utils.ResourceUtils;
import poc.library.dropwizard.utils.Try;

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
        Try<Booking> result = service.borrowBook(booking);
        return ResourceUtils.render(result, Response.Status.CREATED);
    }

    @Timed
    @PUT
    public Response updateBooking(@NotNull Booking booking) {
        logger.info("updateBooking({})", booking);
        Try<Booking> result = service.returnBook(booking);
        return ResourceUtils.render(result, Response.Status.OK);
    }
}
