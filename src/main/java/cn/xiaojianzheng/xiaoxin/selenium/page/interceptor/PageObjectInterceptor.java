package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.ReflectUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.internal.Require;

import java.lang.reflect.Field;

/**
 * 拦截Page所有get的调用方法，用于回填 {@link cn.xiaojianzheng.xiaoxin.selenium.page.Element }
 * 和 {@link cn.xiaojianzheng.xiaoxin.selenium.page.Elements} 中的webDriver属性
 *
 * @author JIAHE
 * @since 1.0
 */
public class PageObjectInterceptor extends AbstractInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String methodName = methodInvocation.getMethod().getName();
        if (methodName.startsWith("get")) {
            String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            Object obj = methodInvocation.getThis();
            Field field = ReflectUtil.getField(obj.getClass(), fieldName);
            Require.nonNull(fieldName, field, "{}方法对应的字段{}缺失", methodName, fieldName);

            // 回填driver
            Object element = ReflectUtil.getFieldValue(obj, field);
            AbstractWebDriver driver = getWebDriver.apply(obj);
            if (getWebDriver.apply(element) == null) {
                setWebDriver.accept(element, driver);
            }
        }
        return methodInvocation.proceed();
    }
}
