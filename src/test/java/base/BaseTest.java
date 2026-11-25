package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp(Method method) throws Exception {
        String browser = "testFourthResultTitle".equals(method.getName())
                ? "firefox"
                : ConfigReader.getTargetBrowser();

        if (ConfigReader.isGridEnabled()) {
            URL gridUrl = URI.create(ConfigReader.getGridUrl()).toURL();
            switch (browser) {
                case "firefox":
                    driver = new RemoteWebDriver(gridUrl, buildFirefoxOptions());
                    break;
                case "chrome":
                    driver = new RemoteWebDriver(gridUrl, buildChromeOptions());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported TargetBrowser: " + browser);
            }
        } else {
            switch (browser) {
                case "firefox":
                    driver = new FirefoxDriver(buildFirefoxOptions());
                    break;
                case "chrome":
                    driver = new ChromeDriver(buildChromeOptions());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported TargetBrowser: " + browser);
            }
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

    private boolean isCiEnvironment() {
        return "true".equalsIgnoreCase(System.getenv("CI"));
    }

    protected ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (isCiEnvironment()) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        return options;
    }

    protected FirefoxOptions buildFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (isCiEnvironment()) {
            options.addArguments("-headless");
        }
        return options;
    }
}