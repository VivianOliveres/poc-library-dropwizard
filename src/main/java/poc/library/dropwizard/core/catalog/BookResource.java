package poc.library.dropwizard.core.catalog;

import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.core.catalog.db.BooksRepo;
import poc.library.dropwizard.domain.Book;

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

    private final BooksRepo dao;

    public BookResource(BooksRepo dao) {
        this.dao = dao;
    }

    @Timed
    @POST
    public Response insertBook(@NotNull Book book) {
        logger.info("insertBook({})", book);
        int result = dao.insert(book.getId().toString(), book.getTitle());
        if (result > 0) {
            return Response.status(Response.Status.CREATED).entity(book).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @GET
    public List<Book> getBooks() {
        return dao.findBooks();
    }

    @Timed
    @GET
    @Path("/{id}")
    @RegisterBeanMapper(Book.class)
    public Book getBook(@PathParam("id") @NotNull UUID id) {
        return dao.findBookById(id.toString());
    }
}
