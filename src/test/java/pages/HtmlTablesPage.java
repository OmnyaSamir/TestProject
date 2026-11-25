package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HtmlTablesPage {

    private final WebDriverWait wait;

    private static final String URL = "https://www.w3schools.com/html/html_tables.asp";

    public HtmlTablesPage(WebDriverWait wait) {
        this.wait = wait;
    }

    public HtmlTablesPage open() {
        wait.until(d -> {
            d.get(URL);
            return true;
        });
        return this;
    }

    public String getCountryForCompany(String companyName) {
        By countryLocator = By.xpath(
                "//table[@id='customers']//tr[td[normalize-space()='" + companyName + "']]/td[3]"
        );
        WebElement country = wait.until(ExpectedConditions.visibilityOfElementLocated(countryLocator));
        return country.getText().trim();
    }
}
