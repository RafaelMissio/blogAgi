package core.utils;

import core.waits.WaitUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;

public class AllureAttachments {

    @Attachment(value = "Screenshot - {status}", type = "image/png")
    public static byte[] screenshot(WebDriver driver, String status) {

        if (driver == null) return null;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WaitUtils.waitForVisualRender(driver);

                byte[] image = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                if (image != null && image.length > 1000) {
                    return image;
                }

                forceRepaint(driver);

            } catch (Exception ignored) {
            }
        }

        try {
            File file = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            if (file != null && file.length() > 1000) {
                return Files.readAllBytes(file.toPath());
            }
        } catch (Exception e) {
            System.err.println("Fallback screenshot falhou: " + e.getMessage());
        }
        return null;
    }

    @Attachment(value = "HTML - {status}", type = "text/html")
    public static String pageSource(WebDriver driver, String status) {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            return "Erro ao capturar HTML";
        }
    }

    private static void forceRepaint(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        js.executeScript("window.scrollTo(0, 0);");
        js.executeScript("document.body.style.transform='scale(1)'");
    }
}
