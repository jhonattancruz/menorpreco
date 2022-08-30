package com.menorvalor.menorpreco.view;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Produto {

    private String info;

    private Double preco;

    private String link;

    public Produto(String info, Double preco, String link) {
        this.info = info;
        this.preco = preco;
        this.link = link;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getInformacoes() {
        return String.format(""
                             + "\n"
                             + "PRODUTO: %s"
                             + "\n"
                             + "PREÇO: %s"
                             + "\n"
                             + "LINK: %s",
                             this.info,
                             getDecimalFormat("R$ #,##0.00").format(this.preco),
                             this.link);
    }

    public DecimalFormat getDecimalFormat(String formatoNumerico) {
        Locale meuLocal = new Locale("pt", "BR");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(meuLocal);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat(formatoNumerico, symbols);

        return decimalFormat;
    }
}
