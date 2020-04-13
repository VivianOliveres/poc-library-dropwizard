package poc.library.dropwizard.core.catalog;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.catalog.request.InsertBookRequest;
import poc.library.dropwizard.core.domain.Book;
import poc.library.dropwizard.utils.ResourceUtils;
import poc.library.dropwizard.utils.Try;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/core/catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookService service;

    public BookResource(BookService service) {
        this.service = service;
    }

    @Timed
    @POST
    public Response insertBook(@NotNull InsertBookRequest request) {
        logger.info("insertBook({})", request);
        Try<Book> result = service.insertBook(request);
        return ResourceUtils.renderCreated(result);
    }

    @Timed
    @GET
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @Timed
    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") @NotNull UUID id) {
        Try<Book> result = service.getBook(id);
        return ResourceUtils.renderOk(result);
    }
}
