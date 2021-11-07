package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.Element;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Require;

import java.lang.reflect.Method;

/**
 * {@link Element} method interceptor
 *
 * @author JIAHE
 * @since 1.0
 */
public class ElementInterceptor extends AbstractInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        AbstractWebDriver webDriver = getWebDriver.apply(o);
        Require.nonNull("webDriver", webDriver,
                "webDriver回填Element失败：Object {}, Method {}, Object[] {}", o, method, args);

        if (method.getName().equals("getWebElement")) {
            ElementOperate elementOperate = getElementOperate.apply(o);
            Require.nonNull("elementOperate", elementOperate);
            WebElement element = webDriver.findElement(elementOperate);
            // 回填 webElement
            setElement.accept(o, element);
        }

        return proxy.invokeSuper(o, args);
    }
}
