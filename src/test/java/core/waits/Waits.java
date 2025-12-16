package core.waits;

import core.driver.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {

    private static WebDriverWait getWait(long timeout) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeout));
    }

    public static WebElement waitForVisibility(WebElement element, long timeout) {
        return getWait(timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebElement element, long timeout) {
        return getWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
    }
}
