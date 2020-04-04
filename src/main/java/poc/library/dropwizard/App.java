package poc.library.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import poc.library.dropwizard.dao.BookDao;
import poc.library.dropwizard.resources.BookResource;

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
        BookResource resource = new BookResource(bookDao);
        environment.jersey().register(resource);
    }
}
