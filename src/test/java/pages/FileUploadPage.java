package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class FileUploadPage {

    private final WebDriverWait wait;

    private static final String URL = "http://the-internet.herokuapp.com/upload";
    private final By fileInputLocator = By.id("file-upload");
    private final By submitButtonLocator = By.id("file-submit");
    private final By headingLocator = By.tagName("h3");
    private final By uploadedFilesLocator = By.id("uploaded-files");

    public FileUploadPage(WebDriverWait wait) {
        this.wait = wait;
    }

    public FileUploadPage open() {
        wait.until(d -> {
            d.get(URL);
            return true;
        });
        return this;
    }

    public void uploadFile(File file) {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileInputLocator));
        fileInput.sendKeys(file.getAbsolutePath());
        wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator)).click();
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator)).getText().trim();
    }

    public String getUploadedFileName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(uploadedFilesLocator)).getText().trim();
    }
}
