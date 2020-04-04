package poc.library.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.dao.BookDao;
import poc.library.dropwizard.domain.Book;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.UUID;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookDao dao;

    public BookResource(BookDao dao) {
        this.dao = dao;
    }

    @Timed
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") @NotNull UUID id) {
        int count = dao.deleteBookById(id.toString());
        if (count > 0) {
            return Response.ok(id).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @POST
    public Response insertBook(@NotNull Book book) {
        logger.info("insertBook({})", book);
        int result = dao.insert(book.getId().toString(), book.getTitle());
        if (result > 0) {
            return Response.created(UriBuilder.fromResource(BookResource.class).build(book.getId(), book.getTitle()))
                    .build();
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
