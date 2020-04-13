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
import poc.library.dropwizard.core.catalog.BookService;
import poc.library.dropwizard.core.catalog.db.BooksRepo;
import poc.library.dropwizard.core.rating.RatingResource;
import poc.library.dropwizard.core.rating.RatingService;
import poc.library.dropwizard.core.rating.db.RatingsRepo;
import poc.library.dropwizard.core.user.UserResource;
import poc.library.dropwizard.core.user.UserService;
import poc.library.dropwizard.core.user.db.UsersRepo;
import poc.library.dropwizard.presentation.PresentationUserResource;
import poc.library.dropwizard.presentation.PresentationUserService;

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
        BookService bookService = new BookService(booksRepo);
        BookResource bookResource = new BookResource(bookService);
        environment.jersey().register(bookResource);

        UsersRepo usersRepo = jdbi.onDemand(UsersRepo.class);
        UserService userService = new UserService(usersRepo);
        UserResource userResource = new UserResource(userService);
        environment.jersey().register(userResource);

        BookingsRepo bookingsRepo = jdbi.onDemand(BookingsRepo.class);
        BookingService bookingService = new BookingService(bookingsRepo);
        BookingResource bookingResource = new BookingResource(bookingService);
        environment.jersey().register(bookingResource);

        RatingsRepo ratingsrepo = jdbi.onDemand(RatingsRepo.class);
        RatingService ratingService = new RatingService(ratingsrepo);
        RatingResource ratingResource = new RatingResource(ratingService);
        environment.jersey().register(ratingResource);

        PresentationUserService presentationService = new PresentationUserService(userService, bookingService, ratingService, bookService);
        PresentationUserResource presentationResource = new PresentationUserResource(presentationService);
        environment.jersey().register(presentationResource);
    }
}
