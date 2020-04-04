package poc.library.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.library.dropwizard.dao.BookDao;
import poc.library.dropwizard.domain.Book;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookDao bookDao;

    public BookResource(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Timed
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") @NotNull UUID id) {
        int count = bookDao.deleteBookById(id.toString());
        if (count > 0) {
            return Response.ok(id).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @POST
    public Response insertBook(@NotNull @Valid Book book) {
        logger.info("insertBook({})", book);
        int result = bookDao.insert(book.getId().toString(), book.getTitle());
        if (result > 0) {
            return Response.created(UriBuilder.fromResource(Book.class).build(book)).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Timed
    @GET
    public List<Book> getBooks() {
        return bookDao.findBooks();
    }

    @Timed
    @GET
    @Path("/{id}")
    @RegisterBeanMapper(Book.class)
    public Book getBook(@PathParam("id") @NotNull UUID id) {
        return bookDao.findBookById(id.toString());
    }
}
