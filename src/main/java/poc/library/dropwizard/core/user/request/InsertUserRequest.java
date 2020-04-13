package poc.library.dropwizard.core.user.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;

@Value
public class InsertUserRequest {

    @JsonProperty
    String firstName;

    @JsonProperty
    String familyName;

    @JsonProperty
    LocalDate birthDate;

    @JsonProperty
    LocalDate membershipDate;

    @JsonCreator
    public InsertUserRequest(@JsonProperty("firstName") String firstName,
                             @JsonProperty("familyName") String familyName,
                             @JsonProperty("birthDate") LocalDate birthDate,
                             @JsonProperty("membershipDate") LocalDate membershipDate) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.membershipDate = membershipDate;
    }
}
