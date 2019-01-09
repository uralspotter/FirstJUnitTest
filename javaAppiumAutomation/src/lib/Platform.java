package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class Platform {

    private static final String PLATFORM_IOS = "IOS";
    private static final String PLATFORM_ANDROID = "android";
    private static final String AppiumUrl = "http://127.0.0.1:4723/wd/hub/";

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception {
        URL URL = new URL(AppiumUrl);
        if(isAndroid()) {
            return new AndroidDriver(new URL(AppiumUrl), this.getAndroidDesiredCapabilities());
        } else if(isIOS()) {
            return new IOSDriver(new URL(AppiumUrl), this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detect type of Driver. Platform value: " + this.getPlatformVar());
        }
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS() {
        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\ural\\Desktop\\ТУСУР\\software testing\\Автоматизация моб тест\\Листинги с лекции\\javaAppiumAutomation\\apks\\org.wikipedia.apk");
        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE 11.3");
        capabilities.setCapability("platformVersion", "11.3");
        capabilities.setCapability("app", "/Users/qa/Desktop/javaAppiumAutomation/apks/wikipedia.app");
        return capabilities;
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }
}
