package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_ADD_TO_MY_LIST = "//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTOM = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        MY_LIST_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{NAME_OF_FOLDER}']";

    /* TEMPLATE METHODS */
    public String getMyListFolderName(String substring) {
        return MY_LIST_NAME_TPL.replace("{NAME_OF_FOLDER}", substring);
    }
    /* TEMPLATE METHODS */
    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options"
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST),
                "Cannot find option to add article to reading list"
        );

        if(this.getAmountOfElements(By.id(ADD_TO_MY_LIST_OVERLAY)) == 1) {
            this.waitForElementAndClick(
                    By.id(ADD_TO_MY_LIST_OVERLAY),
                    "Cannot find 'Got it' tip over lay"
            );
        }

        if(this.getAmountOfElements(By.id(MY_LIST_NAME_INPUT)) == 1) {
            this.waitForElementAndClear(
                    By.id(MY_LIST_NAME_INPUT),
                    "Cannot find input to set name of article folder"
            );

            this.waitForElementAndSendKeys(
                    By.id(MY_LIST_NAME_INPUT),
                    name_of_folder,
                    "Cannot put text into article folder input"
            );

            this.waitForElementAndClick(
                    By.xpath(MY_LIST_OK_BUTTOM),
                    "Cannot press ok button"
            );
        } else {
            String name_user_folder_xpath = getMyListFolderName(name_of_folder);
            this.waitForElementAndClick(
                    By.xpath(name_user_folder_xpath),
                    "Cannot add article to list"
            );
        }
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link"
        );
    }

    public void assertElementPresent(String article_title) {
        String error_message = "Cannot find title or title no present";
        List<WebElement> element = driver.findElements(By.id(TITLE));
        if (element.size() == 0) {
            throw new AssertionError(error_message);
        }
        String title = element.get(0).getAttribute("text");
        assertEquals(error_message, article_title, title);
    }
}
