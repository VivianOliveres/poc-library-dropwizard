package poc.library.dropwizard.book.db;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import poc.library.dropwizard.domain.Book;

public interface BookDao {

    @SqlUpdate("insert into book (book_id, title) values (:bookId, :title)")
    int insert(@Bind("bookId") String bookId, @Bind("title") String title);

    @SqlQuery("select book_id, title from book where book_id = :bookId")
    @RegisterRowMapper(BookMapper.class)
    Book findBookById(@Bind("bookId") String bookId);

    @SqlQuery("select book_id, title from book")
    @RegisterRowMapper(BookMapper.class)
    List<Book> findBooks();

    @SqlUpdate("delete from book where book_id = :bookId")
    int deleteBookById(@Bind("bookId") String bookId);
}
