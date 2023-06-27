package de.ait.gethelp.inna;

import de.ait.gethelp.controllers.RootController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RootTests extends TestBase {
    /*
     * method verifies that the redirect()
     * method of the controller returns the expected redirect URL.
     * We assert that the returned value also.
     */


    @Test
    void testRedirect() {
        String redirectUrl = "home";
        RootController rootController = new RootController();

        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("app.root-redirect-url", redirectUrl);

        ReflectionTestUtils.setField(rootController, "redirectUrl", redirectUrl);
        ReflectionTestUtils.setField(rootController, "environment", environment);

        String result = rootController.redirect();

        assertEquals("redirect:" + redirectUrl, result);
    }



}
