package poc.library.dropwizard.resources;

import org.junit.Test;
import poc.library.dropwizard.dao.BookDao;
import poc.library.dropwizard.domain.Book;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static poc.library.dropwizard.resources.BookMotherObject.*;

public class BookResourceTest {

    private BookDao dao = mock(BookDao.class);

    private BookResource resource = new BookResource(dao);

    @Test
    public void should_getBooks_return_empty_when_dao_contains_no_books() {
        // GIVEN: dao contains no books
        when(dao.findBooks()).thenReturn(List.of());

        // WHEN: getBooks
        List<Book> result = resource.getBooks();

        // THEN: resource return no books
        assertThat(result).isEmpty();
    }

    @Test
    public void should_getBooks_return_books_from_dao() {
        // GIVEN: dao contains 2 items
        when(dao.findBooks()).thenReturn(List.of(SCALA, SPARK));

        // WHEN: getBooks
        List<Book> result = resource.getBooks();

        // THEN: resource returns 2 items
        assertThat(result).hasSize(2);
        assertThat(result).contains(SCALA, SPARK);
    }

    @Test
    public void should_insertBook_return_ok_if_dao_has_successfully_inserted_book() {
        // GIVEN: dao insert 1 book
        when(dao.insert(eq(CONCURRENCY.getId().toString()), eq(CONCURRENCY.getTitle()))).thenReturn(1);

        // WHEN: insertBook
        Response response = resource.insertBook(CONCURRENCY);

        // THEN: response is created
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void should_insertBook_return_BadRequest_if_dao_has_failed_to_insert_book() {
        // GIVEN: dao fails to insert 1 book
        when(dao.insert(eq(CONCURRENCY.getId().toString()), eq(CONCURRENCY.getTitle()))).thenReturn(0);

        // WHEN: insertBook
        Response response = resource.insertBook(CONCURRENCY);

        // THEN: response is BadRequest
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_deleteBook_return_ok_if_dao_has_successfully_deleted_a_book() {
        // GIVEN: dao delete 1 book
        when(dao.deleteBookById(eq(DESIGN_PATERNS.getId().toString()))).thenReturn(1);

        // WHEN: deleteBook
        Response response = resource.deleteBook(DESIGN_PATERNS.getId());

        // THEN: response is ok
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_deleteBook_return_BadRequest_if_dao_has_failed_to_delete_a_book() {
        // GIVEN: dao fails to delete 1 book
        when(dao.deleteBookById(eq(DESIGN_PATERNS.getId().toString()))).thenReturn(0);

        // WHEN: deleteBook
        Response response = resource.deleteBook(DESIGN_PATERNS.getId());

        // THEN: response is BadRequest
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_getBook_return_ok_if_dao_findBookById() {
        // GIVEN: dao return 1 book
        when(dao.findBookById(eq(DATA.getId().toString()))).thenReturn(DATA);

        // WHEN: getBook
        Book result = resource.getBook(DATA.getId());

        // THEN: result is DATA
        assertThat(result).isEqualTo(DATA);
    }

    @Test
    public void should_getBook_return_null_if_dao_findBookById_return_null() {
        // GIVEN: dao fails to delete 1 book
        when(dao.findBookById(eq(DATA.getId().toString()))).thenReturn(null);

        // WHEN: getBook
        Book result = resource.getBook(DATA.getId());

        // THEN: result is null
        assertThat(result).isNull();
    }

}
