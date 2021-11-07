package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.page.PageObject;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Window;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class WindowAutoSwitchInterceptor extends AbstractInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object obj = methodInvocation.getThis();
        Window window = obj.getClass().getAnnotation(Window.class);

        if (PageObject.class != methodInvocation.getMethod().getDeclaringClass() && window != null) {
            AbstractWebDriver webDriver = getWebDriver.apply(obj);
            WebDriverWait defaultWebDriverWait = webDriver.getDefaultWebDriverWait();
            if (StrUtil.isNotBlank(window.value())) {
                log.info("自动切换到Window[name contains '{}']", window.value());
                defaultWebDriverWait.until(driver -> webDriver.switchWindowByName(window.value()));

            } else if (StrUtil.isNotBlank(window.url())) {
                log.info("自动切换到Window[url contains '{}']", window.url());
                defaultWebDriverWait.until(driver -> webDriver.switchWindowByUrl(window.url()));

            } else if (window.index() > -1) {
                String handle = webDriver.getWindowHandles().toArray(new String[0])[window.index()];
                log.info("自动切换到Window[handle equals '{}']", handle);
                defaultWebDriverWait.until(driver -> webDriver.switchWindow(handle));
            }
        }

        return methodInvocation.proceed();
    }
}
