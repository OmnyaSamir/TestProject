package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;

public class CheckboxesTest extends BaseTest {

    @Test
    public void testCheckboxes() {
        CheckboxesPage page = new CheckboxesPage(wait).open();
        page.clickCheckbox1();

        Assert.assertTrue(page.isCheckbox1Selected(), "Checkbox 1 is not checked");
        Assert.assertTrue(page.isCheckbox2Selected(), "Checkbox 2 is not checked");
    }
}
