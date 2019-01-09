package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LIST_LINK;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyList() {
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Cannot find navigation button to My list"
        );
    }
}
