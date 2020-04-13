package poc.library.dropwizard.core.rating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import poc.library.dropwizard.AbstractIntegrationTest;
import poc.library.dropwizard.core.domain.Rating;
import poc.library.dropwizard.core.rating.request.InsertRatingRequest;
import poc.library.dropwizard.core.rating.request.UpdateRatingRequest;

import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static poc.library.dropwizard.core.catalog.BookMotherObject.DESIGN_PATERNS;
import static poc.library.dropwizard.core.rating.RatingMotherObject.ALL_RATINGS;
import static poc.library.dropwizard.core.rating.RatingMotherObject.RATING_JAMES_DATA;
import static poc.library.dropwizard.core.user.UserMotherObject.MARGARET;

@ExtendWith(DropwizardExtensionsSupport.class)
public class RatingResourceTest extends AbstractIntegrationTest {

    @Test
    public void should_retrieve_ratings() throws JsonProcessingException {
        Response response = target("/core/rating").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        String resultAsJson = response.readEntity(String.class);
        List<Rating> result = MAPPER.readValue(resultAsJson, new TypeReference<>(){});

        assertThat(result).containsAll(ALL_RATINGS);
        assertThat(result).hasSize(ALL_RATINGS.size());
    }

    @Test
    public void should_retrieve_ratings_by_id() {
        String path = "/core/rating/" + RATING_JAMES_DATA.getRatingId();
        Response response = target(path).request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        Rating result = response.readEntity(Rating.class);

        assertThat(result).isEqualTo(RATING_JAMES_DATA);
    }

    @Test
    public void should_insert_then_update_then_delete() throws JsonProcessingException {
        // Insert
        InsertRatingRequest insertRequest = new InsertRatingRequest(MARGARET.getUserId(), DESIGN_PATERNS.getId(), 5);
        Response insertResponse = target("/core/rating").request()
                .post(entity(insertRequest, APPLICATION_JSON));

        assertThat(insertResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        Rating insertedRating = insertResponse.readEntity(Rating.class);
        assertThat(insertedRating).isNotNull();
        assertThat(insertedRating.getRatingId()).isEqualTo(ALL_RATINGS.size() + 1);
        assertThat(insertedRating.getUserId()).isEqualTo(MARGARET.getUserId());
        assertThat(insertedRating.getBookId()).isEqualTo(DESIGN_PATERNS.getId());
        assertThat(insertedRating.getRatingValue()).isEqualTo(5);

        // UPDATE
        UpdateRatingRequest updateRequest = new UpdateRatingRequest(insertedRating.getRatingId(), 4);
        Response updateResponse = target("/core/rating").request()
                .put(entity(updateRequest, APPLICATION_JSON));

        assertThat(updateResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        Rating updatedRating = updateResponse.readEntity(Rating.class);
        assertThat(updatedRating).isNotNull();
        assertThat(updatedRating.getRatingId()).isEqualTo(insertedRating.getRatingId());
        assertThat(updatedRating.getUserId()).isEqualTo(MARGARET.getUserId());
        assertThat(updatedRating.getBookId()).isEqualTo(DESIGN_PATERNS.getId());
        assertThat(updatedRating.getRatingValue()).isEqualTo(4);

        // DELETE
        Rating ratingToDelete = insertedRating.withRatingValue(4);
        Response deleteResponse = target("/core/rating/" + updatedRating.getRatingId()).request().delete();

        assertThat(deleteResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        Rating deletedRating = deleteResponse.readEntity(Rating.class);
        assertThat(deletedRating).isNotNull();
        assertThat(deletedRating.getRatingId()).isEqualTo(insertedRating.getRatingId());
        assertThat(deletedRating.getUserId()).isEqualTo(MARGARET.getUserId());
        assertThat(deletedRating.getBookId()).isEqualTo(DESIGN_PATERNS.getId());
        assertThat(deletedRating.getRatingValue()).isEqualTo(4);
    }

}
