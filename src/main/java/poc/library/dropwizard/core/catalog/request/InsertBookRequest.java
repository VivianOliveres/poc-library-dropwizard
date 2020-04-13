package poc.library.dropwizard.core.catalog.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class InsertBookRequest {

    @JsonProperty
    String title;

    @JsonCreator
    public InsertBookRequest(@JsonProperty("title") String title) {
        this.title = title;
    }
}
