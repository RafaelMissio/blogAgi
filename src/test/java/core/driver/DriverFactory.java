package core.driver;

import core.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            createDriver();
        }
        return driverThread.get();
    }

    private static void createDriver() {
        String browser = ConfigManager.get("browser");
        boolean headless = ConfigManager.getBoolean("headless");

        WebDriver driver;

        switch (browser.toLowerCase()) {

            case "chrome" -> {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();

                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                if (headless) {
                    options.addArguments(
                            "--headless",
                            "--window-size=1920,1080",
                            "--disable-gpu",
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--remote-allow-origins=*"
                    );
                }


                driver = new ChromeDriver(options);

                driver.manage().window().setSize(new Dimension(1920, 1080));


                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, document.body.scrollHeight);");
                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, 0);");
            }

            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }

            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }

            default -> throw new RuntimeException(
                    "Navegador não suportado: " + browser
            );
        }


        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        driverThread.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}
