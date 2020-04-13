package poc.library.dropwizard.core.user;

import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.domain.User;
import poc.library.dropwizard.utils.ResourceUtils;
import poc.library.dropwizard.utils.Try;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/core/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @Timed
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") long userId) {
        //TODO: delete user in others tables (bookings and ratings) ?
        Try<User> result = service.deleteUser(userId);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @POST
    public Response insertUser(@NotNull User user) {
        logger.info("insertUser({})", user);
        Try<User> result = service.insertUser(user);
        return ResourceUtils.renderCreated(result);
    }

    @Timed
    @GET
    public Response getUsers() {
        logger.info("getUsers");
        Try<List<User>> result = service.getUsers();
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @GET
    @Path("/{userId}")
    public Response getUserById(@PathParam("userId") long userId) {
        logger.info("getUserById({})", userId);
        Try<User> result = service.getUserById(userId);
        return ResourceUtils.renderOk(result);
    }

    @Timed
    @GET
    @Path("/{firstName}/{familyName}")
    @RegisterBeanMapper(User.class)
    public Response getUserByName(@PathParam("firstName") @NotNull String firstName,
                              @PathParam("familyName") @NotNull String familyName) {
        logger.info("getUserByNames({}, {})", firstName, familyName);
        Try<User> result = service.getUserByNames(firstName, familyName);
        return ResourceUtils.renderOk(result);
    }
}
