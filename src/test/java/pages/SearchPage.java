package pages;

import core.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Decisão técnica:
 * A busca do Blog do Agi é validada via URL (?s=),
 * pois o componente de busca do header depende de
 * interação humana real e não é determinístico
 * para automação Selenium.
 */
public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait navigationWait;

    private final By results =
            By.cssSelector("main#main article[id^='post-'].type-post");

    private final By noResultsSection = By.cssSelector("section.no-results.not-found");
    private final By noResultsMessageText =
            By.cssSelector("section.no-results.not-found .page-content p");

    public SearchPage() {
        this.driver = DriverFactory.getDriver();
        this.navigationWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private void waitForSearchState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(results),
                    ExpectedConditions.visibilityOfElementLocated(noResultsSection)
            ));

            long stableUntil = System.currentTimeMillis() + 700; // 700ms
            while (System.currentTimeMillis() < stableUntil) {
                if (hasResults() || hasNoResults()) {
                    return;
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException ignored) { }
            }

            // se não detectou nada mesmo após visibilidade, falha explicativa
            if (!hasResults() && !hasNoResults()) {
                throw new TimeoutException("Nenhum estado de busca detectado após visibilidade.");
            }

        } catch (TimeoutException e) {
            Path[] artifacts = dumpDebugArtifacts();
            String message = "A página de busca não estabilizou: nem resultados nem mensagem de erro apareceram.";
            if (artifacts != null && artifacts.length == 2) {
                message += " Artifacts: " + artifacts[0] + " , " + artifacts[1];
            }
            throw new AssertionError(message, e);
        }
    }

    public void searchFor(String term) {
        String encodedTerm = term == null
                ? ""
                : URLEncoder.encode(term, StandardCharsets.UTF_8);

        driver.get("https://blogdoagi.com.br/?s=" + encodedTerm);

        navigationWait.until(ExpectedConditions.urlContains("?s="));

        waitForSearchState();
    }

    public boolean hasResults() {
        return !driver.findElements(results).isEmpty();
    }

    public boolean hasNoResults() {
        return !driver.findElements(noResultsSection).isEmpty();
    }

    public boolean hasExpectedNoResultsMessage() {
        WebElement message = driver.findElement(noResultsMessageText);
        return message.getText().trim().equals(
                "Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras."
        );
    }

    private Path[] dumpDebugArtifacts() {
        try {
            Path targetDir = Paths.get("target");
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }
            String ts = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(Instant.now().atZone(java.time.ZoneId.systemDefault()));
            Path html = targetDir.resolve("debug_search_" + ts + ".html");
            Path png = targetDir.resolve("debug_search_" + ts + ".png");

            Files.writeString(html, driver.getPageSource(), StandardCharsets.UTF_8);

            try {
                File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(scr.toPath(), png);
            } catch (ClassCastException | IOException ex) {

            }

            return new Path[]{html, png};
        } catch (Exception ex) {
            return null;
        }
    }
}
