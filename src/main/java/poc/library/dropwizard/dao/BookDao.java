package poc.library.dropwizard.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookDao {

    @SqlUpdate("insert into book (book_id, isbn, title) values (:bookId, :isbn, :title)")
    void insert(@Bind("bookId") String bookId, @Bind("name") String isbn, @Bind("title") String title);

    @SqlQuery("select book_id, isbn, title from book where book_id = :bookId")
    @RegisterRowMapper(BookMapper.class)
    Book findBookById(@Bind("bookId") String bookId);

    @SqlQuery("select book_id, isbn, title from book")
    @RegisterRowMapper(BookMapper.class)
    List<Book> findBooks();
}
