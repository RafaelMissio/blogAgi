package tests;


import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchTest extends BaseTest {

    @Test
    @Story("Busca com termo válido")
    @DisplayName("Deve retornar resultados ao buscar por termo existente")
    @Description("Valida que a busca retorna artigos relacionados ao termo informado")
    public void testBuscaArtigos() {
        String termoBusca = "emprestimo";
        HomePage home = new HomePage(driver);

        SearchResultsPage results = home
                .clicarNaLupa()
                .buscar(termoBusca);

//       Assertions.assertTrue(driver.getCurrentUrl().contains("s=" + termoBusca));
        Assertions.assertEquals("Resultados encontrados para: " + termoBusca, results.getMensagemResultado());
        Assertions.assertTrue(results.temMaisDeUmArtigo());
    }

    @Test
    @Story("Busca com termo inválido")
    @DisplayName("Não deve retornar resultados ao buscar por termo inexistente")
    @Description("Valida que a busca não retorna artigos quando o termo não existe")
    public void testBuscaTermoInvalido() {
        String termoBusca = "xyzabc123termoinexistente";
        HomePage home = new HomePage(driver);

        SearchResultsPage results = home
                .clicarNaLupa()
                .buscar(termoBusca);


        Assertions.assertEquals("Resultados encontrados para: " + termoBusca, results.getMensagemResultado());
        Assertions.assertTrue(results.nenhumArtigoEncontrado());
        Assertions.assertEquals("Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras.",
                results.getMensagemNenhumResultado());
    }

    @Test
    @Story("Busca de termos com caracteres especiais")
    @DisplayName("Não deve retornar resultados ao buscar por termo com caracteres especiais")
    @Description("Valida que a busca não retorna artigos quando o termo tem caracteres especiais")
    public void testBuscaTermoCaracteresEspeciais() {
        String termoBusca = "cart@o";
        HomePage home = new HomePage(driver);

        SearchResultsPage results = home
                .clicarNaLupa()
                .buscar(termoBusca);


        Assertions.assertEquals("Resultados encontrados para: " + termoBusca, results.getMensagemResultado());
        Assertions.assertTrue(results.nenhumArtigoEncontrado());
        Assertions.assertEquals("Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras.",
                results.getMensagemNenhumResultado());
    }

    @Test
    @Story("Busca com termo vazio")
    @DisplayName("Deve retornar resultados ao buscar por termo existente")
    @Description("Valida que a busca retorna artigos relacionados ao termo informado")
    public void testBuscaTermoVazio() {
        String termoBusca = "";
        HomePage home = new HomePage(driver);

        SearchResultsPage results = home
                .clicarNaLupa()
                .buscar(termoBusca);


        Assertions.assertEquals("Resultados encontrados para:", results.getMensagemResultado());
        Assertions.assertTrue(results.temMaisDeUmArtigo());
    }

}



