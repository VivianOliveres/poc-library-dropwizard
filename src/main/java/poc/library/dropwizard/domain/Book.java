package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @JsonProperty
    UUID id;

    @JsonProperty
    String title;

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
