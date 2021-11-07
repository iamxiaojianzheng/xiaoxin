package cn.xiaojianzheng.xiaoxin.selenium.listener;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * log event listener for navigation
 *
 * @author JIAHE
 * @since 1.0
 */
public class NavigationLogEventListener implements WebDriverListener {
    @Override
    public void beforeAnyNavigationCall(WebDriver.Navigation navigation, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyNavigationCall(navigation, method, args);
    }

    @Override
    public void afterAnyNavigationCall(WebDriver.Navigation navigation, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyNavigationCall(navigation, method, args, result);
    }

    @Override
    public void beforeTo(WebDriver.Navigation navigation, String url) {
        WebDriverListener.super.beforeTo(navigation, url);
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        WebDriverListener.super.afterTo(navigation, url);
    }

    @Override
    public void beforeTo(WebDriver.Navigation navigation, URL url) {
        WebDriverListener.super.beforeTo(navigation, url);
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, URL url) {
        WebDriverListener.super.afterTo(navigation, url);
    }

    @Override
    public void beforeBack(WebDriver.Navigation navigation) {
        WebDriverListener.super.beforeBack(navigation);
    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        WebDriverListener.super.afterBack(navigation);
    }

    @Override
    public void beforeForward(WebDriver.Navigation navigation) {
        WebDriverListener.super.beforeForward(navigation);
    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        WebDriverListener.super.afterForward(navigation);
    }

    @Override
    public void beforeRefresh(WebDriver.Navigation navigation) {
        WebDriverListener.super.beforeRefresh(navigation);
    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        WebDriverListener.super.afterRefresh(navigation);
    }
}
