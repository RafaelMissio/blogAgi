package actions;

import core.driver.DriverFactory;
import core.utils.AllureAttachments;
import io.qameta.allure.Step;
import pages.SearchPage;

public class SearchActions {

    private final SearchPage searchPage;

    public SearchActions() {
        this.searchPage = new SearchPage();
    }

    @Step("Buscar por termo: {term}")
    public void search(String term) {
        searchPage.searchFor(term);
    }

    @Step("Resultado final da busca")
    public void attachFinalState() {
        AllureAttachments.screenshot(
                DriverFactory.getDriver(),
                "FINAL"
        );
    }
}
