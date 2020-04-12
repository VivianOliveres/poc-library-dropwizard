package poc.library.dropwizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@ExtendWith(DropwizardExtensionsSupport.class)
public abstract class AbstractIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    protected static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static final String RESOURCE_FILE_PATH = ResourceHelpers.resourceFilePath("application.yml");
    public static final DropwizardAppExtension<LibraryConfiguration> DROPWIZARD = new DropwizardAppExtension<>(App.class, RESOURCE_FILE_PATH);

    private static JdbiFactory factory;
    private static Jdbi JDBI;

    @BeforeClass
    public static void init() throws Exception {
        DROPWIZARD.before();

        factory = new JdbiFactory();
        JDBI = factory.build(DROPWIZARD.getEnvironment(), DROPWIZARD.getConfiguration().getDataSourceFactory(), "mysql-" + UUID.randomUUID());
    }

    @BeforeClass
    public static void beforeAll() throws IOException {
        logger.info("beforeAll...");

        Path path = Paths.get("src/test/resources/beforeAll.sql");
        List<String> sqlLines = Files.readAllLines(path, Charset.defaultCharset());
        logger.info("beforeAll: read {} lines from [{}]", sqlLines.size(), path);
        String sqlFile = String.join("\n", sqlLines);
        try (Handle h = JDBI.open()) {
            h.createScript(sqlFile).execute();
        }
        logger.info("beforeAll done!");
    }

    @Before
    public void before() throws IOException {
        logger.info("before...");
        Path path = Paths.get("src/test/resources/before.sql");
        List<String> sqlLines = Files.readAllLines(path, Charset.defaultCharset());
        logger.info("before: read {} lines from [{}]", sqlLines.size(), path);
        String sqlFile = String.join("\n", sqlLines);
        try (Handle h = JDBI.open()) {
            h.createScript(sqlFile).execute();
        }
        logger.info("before done!");
    }

    @After
    public void after() throws IOException {
        logger.info("after...");
        Path path = Paths.get("src/test/resources/after.sql");
        List<String> sqlLines = Files.readAllLines(path, Charset.defaultCharset());
        logger.info("after: read {} lines from [{}]", sqlLines.size(), path);
        String sqlFile = String.join("\n", sqlLines);
        try (Handle h = JDBI.open()) {
            h.createScript(sqlFile).execute();
        }
        logger.info("after done!");
    }

    public WebTarget target(String path) {
        String uri = "http://localhost:" + DROPWIZARD.getLocalPort() + path;
        return DROPWIZARD.client().target(uri);
    }

}
