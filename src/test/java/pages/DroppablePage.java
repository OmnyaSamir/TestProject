package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DroppablePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL = "https://jqueryui.com/resources/demos/droppable/default.html";
    private final By draggableLocator = By.id("draggable");
    private final By droppableLocator = By.id("droppable");
    private final By droppableTextLocator = By.cssSelector("#droppable p");

    public DroppablePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public DroppablePage open() {
        wait.until(d -> {
            d.get(URL);
            return true;
        });
        return this;
    }

    public void dragAndDropToTarget() {
        WebElement source = wait.until(ExpectedConditions.visibilityOfElementLocated(draggableLocator));
        WebElement target = wait.until(ExpectedConditions.visibilityOfElementLocated(droppableLocator));
        new Actions(driver).dragAndDrop(source, target).perform();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(droppableTextLocator, "Dropped!"));
    }

    public String getDropText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(droppableTextLocator)).getText().trim();
    }
}
