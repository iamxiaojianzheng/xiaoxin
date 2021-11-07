package cn.xiaojianzheng.xiaoxin.selenium.listener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * log event listener for timeouts
 *
 * @author JIAHE
 * @since 1.0
 */
public class TimeoutsLogEventListener implements WebDriverListener {
    @Override
    public void beforeAnyTimeoutsCall(WebDriver.Timeouts timeouts, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyTimeoutsCall(timeouts, method, args);
    }

    @Override
    public void afterAnyTimeoutsCall(WebDriver.Timeouts timeouts, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyTimeoutsCall(timeouts, method, args, result);
    }

    @Override
    public void beforeImplicitlyWait(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.beforeImplicitlyWait(timeouts, duration);
    }

    @Override
    public void afterImplicitlyWait(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.afterImplicitlyWait(timeouts, duration);
    }

    @Override
    public void beforeSetScriptTimeout(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.beforeSetScriptTimeout(timeouts, duration);
    }

    @Override
    public void afterSetScriptTimeout(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.afterSetScriptTimeout(timeouts, duration);
    }

    @Override
    public void beforePageLoadTimeout(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.beforePageLoadTimeout(timeouts, duration);
    }

    @Override
    public void afterPageLoadTimeout(WebDriver.Timeouts timeouts, Duration duration) {
        WebDriverListener.super.afterPageLoadTimeout(timeouts, duration);
    }
}
