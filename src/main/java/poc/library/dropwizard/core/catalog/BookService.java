package poc.library.dropwizard.core.catalog;

import poc.library.dropwizard.core.catalog.db.BooksRepo;
import poc.library.dropwizard.core.catalog.request.InsertBookRequest;
import poc.library.dropwizard.core.domain.Book;
import poc.library.dropwizard.utils.Try;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Try<Book> insertBook(@NotNull InsertBookRequest request) {
        UUID bookId = UUID.randomUUID();
        int rowsInserted = repo.insert(bookId.toString(), request.getTitle());
        if (rowsInserted <= 0) {
            return Try.right("Can not insert Book: " + request);
        }

        Optional<Book> maybeBook = repo.findBookById(bookId.toString());
        return Try.left(maybeBook);
    }

    public List<Book> getBooks(Set<UUID> books) {
        List<String> bookIds = books.stream().map(UUID::toString).collect(Collectors.toList());
        return repo.findBookByIds(bookIds);
    }
}
