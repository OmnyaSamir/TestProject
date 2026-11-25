
package testPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
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


    @Test
    public void testCheckboxes() {

        driver.get("http://the-internet.herokuapp.com/checkboxes");



        WebElement checkbox1 = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//input[@type='checkbox'])[1]")));
        checkbox1.click();

        WebElement checkbox2 = driver.findElement(
                By.xpath("(//input[@type='checkbox'])[2]"));

        Assert.assertTrue(checkbox1.isSelected(), "Checkbox 1 is not checked");
        Assert.assertTrue(checkbox2.isSelected(), "Checkbox 2 is not checked");
    }


    @Test
    public void testCountryForErnstHandel() {

        driver.get("https://www.w3schools.com/html/html_tables.asp");
        WebElement country = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//table[@id='customers']//tr[td[normalize-space()='Ernst Handel']]/td[3]")));

        Assert.assertEquals(country.getText().trim(), "Austria", "Country for Ernst Handel is incorrect");
    }

    @Test
    public void testFileUpload() {

        driver.get("http://the-internet.herokuapp.com/upload");

        File file = new File("C:\\Users\\Asus\\Downloads\\small-image.png");
        Assert.assertTrue(file.exists(), "File not found: " + file.getAbsolutePath());

        String fileName = file.getName();

        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("file-upload")));
        fileInput.sendKeys(file.getAbsolutePath());

        driver.findElement(By.id("file-submit")).click();

        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));
        Assert.assertEquals(heading.getText().trim(), "File Uploaded!", "Upload success message not shown");

        WebElement uploadedFiles = driver.findElement(By.id("uploaded-files"));
        Assert.assertEquals(uploadedFiles.getText().trim(), fileName, "Uploaded file name is incorrect");
    }

    @Test
    public void testDragAndDrop() {

        driver.get("https://jqueryui.com/resources/demos/droppable/default.html");

        WebElement source = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("draggable")));
        WebElement target = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("droppable")));

        new Actions(driver).dragAndDrop(source, target).perform();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("#droppable p"), "Dropped!"));

        WebElement targetText = driver.findElement(By.cssSelector("#droppable p"));
        Assert.assertEquals(targetText.getText().trim(), "Dropped!", "Drop text was not changed");
    }
}
