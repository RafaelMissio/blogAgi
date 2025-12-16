package base;

import core.config.ConfigManager;
import core.driver.DriverFactory;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.openqa.selenium.WebDriver;


public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;

    @BeforeEach
    @Step("Iniciando teste")
    public void setUp() {
        driver = DriverFactory.getDriver();
        baseUrl = ConfigManager.get("base.url");

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("base.url não pode ser nulo ou vazio");
        }

        driver.get(baseUrl);
    }

    @AfterEach
    @Step("Finalizando teste")
    public void tearDown() {
        DriverFactory.quitDriver();
    }

}
