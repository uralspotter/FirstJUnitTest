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
}
