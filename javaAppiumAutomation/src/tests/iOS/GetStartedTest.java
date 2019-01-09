package tests.iOS;

import lib.iOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends iOSTestCase {

    @Test
    public void testPassThroughWellcome() {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();
        WelcomePage.waitForNewWayToExploreText();
        WelcomePage.clickNextButton();
        WelcomePage.waitForAddOrEditPreferredText();
        WelcomePage.clickNextButton();
        WelcomePage.waitForLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}
