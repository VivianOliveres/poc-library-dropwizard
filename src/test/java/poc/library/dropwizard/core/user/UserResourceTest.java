package poc.library.dropwizard.core.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import poc.library.dropwizard.AbstractIntegrationTest;
import poc.library.dropwizard.core.domain.User;
import poc.library.dropwizard.core.user.request.InsertUserRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static poc.library.dropwizard.core.user.UserMotherObject.ALL_USERS;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UserResourceTest extends AbstractIntegrationTest {

    @Test
    public void should_retrieve_all_users() throws JsonProcessingException {
        Response response = target("/core/user").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        String resultAsJson = response.readEntity(String.class);
        List<User> result = MAPPER.readValue(resultAsJson, new TypeReference<>(){});

        assertThat(result).containsAll(ALL_USERS);
        assertThat(result).hasSize(ALL_USERS.size());
    }

    @Test
    public void should_retrieve_one_user_by_id() throws JsonProcessingException {
        for (User user : ALL_USERS) {
            String path = "/core/user/" + user.getUserId();
            Response response = target(path).request().get();

            assertThat(response.getStatus()).isEqualTo(200);

            User result = response.readEntity(User.class);
            assertThat(result).isEqualTo(user);
        }
    }

    @Test
    public void should_retrieve_one_user_by_name() throws JsonProcessingException {
        for (User user : ALL_USERS) {
            String path = "/core/user/" + user.getFirstName() + "/" + user.getFamilyName();
            Response response = target(path).request().get();

            assertThat(response.getStatus()).isEqualTo(200);

            User result = response.readEntity(User.class);
            assertThat(result).isEqualTo(user);
        }
    }

    @Test
    public void should_insert_one_user_then_delete_it() throws JsonProcessingException {
        // Insert user
        InsertUserRequest insertRequest = new InsertUserRequest("Grace", "Hopper", LocalDate.of(1906, 12, 9), LocalDate.of(1912, 5, 12));
        Response insertResponse = target("/core/user").request()
                .post(Entity.entity(insertRequest, APPLICATION_JSON));

        assertThat(insertResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        // Users are the same except for UserId
        User insertEntity = insertResponse.readEntity(User.class);
        assertThat(insertEntity.getUserId()).isEqualTo(ALL_USERS.size() + 1);
        assertThat(insertEntity.getFirstName()).isEqualTo(insertRequest.getFirstName());
        assertThat(insertEntity.getFamilyName()).isEqualTo(insertRequest.getFamilyName());
        assertThat(insertEntity.getBirthDate()).isEqualTo(insertRequest.getBirthDate());
        assertThat(insertEntity.getMembershipDate()).isEqualTo(insertRequest.getMembershipDate());

        // THEN: delete it
        Response deleteResponse = target("/core/user/" + insertEntity.getUserId()).request().delete();
        assertThat(deleteResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        User deletedUser = deleteResponse.readEntity(User.class);
        assertThat(deletedUser.getUserId()).isEqualTo(insertEntity.getUserId());
        assertThat(deletedUser.getFirstName()).isEqualTo(insertRequest.getFirstName());
        assertThat(deletedUser.getFamilyName()).isEqualTo(insertRequest.getFamilyName());
        assertThat(deletedUser.getBirthDate()).isEqualTo(insertRequest.getBirthDate());
        assertThat(deletedUser.getMembershipDate()).isEqualTo(insertRequest.getMembershipDate());

    }
}
