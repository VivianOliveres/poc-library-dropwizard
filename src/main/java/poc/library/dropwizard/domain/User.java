package poc.library.dropwizard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@Value
public class User {

    @With
    @JsonProperty long userId;

    @JsonProperty String firstName;

    @JsonProperty String familyName;

    @JsonProperty LocalDate birthDate;

    @JsonProperty LocalDate membershipDate;

    @JsonCreator
    public User(@JsonProperty("userId") long userId,
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
