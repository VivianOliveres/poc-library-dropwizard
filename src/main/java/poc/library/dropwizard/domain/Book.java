package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Book {
    @JsonProperty
    UUID id;

    @JsonProperty
    String isbn;

    @JsonProperty
    String title;
}
