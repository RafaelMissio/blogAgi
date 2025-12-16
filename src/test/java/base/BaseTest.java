package base;

import core.config.ConfigManager;
import core.driver.DriverFactory;
import core.hooks.TestListener;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@ExtendWith(TestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;

    @BeforeEach
    @Step("Iniciando teste")
    public void setUp() {
        driver = DriverFactory.getDriver();
        baseUrl = ConfigManager.get("base.url");
        driver.get(baseUrl);
    }

    @AfterEach
    @Step("Finalizando teste")
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Step("Capturando screenshot")
    public byte[] takeScreenshot() {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return null;
        }
    }
}
