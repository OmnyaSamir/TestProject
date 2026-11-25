
package testPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.lang.reflect.Method;

public class TestClass {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp(Method method) {

        if (method.getName().equals("testFourthResultTitle")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void title() {
        driver.get("https://duckduckgo.com/");

        Assert.assertEquals(
                driver.getTitle(),
                "Google"
        );
    }

    @Test
    public void testLogo() {
        driver.get("https://duckduckgo.com/");

        WebElement logo = wait.until(d -> d.findElements(
                        By.cssSelector("section[class*='logoSection'] a[class*='desktop-logo'][class*='root']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));

        Assert.assertTrue(
                logo.isDisplayed(),
                "Logo is not displayed"
        );

        Assert.assertTrue(
                logo.getSize().getWidth() > 0 &&
                        logo.getSize().getHeight() > 0,
                "Logo has zero size"
        );
    }

    @Test
    public void verifyFirstSearchResultLink() {
        driver.get("https://duckduckgo.com/");

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("q")
                )
        );

        searchBox.sendKeys("Selenium WebDriver", Keys.ENTER);

        List<WebElement> resultLinks = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector("article[data-testid='result'] a[data-testid='result-title-a']")
                )
        );

        Assert.assertFalse(
                resultLinks.isEmpty(),
                "Expected at least one search result."
        );

        String firstResultUrl = resultLinks.get(0).getAttribute("href");

        Assert.assertEquals(
                firstResultUrl,
                "https://www.selenium.dev/documentation/webdriver/"
        );
    }

    @Test
    public void testFourthResultTitle() {
        driver.get("https://duckduckgo.com/");

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name='q']")
                )
        );

        searchBox.sendKeys("TestNG", Keys.ENTER);

        List<WebElement> results = wait.until(d -> {
            List<WebElement> items = d.findElements(
                    By.cssSelector("article[data-testid='result']")
            );

            return items.size() >= 4 ? items : null;
        });

        String actualTitle = results.get(3)
                .findElement(By.cssSelector("h2 a"))
                .getText()
                .trim();

        Assert.assertEquals(
                actualTitle,
                "TestNG Tutorial",
                "Fourth result text mismatch"
        );
    }

    @Test
    public void testSearchCucumberIO() {

        driver.get("https://duckduckgo.com/");


        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("textarea[name='q']")));
        searchBox.sendKeys("Cucumber IO", Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("article[data-testid='result']")));
    }
}
