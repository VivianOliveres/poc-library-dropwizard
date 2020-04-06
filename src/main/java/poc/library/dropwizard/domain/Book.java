package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;

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
