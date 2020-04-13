package poc.library.dropwizard.presentation;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import poc.library.dropwizard.AbstractIntegrationTest;
import poc.library.dropwizard.presentation.domain.UserWithBookings;
import poc.library.dropwizard.presentation.domain.UserWithRatings;
import poc.library.dropwizard.presentation.domain.UserWithRatingsAndBookings;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static poc.library.dropwizard.core.user.UserMotherObject.*;
import static poc.library.dropwizard.presentation.PresentationUserMotherObject.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PresentationUserResourceTest extends AbstractIntegrationTest {

    @Test
    public void should_retrieve_ratings() {
        Response response = target("/presentation/rating/" + JAMES.getUserId()).request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        UserWithRatings result = response.readEntity(UserWithRatings.class);
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(JAMES);
        assertThat(result.getRatings()).contains(RATINGWITHBOOK_JAMES_DATA, RATINGWITHBOOK_JAMES_SCALA);
        assertThat(result.getRatings()).hasSize(2);
    }

    @Test
    public void should_retrieve_bookings() {
        Response response = target("/presentation/booking/" + VIVIAN.getUserId()).request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        UserWithBookings result = response.readEntity(UserWithBookings.class);
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(VIVIAN);
        assertThat(result.getBookings()).contains(BOOKINGWITHBOOK_VIVIAN_DATA, BOOKINGWITHBOOK_VIVIAN_SPARK);
        assertThat(result.getBookings()).hasSize(2);
    }

    @Test
    public void should_retrieve_user() {
        Response response = target("/presentation/user/" + ERICH.getUserId()).request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        UserWithRatingsAndBookings result = response.readEntity(UserWithRatingsAndBookings.class);
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(ERICH);
        assertThat(result.getRatings()).contains(RATINGWITHBOOK_ERICH_CONCURRENCY);
        assertThat(result.getRatings()).hasSize(1);
        assertThat(result.getBookings()).contains(BOOKINGWITHBOOK_ERICH_SCALA);
        assertThat(result.getBookings()).hasSize(1);
    }

}
