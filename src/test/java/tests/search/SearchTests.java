package tests.search;

import actions.SearchActions;
import base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import pages.SearchPage;

import static java.lang.Thread.sleep;

@Epic("Blog do Agi")
@Feature("Pesquisa de artigos")
public class SearchTests extends BaseTest {

    @BeforeEach
    public void cleanBeforeEach() {
        if (driver == null) return;
        try {
            driver.manage().deleteAllCookies();
            try {
                ((JavascriptExecutor) driver).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
            } catch (Exception ignored) { }

            driver.navigate().to(baseUrl);
        } catch (Exception ignored) { }
    }

    @Test
    @Story("Busca com termo válido")
    @DisplayName("Deve retornar resultados ao buscar por termo existente")
    @Description("Valida a busca funcional via URL (?s=)")

    public void validSearchShouldReturnResults() throws InterruptedException {
        SearchActions searchActions = new SearchActions();
        SearchActions actions = new SearchActions();
        SearchPage page = new SearchPage();

        actions.search("cartão");
        sleep(2000);

        searchActions.attachFinalState();
        Assertions.assertTrue(
                page.hasResults(),
                "A busca deveria retornar ao menos um artigo"
        );

    }


    @Test
    @Story("Busca sem resultados")
    @DisplayName("Deve exibir mensagem quando não há resultados")
    @Description("Valida comportamento quando a busca não retorna artigos")

    public void invalidSearchShouldShowNoResultsMessage() {
        SearchActions searchActions = new SearchActions();
        SearchActions actions = new SearchActions();
        SearchPage page = new SearchPage();

        actions.search("xpto123termoInexistente");

        searchActions.attachFinalState();
        Assertions.assertAll(
                () -> Assertions.assertTrue(
                        page.hasNoResults(),
                        "Deveria exibir a seção de nenhum resultado"
                ),
                () -> Assertions.assertTrue(
                        page.hasExpectedNoResultsMessage(),
                        "A mensagem exibida para nenhum resultado está incorreta"
                ),
                () -> Assertions.assertFalse(
                        page.hasResults(),
                        "Não deveria retornar artigos"
                )
        );
    }

    @Test
    @DisplayName("Deve retornar resultados ao pesquisar com campo vazio")
    @Description("""
        Valida o comportamento da busca quando o usuário
        executa uma pesquisa sem informar termo algum.
        """)

    void searchWithEmptyTermShouldReturnResults() {
        SearchActions searchActions = new SearchActions();
        SearchActions actions = new SearchActions();
        SearchPage page = new SearchPage();

        actions.search("");

        searchActions.attachFinalState();
        Assertions.assertTrue(
                page.hasResults(),
                "A busca com campo vazio deveria retornar uma lista de artigos"
        );
    }

    @Test
    @Story("Busca sem resultados")
    @DisplayName("Deve exibir mensagem quando buscar termo com caracteres especiais")
    @Description("""
    Valida que a busca por palavras contendo caracteres especiais
    não retorna artigos e exibe a mensagem de nenhum resultado.
    """)

    public void searchWithSpecialCharactersInWordShouldShowNoResultsMessage() {
        SearchActions searchActions = new SearchActions();
        SearchActions actions = new SearchActions();
        SearchPage page = new SearchPage();

        actions.search("cart@o");
        searchActions.attachFinalState();
        Assertions.assertTrue(page.hasNoResults());
        Assertions.assertTrue(page.hasExpectedNoResultsMessage());



    }



}
