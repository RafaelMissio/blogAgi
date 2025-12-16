package pages;

import components.HeaderComponent;
import core.driver.DriverFactory;
import core.waits.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import java.util.List;

public class SearchPage {

    private final WebDriver driver;
    private final HeaderComponent header;

    private final By resultTitlesLocator = By.cssSelector("h2.entry-title a, h2.post-title a");
    private final By noResultsLocator = By.cssSelector(".no-results, .search-no-results, .page-content");

    public SearchPage() {
        this.driver = DriverFactory.getDriver();
        this.header = new HeaderComponent();
    }

    @Step("Executar busca pelo termo: {term}")
    public void searchFor(String term) {
        header.openSearch();
        header.typeSearchTerm(term);
        header.getSearchInput().sendKeys(Keys.ENTER);
    }

    @Step("Verificar se há resultados")
    public boolean hasResults() {
        List<WebElement> results = driver.findElements(resultTitlesLocator);
        return !results.isEmpty();
    }

    @Step("Verificar se há mensagem de nenhum resultado")
    public boolean hasNoResultsMessage() {
        try {
            WebElement msg = driver.findElement(noResultsLocator);
            Waits.waitForVisibility(msg, 10);
            return msg.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Obter quantidade de resultados")
    public int getResultsCount() {
        return driver.findElements(resultTitlesLocator).size();
    }
}
