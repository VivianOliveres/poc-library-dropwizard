package poc.library.dropwizard.core.rating;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.domain.Rating;
import poc.library.dropwizard.utils.ResourceUtils;
import poc.library.dropwizard.utils.Try;

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
        Try<Rating> result = service.rate(rating);
        return ResourceUtils.renderCreated(result);
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
        Try<Rating> result = service.getRating(ratingId);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @PUT
    public Response updateRating(@NotNull Rating rating) {
        logger.info("updateRating({})", rating);
        Try<Rating> result = service.updateRating(rating);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @DELETE
    @Path("/{ratingId}")
    public Response deleteRating(@PathParam("ratingId") long ratingId) {
        Try<Rating> result = service.deleteRating(ratingId);
        return ResourceUtils.renderOk(result);
    }

}
