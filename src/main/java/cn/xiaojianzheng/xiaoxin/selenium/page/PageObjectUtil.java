package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.interceptor.PageObjectInterceptor;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * Can be used to wrap subclasses of {@link PageObject } to achieve pre-loading functionality.
 *
 * @author JIAHE
 * @since 1.0
 */
public class PageObjectUtil {

    private static final Callback[] callback = new Callback[]{new PageObjectInterceptor()};

    private static final Function<Object, ElementOperate> getElementOperate =
            obj -> (ElementOperate) ReflectUtil.getFieldValue(obj, "elementOperate");

    public static boolean enablePreloading = false;

    @SuppressWarnings("unchecked")
    public static <T extends PageObject> T wrapPage(Class<T> clazz, String url, AbstractWebDriver webDriver) {
        boolean b = PageObjectUtil.enablePreloading;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallbacks(callback);
        PageObject page = (PageObject) enhancer.create(new Class[]{AbstractWebDriver.class}, new Object[]{webDriver});

        if (StrUtil.isNotBlank(url)) {
            PageObjectUtil.enablePreloading = true;
            page.open(url);
        }

        if (PageObjectUtil.enablePreloading) {
            for (Field field : ReflectUtil.getFields(clazz)) {
                Class<?> fieldType = field.getType();
                if (fieldType == Element.class || fieldType == Elements.class) {
                    preFind(page, field, webDriver);
                }
            }
        }

        PageObjectUtil.enablePreloading = b;
        return (T) page;
    }

    public static <T extends PageObject> T wrapPage(Class<T> clazz, AbstractWebDriver webDriver) {
        return wrapPage(clazz, null, webDriver);
    }

    private static void preFind(Object obj, Field field, AbstractWebDriver webDriver) {
        Object element = ReflectUtil.getFieldValue(obj, field);
        ElementOperate elementOperate;
        if (ObjectUtil.isNotEmpty(element)) {
            if (element instanceof Element) {
                elementOperate = getElementOperate.apply(element);
                if (ObjectUtil.isNotEmpty(elementOperate)) {
                    WebElement webElement = webDriver.findElement(elementOperate);
                    ReflectUtil.setFieldValue(element, "element", webElement);
                }
            } else if (element instanceof Elements) {
                elementOperate = getElementOperate.apply(element);
                if (ObjectUtil.isNotEmpty(elementOperate)) {
                    List<WebElement> webElements = webDriver.findElements(elementOperate);
                    ReflectUtil.setFieldValue(element, "elements", webElements);
                }
            }
        }
    }

}
