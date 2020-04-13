package poc.library.dropwizard.core.user;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.core.domain.User;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class UserMotherObject {

    public static final User VIVIAN =
            new User(1, "Vivian", "Oliveres", LocalDate.of(1984, 11, 17), LocalDate.of(2001, 7, 25));
    public static final User ERICH =
            new User(2, "Erich", "Gamma", LocalDate.of(1961, 3, 13), LocalDate.of(1980, 8, 26));
    public static final User JAMES =
            new User(3, "James", "Gosling", LocalDate.of(1955, 5, 19), LocalDate.of(1978, 2, 20));
    public static final User MARGARET = new User(4,
                                        "Margaret",
                                       "Heafield",
                                                 LocalDate.of(1936, 8, 17),
                                                 LocalDate.of(1955, 7, 25));

    public static final List<User> ALL_USERS = List.of(VIVIAN, ERICH, JAMES, MARGARET);
}
