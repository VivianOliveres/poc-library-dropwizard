package poc.library.dropwizard.presentation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import poc.library.dropwizard.core.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Value
public class Books {

    @JsonProperty
    Map<UUID, Book> books;

    @JsonCreator
    public Books(@JsonProperty("books") Map<UUID, Book> books) {
        this.books = books;
    }

    public Book get(UUID id) {
        return books.get(id);
    }

    public Set<UUID> keys() {
        return books.keySet();
    }

    public static final Books of(List<Book> bookList) {
        Map<UUID, Book> books = bookList.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        return new Books(books);
    }
}
