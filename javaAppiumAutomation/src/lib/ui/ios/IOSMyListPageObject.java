package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class IOSMyListPageObject extends MyListPageObject {

    static {
        ARTICLE_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@text,'{TITLE}')]";
    }

    public IOSMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
