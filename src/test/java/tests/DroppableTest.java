package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DroppablePage;

public class DroppableTest extends BaseTest {

    @Test
    public void testDragAndDrop() {
        DroppablePage page = new DroppablePage(driver, wait).open();
        page.dragAndDropToTarget();

        Assert.assertEquals(page.getDropText(), "Dropped!", "Drop text was not changed");
    }
}
