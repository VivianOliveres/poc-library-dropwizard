package poc.library.dropwizard.presentation;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.presentation.domain.UserWithBookings;
import poc.library.dropwizard.presentation.domain.UserWithRatings;
import poc.library.dropwizard.presentation.domain.UserWithRatingsAndBookings;
import poc.library.dropwizard.utils.ResourceUtils;
import poc.library.dropwizard.utils.Try;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/presentation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PresentationUserResource {

    private static final Logger logger = LoggerFactory.getLogger(PresentationUserResource.class);

    private final PresentationUserService service;

    public PresentationUserResource(PresentationUserService service) {
        this.service = service;
    }

    @Timed
    @GET
    @Path("/rating/{userId}")
    public Response getRatings(@PathParam("userId") @NotNull long userId) {
        logger.info("getRatings({})", userId);
        Try<UserWithRatings> result = service.getRatings(userId);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @GET
    @Path("/booking/{userId}")
    public Response getBookings(@PathParam("userId") @NotNull long userId) {
        logger.info("getBookings({})", userId);
        Try<UserWithBookings> result = service.getBookings(userId);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @GET
    @Path("/user/{userId}")
    public Response getUser(@PathParam("userId") @NotNull long userId) {
        logger.info("getUser({})", userId);
        Try<UserWithRatingsAndBookings> result = service.getUser(userId);
        return ResourceUtils.renderOk(result);
    }

}
