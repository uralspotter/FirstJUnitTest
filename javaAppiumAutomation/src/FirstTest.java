import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    public AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\ural\\Desktop\\ТУСУР\\software testing\\Автоматизация моб тест\\javaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testWikiSearch() {

        String search_query = "Java";

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find the search field"
        );

        WebElement input_search_field = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find input search"
        );

        String expected_title = "Search…";
        checkSearch(input_search_field, "We see unexpected title", expected_title);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_query,
                "Cannot find input search",
                5
        );

        List<WebElement> result_list = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "List is empty",
                15
        );

        Assert.assertTrue("In the results list less than two elements", result_list.size() > 1);
        for(int i = 0; i < result_list.size(); i++) {
            Assert.assertTrue(
                    "Not all of the results contain " + search_query,
                    result_list.get(i).getAttribute("text").toLowerCase().contains(search_query.toLowerCase())
            );
        }

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find input search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "List not empty",
                15
        );
    }

    @Test
    public void testArticleList() {

        By java_article_xpath = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
        By java_article_title_xpath = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']");
        By appium_article_xpath = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']");
        By appium_into_folder_xpath = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']");

        String search_query = "Java";
        String java_article_title = "Java (programming language)";
        String search_query_2 = "Appium";
        String appium_article_title = "Appium";
        String folder_name = "My articles";
        String error_titles_no_equals = "Expected and actual titles are not equals";

        addArticleToList(search_query, java_article_title, java_article_xpath, folder_name);
        addArticleToList(search_query_2, appium_article_title, appium_article_xpath, folder_name);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + folder_name + "']"),
                "Cannot find the article folder",
                15
        );

        swipeElementToLeft(
                appium_into_folder_xpath,
                "Cannot find saved article"
        );

        waitForElementAndClick(
                java_article_title_xpath,
                "Another article was deleted"
        );

        String article_title = waitForElementAndGetAttribute(
                java_article_title_xpath,
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(error_titles_no_equals, java_article_title, article_title);
    }

    @Test
    public void testGetTitleNow() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find element Search",
                5);

        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                search_line,
                "Cannot find element Search",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15);

        assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find title or title no present",
                "Object-oriented programming language"
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message) {
        return waitForElementsPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndClick(By by, String error_message) {
        return waitForElementAndClick(by, error_message, 5);
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 5);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private boolean waitForElementNotPresent(By by, String error_message) {
        return waitForElementNotPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement waitForElementAndClear(By by, String error_message) {
        return waitForElementAndClear(by, error_message, 5);
    }

    private void checkSearch(WebElement input_title, String error_message, String expected_title) {
        String title_search_field = input_title.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_title,
                title_search_field
        );
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while(driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementsPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);

        action
                .press(right_x, middle_y)
                .waitAction(500)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private void addArticleToList(String search_query, String article_title_exp, By article_xpath, String folder_name) {

        By search_field_xpath = By.xpath("//*[contains(@text,'Search Wikipedia')]");
        By button_options_xpath = By.xpath("//android.widget.ImageView[@content-desc='More options']");
        By button_add_to_list_xpath = By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']");
        By got_it_button_xpath = By.id("org.wikipedia:id/onboarding_button");
        By ok_button_xpath = By.xpath("//*[@text='OK']");

        String error_find_field = "Cannot find the search field";
        String no_article_error = "Cannot find article by the request: ";
        String error_no_button_options = "Cannot find button of the options menu";
        String error_no_button_add_to_list = "Cannot find button add to list";
        String no_got_it_button = "Cannot find 'Got it' tip over lay";
        String no_ok_button = "Cannot press ok button";

        waitForElementAndClick(
                search_field_xpath,
                error_find_field
        );

        waitForElementAndSendKeys(
                search_field_xpath,
                search_query,
                error_find_field
        );

        waitForElementAndClick(
                article_xpath,
                no_article_error + search_query
        );


        waitForElementAndClick(
                button_options_xpath,
                error_no_button_options
        );

        waitForElementAndClick(
                button_add_to_list_xpath,
                error_no_button_add_to_list
        );

        if(getAmountOfElements(got_it_button_xpath) == 1) {
            waitForElementAndClick(
                    got_it_button_xpath,
                    no_got_it_button
            );
        }

        if (getAmountOfElements(By.id("org.wikipedia:id/text_input")) == 1) {
            waitForElementAndClear(
                    By.id("org.wikipedia:id/text_input"),
                    "Cannot find input to set name of article folder"
            );

            waitForElementAndSendKeys(
                    By.id("org.wikipedia:id/text_input"),
                    folder_name,
                    "Cannot put text into article folder input"
            );

            waitForElementAndClick(
                    ok_button_xpath,
                    no_ok_button
            );
        } else {
            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + folder_name + "']"),
                    "Cannot add article to list"
            );
        }

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );
    }

    private void assertElementPresent(By by, String error_message, String article_title) {
        List<WebElement> element = driver.findElements(by);
        if (element.size() == 0) {
            throw new AssertionError(error_message);
        }
        String title = element.get(0).getAttribute("text");
        Assert.assertEquals(error_message, article_title, title);
    }
}
