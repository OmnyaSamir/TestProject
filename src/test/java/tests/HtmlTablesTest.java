package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HtmlTablesPage;

public class HtmlTablesTest extends BaseTest {

    @Test
    public void testCountryForErnstHandel() {
        HtmlTablesPage page = new HtmlTablesPage(wait).open();
        String country = page.getCountryForCompany("Ernst Handel");

        Assert.assertEquals(country, "Austria", "Country for Ernst Handel is incorrect");
    }
}
