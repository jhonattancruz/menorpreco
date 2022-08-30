package com.menorvalor.menorpreco.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumUtil {

    public WebElement aguardarElementoVisivel(WebDriver webDriver,
                                              Long tempoEspera,
                                              By by) {
//        try {
        return new WebDriverWait(webDriver, tempoEspera).until(ExpectedConditions.visibilityOfElementLocated(by));
//        } catch (Exception ex) {
//        }
    }

    public static List<WebElement> aguardarElementosVisiveis(WebDriver webDriver, Long tempoEspera, By by) {
//        try {
        return (List) (new WebDriverWait(webDriver, tempoEspera)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
//        } catch (Exception ex) {
//        }
    }

    public static boolean aguardarElementoDesaparecer(WebDriver webDriver, Long tempoEspera, By by) {
//        try {
        return (Boolean) (new WebDriverWait(webDriver, tempoEspera)).until(ExpectedConditions.invisibilityOfElementLocated(by));
//        } catch (Exception ex) {
//        }
    }

    public static boolean isElementoVisivel(WebDriver webDriver, Long tempoEspera, By by) {
        try {
            (new WebDriverWait(webDriver, tempoEspera)).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static void aguardarSegundos(Long segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos.intValue());
        } catch (Exception ex) {
        }
    }
}
