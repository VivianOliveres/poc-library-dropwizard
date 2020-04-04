package poc.library.dropwizard.resources;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codahale.metrics.annotation.Timed;
import poc.library.dropwizard.dao.BookDao;
import poc.library.dropwizard.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookDao bookDao;

    public BookResource(BookDao bookDao) {
        this.bookDao = bookDao;
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
    public Book getBook(@PathParam("id") UUID id) {
        return bookDao.findBookById(id.toString());
    }
}
