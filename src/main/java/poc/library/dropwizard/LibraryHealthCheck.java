package poc.library.dropwizard;

import com.codahale.metrics.health.HealthCheck;

public class LibraryHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
