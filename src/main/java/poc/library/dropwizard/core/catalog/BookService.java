package poc.library.dropwizard.core.catalog;

import poc.library.dropwizard.core.catalog.db.BooksRepo;
import poc.library.dropwizard.domain.Book;
import poc.library.dropwizard.utils.Try;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookService {

    private final BooksRepo repo;

    public BookService(BooksRepo repo) {
        this.repo = repo;
    }

    public Try<Book> getBook(UUID id) {
        Optional<Book> maybeBook = repo.findBookById(id.toString());
        if (maybeBook.isEmpty()) {
            return Try.right("Can not find book[" + id + "]");
        }

        return Try.left(maybeBook);
    }

    public List<Book> getBooks() {
        return repo.findBooks();
    }

    public Try<Book> insertBook(Book book) {
        int rowsInserted = repo.insert(book.getId().toString(), book.getTitle());
        if (rowsInserted <= 0) {
            return Try.right("Can not insert Book: " + book);
        }

        Optional<Book> maybeBook = repo.findBookById(book.getId().toString());
        return Try.left(maybeBook);
    }
}
