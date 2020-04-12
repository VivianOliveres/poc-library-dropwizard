package poc.library.dropwizard.core.user;

import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.user.db.UsersRepo;
import poc.library.dropwizard.domain.User;

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

    private final UsersRepo dao;

    public UserResource(UsersRepo dao) {
        this.dao = dao;
    }

    @Timed
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") long userId) {
        int count = dao.deleteUserById(userId);
        if (count > 0) {
            return Response.ok(userId).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @POST
    public Response insertUser(@NotNull User user) {
        logger.info("insertUser({})", user);
        long result =
                dao.insert(
                        user.getFirstName(),
                        user.getFamilyName(),
                        user.getBirthDate(),
                        user.getMembershipDate());
        if (result > 0) {
            return Response.status(Response.Status.CREATED).entity(user.withUserId(result)).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @GET
    public List<User> getUsers() {
        logger.info("getUsers");
        return dao.findUsers();
    }

    @Timed
    @GET
    @Path("/{userId}")
    @RegisterBeanMapper(User.class)
    public User getUserById(@PathParam("userId") long userId) {
        logger.info("getUserById({})", userId);
        return dao.findUserById(userId);
    }

    @Timed
    @GET
    @Path("/{firstName}/{familyName}")
    @RegisterBeanMapper(User.class)
    public User getUserByName(@PathParam("firstName") @NotNull String firstName,
                              @PathParam("familyName") @NotNull String familyName) {
        logger.info("getUserByNames({}, {})", firstName, familyName);
        return dao.findUserByNames(firstName, familyName);
    }
}
