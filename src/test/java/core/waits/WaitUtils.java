package core.waits;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static void waitForVisualRender(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(d ->
                "complete".equals(
                        ((JavascriptExecutor) d)
                                .executeScript("return document.readyState")
                )
        );

        wait.until(d -> {
            Long height = (Long) ((JavascriptExecutor) d)
                    .executeScript("return document.body.scrollHeight");
            return height != null && height > 100;
        });

        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {}
    }

}
