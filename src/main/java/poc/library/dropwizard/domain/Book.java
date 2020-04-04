package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

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
