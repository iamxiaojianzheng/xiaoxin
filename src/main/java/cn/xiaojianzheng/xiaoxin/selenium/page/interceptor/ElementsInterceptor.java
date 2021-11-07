package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.Elements;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;

/**
 * {@link Elements} method interceptor
 *
 * @author JIAHE
 * @since 1.0
 */
public class ElementsInterceptor extends AbstractInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        AbstractWebDriver webDriver = getWebDriver.apply(o);
        if (webDriver != null) {
            List<WebElement> elements = getElements.apply(o);
            if (elements == null) {
                ElementOperate elementOperate = getElementOperate.apply(o);
                if (elementOperate != null) {
                    elements = webDriver.findElements(elementOperate);
                    setElements.accept(o, elements);
                }
            }
        }
        return proxy.invokeSuper(o, args);
    }
}
