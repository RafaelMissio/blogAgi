package actions;

import io.qameta.allure.Step;
import pages.SearchPage;

public class SearchActions {

    private final SearchPage searchPage;

    public SearchActions() {
        this.searchPage = new SearchPage();
    }

    @Step("Buscar por termo válido: {term}")
    public boolean searchValidTerm(String term) {
        searchPage.searchFor(term);
        return searchPage.hasResults();
    }

    @Step("Buscar por termo inexistente: {term}")
    public boolean searchInvalidTerm(String term) {
        searchPage.searchFor(term);
        return searchPage.hasNoResultsMessage() && !searchPage.hasResults();
    }
}
