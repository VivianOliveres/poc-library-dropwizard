package poc.library.dropwizard.core.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import poc.library.dropwizard.AbstractIntegrationTest;
import poc.library.dropwizard.core.booking.request.InsertBookingRequest;
import poc.library.dropwizard.core.booking.request.UpdateBookingRequest;
import poc.library.dropwizard.core.domain.Booking;
import poc.library.dropwizard.core.user.UserMotherObject;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static poc.library.dropwizard.core.booking.BookingMotherObject.*;
import static poc.library.dropwizard.core.catalog.BookMotherObject.CONCURRENCY;
import static poc.library.dropwizard.core.user.UserMotherObject.MARGARET;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BookingResourceTest extends AbstractIntegrationTest {

    @Test
    public void should_retrieve_active_bookings_by_userId() throws JsonProcessingException {
        String path = "/core/booking/" + UserMotherObject.VIVIAN.getUserId();
        Response response = target(path).request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        String resultAsJson = response.readEntity(String.class);
        List<Booking> result = MAPPER.readValue(resultAsJson, new TypeReference<>(){});

        assertThat(result).contains(BOOKING_VIVIAN_DATA, BOOKING_VIVIAN_SPARK);
        assertThat(result).hasSize(2);
    }

    @Test
    public void should_retrieve_inactive_bookings_by_userId() throws JsonProcessingException {
        String path = "/core/booking/" + UserMotherObject.JAMES.getUserId() + "?isActive=false";
        Response response = target(path).request().get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        String resultAsJson = response.readEntity(String.class);
        List<Booking> result = MAPPER.readValue(resultAsJson, new TypeReference<>(){});

        assertThat(result).contains(BOOKING_JAMES_SCALA);
        assertThat(result).hasSize(1);
    }

    @Test
    public void should_insert_booking_then_update_it() {
        // WHEN: insert a booking
        InsertBookingRequest insertRequest = new InsertBookingRequest(MARGARET.getUserId(), CONCURRENCY.getId(), LocalDate.now());
        Response insertResponse = target("/core/booking").request()
                .post(entity(insertRequest, APPLICATION_JSON));

        assertThat(insertResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        // THEN: booking is inserted (bookingId could be different)
        Booking insertResult = insertResponse.readEntity(Booking.class);
        assertThat(insertResult.getUserId()).isEqualTo(MARGARET.getUserId());
        assertThat(insertResult.getBookId()).isEqualTo(CONCURRENCY.getId());
        assertThat(insertResult.getBookingDate()).isEqualTo(insertRequest.getBookingDate());
        assertThat(insertResult.getReturnedDate()).isEmpty();

        // WHEN: booking is updated
        UpdateBookingRequest updateRequest = new UpdateBookingRequest(insertResult.getBookingId(), LocalDate.now().plusDays(1));
        Response updateResponse = target("/core/booking").request()
                .put(entity(updateRequest, APPLICATION_JSON));
        assertThat(updateResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        // THEN: booking is updated
        Booking updateResult = updateResponse.readEntity(Booking.class);
        assertThat(updateResult.getUserId()).isEqualTo(MARGARET.getUserId());
        assertThat(updateResult.getBookId()).isEqualTo(CONCURRENCY.getId());
        assertThat(updateResult.getBookingDate()).isEqualTo(insertRequest.getBookingDate());
        assertThat(updateResult.getReturnedDate()).isEqualTo(Optional.of(updateRequest.getReturnedDate()));
    }

}
