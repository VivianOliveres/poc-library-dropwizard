package poc.library.dropwizard.adherent;

import com.codahale.metrics.annotation.Timed;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.adherent.db.AdherentRepo;
import poc.library.dropwizard.domain.Adherent;

@Path("/adherent")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdherentResource {

    private static final Logger logger = LoggerFactory.getLogger(AdherentResource.class);

    private final AdherentRepo dao;

    public AdherentResource(AdherentRepo dao) {
        this.dao = dao;
    }

    @Timed
    @DELETE
    @Path("/{userId}")
    public Response deleteAdherent(@PathParam("userId") long userId) {
        int count = dao.deleteAdherentById(userId);
        if (count > 0) {
            return Response.ok(userId).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @DELETE
    @Path("/{firstName}/{familyName}")
    public Response deleteAdherent(
            @PathParam("firstName") @NotNull String firstName,
            @PathParam("familyName") @NotNull String familyName) {
        int count = dao.deleteAdherentByNames(firstName, familyName);
        if (count > 0) {
            return Response.ok(count).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @POST
    public Response insertAdherent(@NotNull Adherent adherent) {
        logger.info("insertAdherent({})", adherent);
        long result =
                dao.insert(
                        adherent.getFirstName(),
                        adherent.getFamilyName(),
                        adherent.getBirthDate(),
                        adherent.getMembershipDate());
        if (result > 0) {
            return Response.status(Response.Status.CREATED).entity(adherent).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @GET
    public List<Adherent> getAdherents() {
        logger.info("getAdherents");
        return dao.findAdherents();
    }

    @Timed
    @GET
    @Path("/{userId}")
    @RegisterBeanMapper(Adherent.class)
    public Adherent getAdherentById(@PathParam("userId") long userId) {
        logger.info("getAdherentById({})", userId);
        return dao.findAdherentById(userId);
    }

    @Timed
    @GET
    @Path("/{firstName}/{familyName}")
    @RegisterBeanMapper(Adherent.class)
    public Adherent getAdherentByNames(
            @PathParam("firstName") @NotNull String firstName,
            @PathParam("familyName") @NotNull String familyName) {
        logger.info("getAdherentByNames({}, {})", firstName, familyName);
        return dao.findAdherentByNames(firstName, familyName);
    }
}
