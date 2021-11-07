package cn.xiaojianzheng.xiaoxin.selenium.listener.decorator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JIAHE
 * @since 1.0
 */
public class WindowDecorator extends WebDriverDecorator {
    private Decorated<WebDriver.Window> decorated;
    public final WebDriver.Window decorate(WebDriver.Window original) {
        Require.nonNull("WebDriver.Window", original);
        decorated = createDecorated(original);
        return createProxy(decorated);
    }
}
