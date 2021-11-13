package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.page.PageObject;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Iframe;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class IframeAutoSwitchInterceptor extends AbstractInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object obj = methodInvocation.getThis();
        Method method = methodInvocation.getMethod();
        AbstractWebDriver webDriver = getWebDriver.apply(obj);
        boolean needToParentFrame = false;
        boolean needToDefaultContent = false;

        if (PageObject.class != methodInvocation.getMethod().getDeclaringClass()) {
            Iframe methodIframe = method.getAnnotation(Iframe.class);
            Iframe classIframe = obj.getClass().getAnnotation(Iframe.class);
            // 方法级别的优先
            Iframe iframe = methodIframe != null ? methodIframe : classIframe;
            if (iframe != null) {
                webDriver.defaultContent();
                switchIframe(webDriver, iframe);
                needToParentFrame = true;
                needToDefaultContent = iframe.needReset();
            }
        }

        Object proceed = methodInvocation.proceed();

        if (needToDefaultContent) {
            webDriver.defaultContent();
        } else if (needToParentFrame) {
            webDriver.parentFrame();
        }

        return proceed;
    }

    public void switchIframe(AbstractWebDriver webDriver, Iframe iframe) {

        if (StrUtil.isNotBlank(iframe.value())) {
            String name = iframe.value();
            log.info("自动切换到Iframe[name equals '{}']", name);
            WebElement element = webDriver.findElement(ByLocation.equalsAttributeValue("iframe", "name", name));
            webDriver.switchIframe(element);

        } else if (StrUtil.isNotBlank(iframe.id())) {
            String id = iframe.id();
            log.info("自动切换到Iframe[id equals '{}']", id);
            WebElement element = webDriver.findElement(ByLocation.id(id));
            webDriver.switchIframe(element);

        } else if (ArrayUtil.isNotEmpty(iframe.index())) {
            int[] index = iframe.index();
            log.info("自动切换到Iframe[index equals '{}']", ArrayUtil.toString(index));
            webDriver.switchIframe(index);

        }

    }

}
