package poc.library.dropwizard.adherent;

import org.junit.Test;
import poc.library.dropwizard.adherent.db.AdherentDao;
import poc.library.dropwizard.domain.Adherent;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static poc.library.dropwizard.adherent.AdherentMotherObject.*;

public class AdherentResourceTest {

    private AdherentDao dao = mock(AdherentDao.class);

    private AdherentResource resource = new AdherentResource(dao);

    @Test
    public void should_getAdherents_return_empty_when_dao_contains_no_adherents() {
        // GIVEN: dao contains nothing
        when(dao.findAdherents()).thenReturn(List.of());

        // WHEN: getAdherents
        List<Adherent> result = resource.getAdherents();

        // THEN: resource returns nothing
        assertThat(result).isEmpty();
    }

    @Test
    public void should_getAdherents_return_results_from_dao() {
        // GIVEN: dao contains 2 items
        when(dao.findAdherents()).thenReturn(List.of(MARGARET, JAMES));

        // WHEN: getAdherents
        List<Adherent> result = resource.getAdherents();

        // THEN: resource return 2 items
        assertThat(result).hasSize(2);
        assertThat(result).contains(MARGARET, JAMES);
    }

    @Test
    public void should_insertAdherent_return_ok_if_dao_has_successfully_inserted_item() {
        // GIVEN: dao insert 1 item
        when(dao.insert(eq(ERICH.getFirstName()), eq(ERICH.getFamilyName()), eq(ERICH.getBirthDate()),
                eq(ERICH.getMembershipDate()))).thenReturn(1L);

        // WHEN: insertAdherent
        Response response = resource.insertAdherent(ERICH);

        // THEN: response is CREATED
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void should_insertAdherent_return_BadRequest_if_dao_has_failed_to_insert_item() {
        // GIVEN: dao fails to insert 1 book
        when(dao.insert(eq(ERICH.getFirstName()), eq(ERICH.getFamilyName()), eq(ERICH.getBirthDate()),
                eq(ERICH.getMembershipDate()))).thenReturn(0L);

        // WHEN: insertAdherent
        Response response = resource.insertAdherent(ERICH);

        // THEN: response is BAD_REQUEST
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_deleteAdherentById_return_ok_if_dao_has_successfully_deleted_item() {
        // GIVEN: dao delete 1 item
        when(dao.deleteAdherentById(eq(VIVIAN.getUserId()))).thenReturn(1);

        // WHEN: deleteAdherent
        Response response = resource.deleteAdherent(VIVIAN.getUserId());

        // THEN: response is ok
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_deleteAdherentById_return_BadRequest_if_dao_has_failed_to_delete_item() {
        // GIVEN: dao fails to delete 1 item
        when(dao.deleteAdherentById(eq(VIVIAN.getUserId()))).thenReturn(0);

        // WHEN: deleteAdherent
        Response response = resource.deleteAdherent(VIVIAN.getUserId());

        // THEN: response is BAD_REQUEST
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_deleteAdherentByNames_return_ok_if_dao_has_successfully_deleted_item() {
        // GIVEN: dao delete 1 item
        when(dao.deleteAdherentByNames(eq(ERICH.getFirstName()), eq(ERICH.getFamilyName()))).thenReturn(1);

        // WHEN: deleteAdherent
        Response response = resource.deleteAdherent(ERICH.getFirstName(), ERICH.getFamilyName());

        // THEN: response is ok
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_deleteAdherentByNames_return_BadRequest_if_dao_has_failed_to_delete_item() {
        // GIVEN: dao fails to delete 1 item
        when(dao.deleteAdherentByNames(eq(ERICH.getFirstName()), eq(ERICH.getFamilyName()))).thenReturn(0);

        // WHEN: deleteAdherent
        Response response = resource.deleteAdherent(ERICH.getFirstName(), ERICH.getFamilyName());

        // THEN: response is BAD_REQUEST
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void should_getAdherentById_return_ok_if_dao_findAdherentById() {
        // GIVEN: dao return 1 item
        when(dao.findAdherentById(eq(MARGARET.getUserId()))).thenReturn(MARGARET);

        // WHEN: getAdherentById
        Adherent result = resource.getAdherentById(MARGARET.getUserId());

        // THEN: result is MARGARET
        assertThat(result).isEqualTo(MARGARET);
    }

    @Test
    public void should_getAdherentById_return_null_if_dao_findAdherentById_return_null() {
        // GIVEN: dao fails to delete 1 item
        when(dao.findAdherentById(eq(MARGARET.getUserId()))).thenReturn(null);

        // WHEN: getAdherentById
        Adherent result = resource.getAdherentById(MARGARET.getUserId());

        // THEN: result is null
        assertThat(result).isNull();
    }

    @Test
    public void should_getAdherentByNames_return_ok_if_dao_findAdherentByNames() {
        // GIVEN: dao return 1 item
        when(dao.findAdherentByNames(eq(MARGARET.getFirstName()), eq(MARGARET.getFamilyName()))).thenReturn(MARGARET);

        // WHEN: getAdherentByNames
        Adherent result = resource.getAdherentByNames(MARGARET.getFirstName(), MARGARET.getFamilyName());

        // THEN: result is MARGARET
        assertThat(result).isEqualTo(MARGARET);
    }

    @Test
    public void should_getAdherentByNames_return_null_if_dao_findAdherentByNames_return_null() {
        // GIVEN: dao fails to delete 1 item
        when(dao.findAdherentByNames(eq(MARGARET.getFirstName()), eq(MARGARET.getFamilyName()))).thenReturn(null);

        // WHEN: getAdherentByNames
        Adherent result = resource.getAdherentByNames(MARGARET.getFirstName(), MARGARET.getFamilyName());

        // THEN: result is null
        assertThat(result).isNull();
    }

}
