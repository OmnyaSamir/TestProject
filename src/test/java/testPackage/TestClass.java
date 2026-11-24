package testPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestClass {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void title() {
        driver.get("https://duckduckgo.com/");
        Assert.assertEquals(driver.getTitle(), "Google");
    }

    @Test
    public void testLogo() {
        driver.get("https://duckduckgo.com/");

        Wait<WebDriver> wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement logo = wait.until(driver -> driver.findElement(By.cssSelector("header img")));

        Assert.assertTrue(logo.isDisplayed(), "Logo is not displayed");

        String src = logo.getAttribute("src");
        Assert.assertNotNull(src, "src should not be null");
        Assert.assertFalse(src.isEmpty(), "src should not be empty");


         }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
