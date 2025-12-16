package core.utils;

import core.driver.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsInteractions {

    private JsInteractions() {
        // impede instanciação
    }

    private static WebDriver driver() {
        return DriverFactory.getDriver();
    }

    public static void activateUserInteraction() {
        ((JavascriptExecutor) driver()).executeScript("""
            document.body.dispatchEvent(new MouseEvent('mousemove', {bubbles: true}));
            document.body.dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));
            window.scrollBy(0, 1);
        """);
    }

    public static void click(WebElement element) {
        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].click();", element
        );
    }

}
