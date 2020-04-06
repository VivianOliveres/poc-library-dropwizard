package poc.library.dropwizard.book.db;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Book;

public interface BookRepo {

    @SqlUpdate("INSERT INTO book (book_id, title) VALUES (?, ?)")
    int insert(@Bind("bookId") String bookId, @Bind("title") String title);

    @SqlQuery("SELECT book_id, title FROM book WHERE book_id = ?")
    @RegisterRowMapper(BookMapper.class)
    Book findBookById(String bookId);

    @SqlQuery("SELECT book_id, title FROM book")
    @RegisterRowMapper(BookMapper.class)
    List<Book> findBooks();

    @SqlUpdate("DELETE FROM book WHERE book_id = ?")
    int deleteBookById(String bookId);
}
