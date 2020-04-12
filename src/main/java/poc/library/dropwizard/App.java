package poc.library.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import poc.library.dropwizard.core.booking.BookingResource;
import poc.library.dropwizard.core.booking.BookingService;
import poc.library.dropwizard.core.booking.db.BookingsRepo;
import poc.library.dropwizard.core.catalog.BookResource;
import poc.library.dropwizard.core.catalog.db.BooksRepo;
import poc.library.dropwizard.core.user.UserResource;
import poc.library.dropwizard.core.user.db.UsersRepo;

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

        BooksRepo booksRepo = jdbi.onDemand(BooksRepo.class);
        BookResource bookResource = new BookResource(booksRepo);
        environment.jersey().register(bookResource);

        UsersRepo usersRepo = jdbi.onDemand(UsersRepo.class);
        UserResource userResource = new UserResource(usersRepo);
        environment.jersey().register(userResource);

        BookingsRepo bookingsRepo = jdbi.onDemand(BookingsRepo.class);
        BookingService bookingService = new BookingService(bookingsRepo);
        BookingResource bookingResource = new BookingResource(bookingService);
        environment.jersey().register(bookingResource);
    }
}
