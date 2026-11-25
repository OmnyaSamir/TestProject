package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DuckDuckGoPage;
import utils.TestDataReader;

import java.util.List;

public class DuckDuckGoTest extends BaseTest {

    @Test
    public void title() {
        DuckDuckGoPage page = new DuckDuckGoPage(wait).open();
        Assert.assertEquals(page.getTitle(), "Google");
    }

    @Test
    public void testLogo() {
        DuckDuckGoPage page = new DuckDuckGoPage(wait).open();
        WebElement logo = page.getDisplayedLogo();

        Assert.assertNotNull(logo, "Logo is not displayed");
        Assert.assertTrue(logo.isDisplayed(), "Logo is not displayed");
        Assert.assertTrue(
                logo.getSize().getWidth() > 0 && logo.getSize().getHeight() > 0,
                "Logo has zero size"
        );
    }

    @Test
    public void verifyFirstSearchResultLink() {
        TestDataReader data = new TestDataReader("search-data.json");
        String query = data.getString("searchQuery");
        String expectedUrl = data.getString("expectedFirstResultUrl");

        DuckDuckGoPage page = new DuckDuckGoPage(wait).open();
        page.searchUsingInput(query);

        List<WebElement> resultLinks = page.getResultLinks();
        Assert.assertFalse(resultLinks.isEmpty(), "Expected at least one search result.");

        String firstResultUrl = resultLinks.get(0).getAttribute("href");
        Assert.assertEquals(firstResultUrl, expectedUrl);
    }

    @Test
    public void testFourthResultTitle() {
        DuckDuckGoPage page = new DuckDuckGoPage(wait).open();
        page.searchUsingInput("TestNG");

        String actualTitle = page.getResultTitleAt(3);
        Assert.assertEquals(actualTitle, "TestNG Tutorial", "Fourth result text mismatch");
    }

    @Test
    public void testSearchCucumberIO() {
        DuckDuckGoPage page = new DuckDuckGoPage(wait).open();
        page.searchUsingTextarea("Cucumber IO");

        String secondResultUrl = page.getResultLinkAt(1);
        Assert.assertNotNull(secondResultUrl, "Second search result link is missing.");
        Assert.assertTrue(
                secondResultUrl.contains("https://www.linkedin.com"),
                "Expected second result link to contain 'https://www.linkedin.com', but got: " + secondResultUrl
        );
    }
}
