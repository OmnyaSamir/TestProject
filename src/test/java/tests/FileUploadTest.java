package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FileUploadPage;

import java.io.File;

public class FileUploadTest extends BaseTest {

    @Test
    public void testFileUpload() {
        File file = new File("C:\\Users\\Asus\\Downloads\\small-image.png");
        Assert.assertTrue(file.exists(), "File not found: " + file.getAbsolutePath());

        FileUploadPage page = new FileUploadPage(wait).open();
        page.uploadFile(file);

        Assert.assertEquals(page.getHeadingText(), "File Uploaded!", "Upload success message not shown");
        Assert.assertEquals(page.getUploadedFileName(), file.getName(), "Uploaded file name is incorrect");
    }
}
