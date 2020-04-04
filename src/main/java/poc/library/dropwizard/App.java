package poc.library.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import poc.library.dropwizard.resources.BookResource;

public class App extends Application<LibraryConfiguration> {

    public static void main(String[] args) throws Exception {
//        new App().run("server", "application.yml");
        new App().run("server");
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
    public void run(LibraryConfiguration configuration, Environment environment) {
        BookResource resource = new BookResource();
        environment.jersey().register(resource);

        environment.healthChecks().register("dummyHealthChecks", new LibraryHealthCheck());

    }
}
