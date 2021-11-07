package cn.xiaojianzheng.xiaoxin.selenium.listener;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

/**
 * log event listener for window
 *
 * @author JIAHE
 * @since 1.0
 */
public class WindowLogEventListener implements WebDriverListener {
    @Override
    public void beforeAnyWindowCall(WebDriver.Window window, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyWindowCall(window, method, args);
    }

    @Override
    public void afterAnyWindowCall(WebDriver.Window window, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyWindowCall(window, method, args, result);
    }

    @Override
    public void beforeGetSize(WebDriver.Window window) {
        WebDriverListener.super.beforeGetSize(window);
    }

    @Override
    public void afterGetSize(WebDriver.Window window, Dimension result) {
        WebDriverListener.super.afterGetSize(window, result);
    }

    @Override
    public void beforeSetSize(WebDriver.Window window, Dimension size) {
        WebDriverListener.super.beforeSetSize(window, size);
    }

    @Override
    public void afterSetSize(WebDriver.Window window, Dimension size) {
        WebDriverListener.super.afterSetSize(window, size);
    }

    @Override
    public void beforeGetPosition(WebDriver.Window window) {
        WebDriverListener.super.beforeGetPosition(window);
    }

    @Override
    public void afterGetPosition(WebDriver.Window window, Point result) {
        WebDriverListener.super.afterGetPosition(window, result);
    }

    @Override
    public void beforeSetPosition(WebDriver.Window window, Point position) {
        WebDriverListener.super.beforeSetPosition(window, position);
    }

    @Override
    public void afterSetPosition(WebDriver.Window window, Point position) {
        WebDriverListener.super.afterSetPosition(window, position);
    }

    @Override
    public void beforeMaximize(WebDriver.Window window) {
        WebDriverListener.super.beforeMaximize(window);
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        WebDriverListener.super.afterMaximize(window);
    }

    @Override
    public void beforeFullscreen(WebDriver.Window window) {
        WebDriverListener.super.beforeFullscreen(window);
    }

    @Override
    public void afterFullscreen(WebDriver.Window window) {
        WebDriverListener.super.afterFullscreen(window);
    }
}
