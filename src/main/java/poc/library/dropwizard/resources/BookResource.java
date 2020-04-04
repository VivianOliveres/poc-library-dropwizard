package poc.library.dropwizard.resources;
import com.codahale.metrics.annotation.Timed;
import poc.library.dropwizard.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    @Timed
    public Book getBook() {
        return new Book(UUID.randomUUID(), "9780596007126", "Head First Design Patterns: A Brain-Friendly Guide");
    }
}
