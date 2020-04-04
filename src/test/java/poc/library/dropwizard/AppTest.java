package poc.library.dropwizard;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppTest {
    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
