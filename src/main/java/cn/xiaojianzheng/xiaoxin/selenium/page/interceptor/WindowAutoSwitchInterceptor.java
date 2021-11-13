package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.page.PageObject;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Window;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class WindowAutoSwitchInterceptor extends AbstractInterceptor implements MethodInterceptor {

    /**
     * 记录上一次切换之前的窗口句柄
     */
    private String previousSwitchAfterWindowHandle;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object obj = methodInvocation.getThis();
        Method method = methodInvocation.getMethod();
        AbstractWebDriver webDriver = getWebDriver.apply(obj);
        String currentWindowHandle = webDriver.getWindowHandle();

        if (PageObject.class != method.getDeclaringClass() && !Objects.equals(currentWindowHandle, previousSwitchAfterWindowHandle)) {
            Window methodWindow = method.getAnnotation(Window.class);
            Window classWindow = obj.getClass().getAnnotation(Window.class);
            // 方法级别的优先
            Window window = methodWindow != null ? methodWindow : classWindow;
            if (window != null) {
                webDriver.getDefaultWebDriverWait().until(driver -> switchWindow(webDriver.getWebDriver(), window));
                previousSwitchAfterWindowHandle = webDriver.getWindowHandle();
            }
        }

        Object proceed = methodInvocation.proceed();

        if (StrUtil.isNotBlank(currentWindowHandle)) {
            webDriver.switchWindow(currentWindowHandle);
        }

        return proceed;
    }

    public boolean switchWindow(WebDriver webDriver, Window window) {
        Set<String> windowHandles = webDriver.getWindowHandles();
        for (String handle : windowHandles) {
            WebDriver driver = webDriver.switchTo().window(handle);
            String title = driver.getTitle();
            String titleContains = window.titleContains();
            if (StrUtil.isNotBlank(titleContains) && title.contains(titleContains)) {
                log.info("自动切换到Window[titleContains contains '{}']", titleContains);
                return true;
            }
            String titleRegex = window.titleRegex();
            if (StrUtil.isNotBlank(titleRegex) && title.matches(titleRegex)) {
                log.info("自动切换到Window[titleRegex contains '{}']", titleRegex);
                return true;
            }

            String currentUrl = driver.getCurrentUrl();
            String urlContains = window.urlContains();
            if (StrUtil.isNotBlank(urlContains) && currentUrl.contains(urlContains)) {
                log.info("自动切换到Window[urlContains contains '{}']", urlContains);
                return true;
            }
            String urlRegex = window.urlRegex();
            if (StrUtil.isNotBlank(urlRegex) && currentUrl.matches(urlRegex)) {
                log.info("自动切换到Window[urlRegex regex '{}']", urlRegex);
                return true;
            }

            int index = window.index();
            if (index > -1) {
                String windowHandle = webDriver.getWindowHandles().toArray(new String[0])[index];
                log.info("自动切换到Window[handle equals '{}']", windowHandle);
                return true;
            }
        }

        throw new NoSuchWindowException(StrUtil.format("Cannot find the window with %s", window));
    }

}
