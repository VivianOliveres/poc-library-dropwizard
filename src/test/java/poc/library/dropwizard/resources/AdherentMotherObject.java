package poc.library.dropwizard.resources;

import lombok.experimental.UtilityClass;
import poc.library.dropwizard.domain.Adherent;

import java.time.LocalDate;

@UtilityClass
public class AdherentMotherObject {

    public static final Adherent VIVIAN = new Adherent(1, "Vivian", "Oliveres", LocalDate.of(1984, 11, 17),
            LocalDate.of(2001, 7, 25));
    public static final Adherent ERICH = new Adherent(2, "Erich", "Gamma", LocalDate.of(1961, 3, 13),
            LocalDate.of(1980, 8, 26));
    public static final Adherent JAMES = new Adherent(3, "James", "Gosling", LocalDate.of(1955, 5, 19),
            LocalDate.of(1978, 2, 20));
    public static final Adherent MARGARET = new Adherent(4, "Margaret", "Heafield", LocalDate.of(1936, 8, 17),
            LocalDate.of(1955, 7, 25));

}
