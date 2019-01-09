package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class AndroidMyListPageObject extends MyListPageObject {

    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']";
        ARTICLE_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";
    }

    public AndroidMyListPageObject(AppiumDriver driver) {
        super(driver);
    }
}
