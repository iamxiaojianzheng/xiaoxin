package cn.xiaojianzheng.xiaoxin.selenium.listener;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * log event listener for options
 *
 * @author JIAHE
 * @since 1.0
 */
public class OptionsLogEventListener implements WebDriverListener {
    @Override
    public void beforeAnyOptionsCall(WebDriver.Options options, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyOptionsCall(options, method, args);
    }

    @Override
    public void afterAnyOptionsCall(WebDriver.Options options, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyOptionsCall(options, method, args, result);
    }

    @Override
    public void beforeAddCookie(WebDriver.Options options, Cookie cookie) {
        WebDriverListener.super.beforeAddCookie(options, cookie);
    }

    @Override
    public void afterAddCookie(WebDriver.Options options, Cookie cookie) {
        WebDriverListener.super.afterAddCookie(options, cookie);
    }

    @Override
    public void beforeDeleteCookieNamed(WebDriver.Options options, String name) {
        WebDriverListener.super.beforeDeleteCookieNamed(options, name);
    }

    @Override
    public void afterDeleteCookieNamed(WebDriver.Options options, String name) {
        WebDriverListener.super.afterDeleteCookieNamed(options, name);
    }

    @Override
    public void beforeDeleteCookie(WebDriver.Options options, Cookie cookie) {
        WebDriverListener.super.beforeDeleteCookie(options, cookie);
    }

    @Override
    public void afterDeleteCookie(WebDriver.Options options, Cookie cookie) {
        WebDriverListener.super.afterDeleteCookie(options, cookie);
    }

    @Override
    public void beforeDeleteAllCookies(WebDriver.Options options) {
        WebDriverListener.super.beforeDeleteAllCookies(options);
    }

    @Override
    public void afterDeleteAllCookies(WebDriver.Options options) {
        WebDriverListener.super.afterDeleteAllCookies(options);
    }

    @Override
    public void beforeGetCookies(WebDriver.Options options) {
        WebDriverListener.super.beforeGetCookies(options);
    }

    @Override
    public void afterGetCookies(WebDriver.Options options, Set<Cookie> result) {
        WebDriverListener.super.afterGetCookies(options, result);
    }

    @Override
    public void beforeGetCookieNamed(WebDriver.Options options, String name) {
        WebDriverListener.super.beforeGetCookieNamed(options, name);
    }

    @Override
    public void afterGetCookieNamed(WebDriver.Options options, String name, Cookie result) {
        WebDriverListener.super.afterGetCookieNamed(options, name, result);
    }
}
