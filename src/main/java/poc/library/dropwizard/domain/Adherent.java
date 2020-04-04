package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adherent {

    @JsonProperty
    long userId;

    @JsonProperty
    String firstName;

    @JsonProperty
    String familyName;

    @JsonProperty
    LocalDate birthDate;

    @JsonProperty
    LocalDate membershipDate;
}
