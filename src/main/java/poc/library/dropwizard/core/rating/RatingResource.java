package poc.library.dropwizard.core.rating;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.domain.Rating;
import poc.library.dropwizard.utils.Either;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/core/rating")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource {

    private static final Logger logger = LoggerFactory.getLogger(RatingResource.class);

    private RatingService service;

    public RatingResource(RatingService service) {
        this.service = service;
    }

    @Timed
    @POST
    public Response insertRating(@NotNull Rating rating) {
        logger.info("insertRating({})", rating);
        Either<Rating, String> result = service.rate(rating);
        if (result.isLeft()) {
            return Response.status(Response.Status.CREATED).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(result.getRight()).build();
    }

    @Timed
    @GET
    public List<Rating> getRatings() {
        return service.getRatings();
    }

    @Timed
    @GET
    @Path("/{ratingId}")
    public Response getRating(@PathParam("ratingId") @NotNull long ratingId) {
        Either<Rating, String> result = service.getRating(ratingId);
        if (result.isLeft()) {
            return Response.status(Response.Status.OK).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }

    @Timed
    @PUT
    public Response updateRating(@NotNull Rating rating) {
        logger.info("updateRating({})", rating);
        Either<Rating, String> result = service.updateRating(rating);
        if (result.isLeft()) {
            return Response.status(Response.Status.OK).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }

    @Timed
    @DELETE
    @Path("/{ratingId}")
    public Response deleteRating(@PathParam("ratingId") long ratingId) {
        Either<Rating, String> result = service.deleteRating(ratingId);
        if (result.isLeft()) {
            return Response.status(Response.Status.OK).entity(result.getLeft()).build();
        }

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), result.getRight()).build();
    }

}
