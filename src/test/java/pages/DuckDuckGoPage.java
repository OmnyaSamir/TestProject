package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DuckDuckGoPage {

    private final WebDriverWait wait;

    private static final String URL = "https://duckduckgo.com/";
    private final By logoLocator = By.cssSelector("section[class*='logoSection'] a[class*='desktop-logo'][class*='root']");
    private final By searchInputLocator = By.name("q");
    private final By searchTextareaLocator = By.cssSelector("textarea[name='q']");
    private final By resultLinksLocator = By.cssSelector("article[data-testid='result'] a[data-testid='result-title-a']");
    private final By resultArticlesLocator = By.cssSelector("article[data-testid='result']");
    private final By resultTitleLinkLocator = By.cssSelector("h2 a");

    public DuckDuckGoPage(WebDriverWait wait) {
        this.wait = wait;
    }

    public DuckDuckGoPage open() {
        wait.until(d -> {
            d.get(URL);
            return true;
        });
        return this;
    }

    public String getTitle() {
        return wait.until(d -> d.getTitle());
    }

    public WebElement getDisplayedLogo() {
        return wait.until(d -> d.findElements(logoLocator)
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));
    }

    public void searchUsingInput(String query) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchBox.sendKeys(query, Keys.ENTER);
    }

    public void searchUsingTextarea(String query) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextareaLocator));
        searchBox.sendKeys(query, Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultArticlesLocator));
    }

    public List<WebElement> getResultLinks() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultLinksLocator));
    }

    public String getResultTitleAt(int index) {
        List<WebElement> results = wait.until(d -> {
            List<WebElement> items = d.findElements(resultArticlesLocator);
            return items.size() > index ? items : null;
        });

        return results.get(index)
                .findElement(resultTitleLinkLocator)
                .getText()
                .trim();
    }

    public String getResultLinkAt(int index) {
        List<WebElement> links = getResultLinks();
        if (links.size() <= index) {
            return null;
        }
        return links.get(index).getAttribute("href");
    }
}
