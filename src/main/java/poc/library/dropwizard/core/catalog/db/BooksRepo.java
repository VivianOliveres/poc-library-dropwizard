package poc.library.dropwizard.core.catalog.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.core.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BooksRepo {

    @SqlUpdate("INSERT INTO books (book_id, title) VALUES (?, ?)")
    int insert(@Bind("bookId") String bookId, @Bind("title") String title);

    @SqlQuery("SELECT book_id, title FROM books WHERE book_id = ?")
    @RegisterRowMapper(BookMapper.class)
    Optional<Book> findBookById(String bookId);

    @SqlQuery("SELECT book_id, title FROM books")
    @RegisterRowMapper(BookMapper.class)
    List<Book> findBooks();

    @SqlQuery("SELECT book_id, title FROM books WHERE book_id IN (<books>)")
    @RegisterRowMapper(BookMapper.class)
    List<Book> findBookByIds(@BindList("books") List<String> books);
}
