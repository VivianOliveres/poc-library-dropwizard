package poc.library.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import poc.library.dropwizard.adherent.db.AdherentDao;
import poc.library.dropwizard.book.db.BookDao;
import poc.library.dropwizard.adherent.AdherentResource;
import poc.library.dropwizard.book.BookResource;
import poc.library.dropwizard.booking.BookingResource;
import poc.library.dropwizard.booking.BookingService;
import poc.library.dropwizard.booking.db.BookingDao;

public class App extends Application<LibraryConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run("server", "src/main/resources/application.yml");
        // new App().run("server");
    }

    @Override
    public String getName() {
        return "Library";
    }

    @Override
    public void initialize(Bootstrap<LibraryConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(LibraryConfiguration config, Environment environment) {
        environment.healthChecks().register("dummyHealthChecks", new LibraryHealthCheck());

        JdbiFactory factory = new JdbiFactory();
        Jdbi jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");

        BookDao bookDao = jdbi.onDemand(BookDao.class);
        BookResource bookResource = new BookResource(bookDao);
        environment.jersey().register(bookResource);

        AdherentDao adherentDao = jdbi.onDemand(AdherentDao.class);
        AdherentResource adherentResource = new AdherentResource(adherentDao);
        environment.jersey().register(adherentResource);

        BookingDao bookingDao = jdbi.onDemand(BookingDao.class);
        BookingService bookingService = new BookingService(bookingDao);
        BookingResource bookingResource = new BookingResource(bookingService);
        environment.jersey().register(bookingResource);
    }
}
