package components;

import core.driver.DriverFactory;
import core.waits.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderComponent {

    private final WebDriver driver;

    private final By searchButtonLocator = By.cssSelector("button.search-open, a.search-open, .search-toggle");
    private final By searchInputLocator = By.cssSelector("input[type='search'], input.search-field");

    public HeaderComponent() {
        this.driver = DriverFactory.getDriver();
    }

    @Step("Abrir campo de busca no header")
    public void openSearch() {
        WebElement button = driver.findElement(searchButtonLocator);
        Waits.waitForClickable(button, 10).click();
    }

    @Step("Preencher termo de busca: {term}")
    public void typeSearchTerm(String term) {
        WebElement input = driver.findElement(searchInputLocator);
        Waits.waitForVisibility(input, 10).clear();
        input.sendKeys(term);
    }

    public WebElement getSearchInput() {
        return driver.findElement(searchInputLocator);
    }
}
