package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckboxesPage {

    private final WebDriverWait wait;

    private static final String URL = "http://the-internet.herokuapp.com/checkboxes";
    private final By checkbox1Locator = By.xpath("(//input[@type='checkbox'])[1]");
    private final By checkbox2Locator = By.xpath("(//input[@type='checkbox'])[2]");

    public CheckboxesPage(WebDriverWait wait) {
        this.wait = wait;
    }

    public CheckboxesPage open() {
        wait.until(d -> {
            d.get(URL);
            return true;
        });
        return this;
    }

    public void clickCheckbox1() {
        WebElement checkbox1 = wait.until(ExpectedConditions.elementToBeClickable(checkbox1Locator));
        checkbox1.click();
    }

    public boolean isCheckbox1Selected() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(checkbox1Locator)).isSelected();
    }

    public boolean isCheckbox2Selected() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(checkbox2Locator)).isSelected();
    }
}
