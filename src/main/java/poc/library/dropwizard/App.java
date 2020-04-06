package poc.library.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import poc.library.dropwizard.adherent.AdherentResource;
import poc.library.dropwizard.adherent.db.AdherentRepo;
import poc.library.dropwizard.book.BookResource;
import poc.library.dropwizard.book.db.BookRepo;
import poc.library.dropwizard.booking.BookingResource;
import poc.library.dropwizard.booking.BookingService;
import poc.library.dropwizard.booking.db.BookingRepo;

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

        BookRepo bookRepo = jdbi.onDemand(BookRepo.class);
        BookResource bookResource = new BookResource(bookRepo);
        environment.jersey().register(bookResource);

        AdherentRepo adherentRepo = jdbi.onDemand(AdherentRepo.class);
        AdherentResource adherentResource = new AdherentResource(adherentRepo);
        environment.jersey().register(adherentResource);

        BookingRepo bookingRepo = jdbi.onDemand(BookingRepo.class);
        BookingService bookingService = new BookingService(bookingRepo);
        BookingResource bookingResource = new BookingResource(bookingService);
        environment.jersey().register(bookingResource);
    }
}
