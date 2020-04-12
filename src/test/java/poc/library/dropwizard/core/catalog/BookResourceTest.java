package poc.library.dropwizard.core.catalog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import poc.library.dropwizard.AbstractIntegrationTest;
import poc.library.dropwizard.domain.Book;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BookResourceTest extends AbstractIntegrationTest {

    @Test
    public void should_retrieve_all_books() throws JsonProcessingException {
        Response response = target("/core/catalog").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        String resultAsJson = response.readEntity(String.class);
        List<Book> result = MAPPER.readValue(resultAsJson, new TypeReference<>(){});

        assertThat(result).containsAll(BookMotherObject.ALL_BOOKS);
        assertThat(result).hasSize(BookMotherObject.ALL_BOOKS.size());
    }

    @Test
    public void should_retrieve_book_by_id() {
        for (Book book : BookMotherObject.ALL_BOOKS) {
            Response response = target("/core/catalog/" + book.getId()).request().get();

            assertThat(response.getStatus()).isEqualTo(200);

            Book result = response.readEntity(Book.class);

            assertThat(result).isEqualTo(book);
        }
    }

    @Test
    public void should_insert_book() {
        Book effectiveJavaBook = new Book(UUID.randomUUID(), "Effective Java (2nd Edition)");
        Response response = target("/core/catalog").request()
                .post(Entity.entity(effectiveJavaBook, APPLICATION_JSON));

        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        Book result = response.readEntity(Book.class);
        assertThat(result).isEqualTo(effectiveJavaBook);
    }
}
