package cn.xiaojianzheng.xiaoxin.selenium.listener;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

/**
 * log event listener for alert
 *
 * @author JIAHE
 * @since 1.0
 */
public class AlertLogEventListener implements WebDriverListener {
    @Override
    public void beforeAnyAlertCall(Alert alert, Method method, Object[] args) {
        WebDriverListener.super.beforeAnyAlertCall(alert, method, args);
    }

    @Override
    public void afterAnyAlertCall(Alert alert, Method method, Object[] args, Object result) {
        WebDriverListener.super.afterAnyAlertCall(alert, method, args, result);
    }

    @Override
    public void beforeAccept(Alert alert) {
        WebDriverListener.super.beforeAccept(alert);
    }

    @Override
    public void afterAccept(Alert alert) {
        WebDriverListener.super.afterAccept(alert);
    }

    @Override
    public void beforeDismiss(Alert alert) {
        WebDriverListener.super.beforeDismiss(alert);
    }

    @Override
    public void afterDismiss(Alert alert) {
        WebDriverListener.super.afterDismiss(alert);
    }

    @Override
    public void beforeGetText(Alert alert) {
        WebDriverListener.super.beforeGetText(alert);
    }

    @Override
    public void afterGetText(Alert alert, String result) {
        WebDriverListener.super.afterGetText(alert, result);
    }

    @Override
    public void beforeSendKeys(Alert alert, String text) {
        WebDriverListener.super.beforeSendKeys(alert, text);
    }

    @Override
    public void afterSendKeys(Alert alert, String text) {
        WebDriverListener.super.afterSendKeys(alert, text);
    }
}
