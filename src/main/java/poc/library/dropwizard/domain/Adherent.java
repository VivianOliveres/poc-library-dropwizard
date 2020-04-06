package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Value;

@Value
public class Adherent {

    @JsonProperty long userId;

    @JsonProperty String firstName;

    @JsonProperty String familyName;

    @JsonProperty LocalDate birthDate;

    @JsonProperty LocalDate membershipDate;

    @JsonCreator
    public Adherent(
            @JsonProperty("userId") long userId,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("familyName") String familyName,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("membershipDate") LocalDate membershipDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.membershipDate = membershipDate;
    }
}
