package poc.library.dropwizard.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
public class Book {
    @JsonProperty UUID id;

    @JsonProperty String title;

    @JsonCreator
    public Book(@JsonProperty("id") String id, @JsonProperty("title") String title) {
        this.id = UUID.fromString(id);
        this.title = title;
    }
}
