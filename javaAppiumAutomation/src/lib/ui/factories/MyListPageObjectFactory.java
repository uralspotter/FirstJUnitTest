package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListPageObject;
import lib.ui.android.AndroidMyListPageObject;
import lib.ui.ios.IOSMyListPageObject;

public class MyListPageObjectFactory {

    public static MyListPageObject get(AppiumDriver driver) {
        if(Platform.getInstance().isAndroid()) {
            return new AndroidMyListPageObject(driver);
        } else {
            return new IOSMyListPageObject(driver);
        }
    }
}
