package com.menorvalor.menorpreco.service;

import com.menorvalor.menorpreco.util.Log;
import com.menorvalor.menorpreco.util.SeleniumUtil;
import com.menorvalor.menorpreco.util.WebDriverUtil;
import com.menorvalor.menorpreco.view.Produto;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MenorPrecoService {

    private SeleniumUtil seleniumUtil = new SeleniumUtil();

    private WebDriverUtil webDriverUtil = new WebDriverUtil();

    private final String URL_BASE = "https://www.americanas.com.br";

    private final String PESQUISAR_PRODUTO = "Webcam";

    private final int QTDE_PRODUTOS = 3;

    private final Long TEMPO_ESPERA_PRIMEIRO_VEZ = 60L;

    private final Long TEMPO_ESPERA = 15L;

    private final Long AGUARDAR_SEGUNDO = 1L;

    public void executar() {
        Log.log("Iniciando automacao de menor preco".toUpperCase());

        Log.log("Abrindo o browser: CHROMEDRIVER");
        WebDriver webDriver = this.webDriverUtil.abrirBrowser();

        Log.log("Navegando para: %s", this.URL_BASE);
        webDriver.navigate().to(this.URL_BASE);

        try {
            this.seleniumUtil.aguardarSegundos(AGUARDAR_SEGUNDO);

            WebElement inputSearch = this.seleniumUtil.aguardarElementoVisivel(webDriver, TEMPO_ESPERA, By.xpath("//input[@name='conteudo']"));
            inputSearch.clear();
            this.seleniumUtil.aguardarSegundos(AGUARDAR_SEGUNDO);

            Log.log("Pesquisando o produto: %s", this.URL_BASE);
            inputSearch.sendKeys(this.PESQUISAR_PRODUTO + Keys.ENTER);
            this.seleniumUtil.aguardarSegundos(AGUARDAR_SEGUNDO);

            Select select = new Select(this.seleniumUtil.aguardarElementoVisivel(webDriver, TEMPO_ESPERA, By.xpath("//select[@id='sort-by']")));

            Log.log("Ordenando por: %s", "ALTERAR"); // select.getAllSelectedOptions().get(0).getText()
            select.selectByValue("lowerPriceRelevance");
            this.seleniumUtil.aguardarSegundos(AGUARDAR_SEGUNDO);

            this.seleniumUtil.aguardarElementoDesaparecer(webDriver, this.TEMPO_ESPERA_PRIMEIRO_VEZ, By.xpath("//div[@contains(@class, 'loading__Content-sc')]"));
            this.seleniumUtil.aguardarSegundos(AGUARDAR_SEGUNDO);

            List<Produto> listProdutos = new ArrayList<>();
            List<WebElement> listWEProdutos = this.seleniumUtil.aguardarElementosVisiveis(webDriver, this.TEMPO_ESPERA_PRIMEIRO_VEZ, By.xpath("//div[contains(@class, 'col__StyledCol-sc')]//div[contains(@class, 'col__StyledCol-sc') and contains(@class, 'src__ColGridItem-sc')]"));

            if (listWEProdutos != null
                && !listWEProdutos.isEmpty()) {
                Log.log("Foram encontrados %d produtos, buscando os 3 menores precos da 1a pagina", listWEProdutos.size());

                for (WebElement weProduto : listWEProdutos) {
                    WebElement weInfo = weProduto.findElement(By.xpath("div[contains(@class, 'src__Wrapper-sc')]//h3"));
                    String info = weInfo.getText();

                    WebElement wePreco = weProduto.findElement(By.xpath("div//span[contains(@class, 'src__Text-sc') and contains(@class, 'price__PromotionalPrice-sc')]"));
                    Double preco = new Double(wePreco.getText().replace("R$ ", "").replace(",", "."));

                    WebElement weLink = weProduto.findElement(By.xpath("div//a"));
                    String link = weLink.getAttribute("href");

                    Produto produto = new Produto(info,
                                                  preco,
                                                  link);

                    if (listProdutos.size() < this.QTDE_PRODUTOS
                        && !listProdutos.stream().anyMatch(pro -> pro.getPreco().compareTo(preco) == 0)) {
                        listProdutos.add(produto);
                    } else if (listProdutos.stream().anyMatch(pro -> pro.getPreco().compareTo(preco) > 0)
                               && !listProdutos.stream().anyMatch(pro -> pro.getPreco().compareTo(preco) == 0)) {
                        listProdutos.remove(listProdutos.stream().filter(pro -> pro.getPreco().compareTo(preco) > 0).sorted(Comparator.comparing(Produto::getPreco).reversed()).collect(Collectors.toList()).get(0));

                        listProdutos.add(produto);
                    }
                }

            }

            int countReg = 0;

            for (Produto produto : listProdutos.stream().sorted(Comparator.comparing(Produto::getPreco)).collect(Collectors.toList())) {
                countReg++;

                Log.log("");
                Log.log("");
                Log.log("");
                Log.log("%do PRODUTO", countReg);
                Log.log(produto.getInformacoes());
            }
        } catch (Exception ex) {
            Log.log("Erro na aplicacao. Contate o suporte.");
        } finally {
            this.webDriverUtil.fecharBrowser(webDriver);
        }

        Log.log("Encerrando automacao de menor preco".toUpperCase());
    }
}
