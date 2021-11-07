package cn.xiaojianzheng.xiaoxin.selenium.listener;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * log event listener for webdriver
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class WebDriverLogEventListener implements WebDriverListener {

    private By previousBy;

    // Global

    @Override
    public void beforeAnyCall(Object target, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyCall(target, method, args);
    }

    @Override
    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyCall(target, method, args, result);
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);
    }

    // WebDriver

    @Override
    public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyWebDriverCall(driver, method, args);
    }

    @Override
    public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyWebDriverCall(driver, method, args, result);
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        WebDriverListener.super.beforeGet(driver, url);
        log.info("ready open url: {}", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        WebDriverListener.super.afterGet(driver, url);
        log.info("success open url: {}", url);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        if (previousBy != locator) {
            log.info("ready find element: {}", locator);
            previousBy = locator;
        }
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        if (previousBy != locator) {
            log.info("success find element: {}", locator);
            previousBy = locator;
        }
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        if (previousBy != locator) {
            log.info("ready find elements: {}", locator);
            previousBy = locator;
        }
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        if (previousBy != locator) {
            log.info("success find elements: {}", locator);
            previousBy = locator;
        }
    }

    @Override
    public void beforeClose(WebDriver driver) {
        log.info("ready close current window");
    }

    @Override
    public void afterClose(WebDriver driver) {
        log.info("success close current window");
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        log.info("ready close all window and session");
    }

    @Override
    public void afterQuit(WebDriver driver) {
        log.info("success close all window and session");
    }

}
