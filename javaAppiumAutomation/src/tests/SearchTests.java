package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testCancelSearchWithFewArticles() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        int amount_of_articles = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("Find only 1 or less articles", amount_of_articles > 1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultSearch();
    }

    @Test
    public void testSearchArticleByTitleAndDescription() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
        int amount_of_articles = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("Find less than 3 articles", amount_of_articles > 3);
    }
}
