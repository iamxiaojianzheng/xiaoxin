package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 拦截Page所有get的调用方法，用于回填 {@link cn.xiaojianzheng.xiaoxin.selenium.page.Element }
 * 和 {@link cn.xiaojianzheng.xiaoxin.selenium.page.Elements} 中的webDriver属性
 *
 * @author JIAHE
 * @since 1.0
 */
public class PageObjectInterceptor extends AbstractInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
            String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            Class<?> aClass = obj.getClass();
            Field field = ReflectUtil.getField(aClass, fieldName);
            if (field != null) {
                Object element = ReflectUtil.getFieldValue(obj, field);
                if (ObjectUtil.isNotEmpty(element)) {
                    // 回填driver
                    AbstractWebDriver driver = getWebDriver.apply(obj);
                    if (getWebDriver.apply(element) == null) {
                        setWebDriver.accept(element, driver);
                    }
                }
            }
        }
        return proxy.invokeSuper(obj, args);
    }
}
