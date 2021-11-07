package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.NoLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.PreLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.interceptor.ElementInterceptor;
import cn.xiaojianzheng.xiaoxin.selenium.page.interceptor.ElementsInterceptor;
import cn.xiaojianzheng.xiaoxin.selenium.page.interceptor.PageObjectInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * 用于包装 {@link PageObject } 子类以实现预加载功能。
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class PageObjectUtil {

    private static final Callback[] callbacks = new Callback[]{new PageObjectInterceptor()};

    private static final Function<Object, ElementOperate> getElementOperate =
            obj -> (ElementOperate) ReflectUtil.getFieldValue(obj, "elementOperate");

    @SuppressWarnings("unchecked")
    public static <T extends PageObject> T wrapPage(Class<T> clazz, String url, AbstractWebDriver webDriver) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallbacks(callbacks);
        PageObject page = (PageObject) enhancer.create(new Class[]{AbstractWebDriver.class}, new Object[]{webDriver});

        if (StrUtil.isNotBlank(url)) {
            page.open(url);
        }

        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            wrapElement(page, field);
            // 是否需要对该字段进行预加载
            if (needPreLoading(clazz, field)) {
                preFind(page, field, webDriver);
            }
        }

        return (T) page;
    }

    public static <T extends PageObject> T wrapPage(Class<T> clazz, AbstractWebDriver webDriver) {
        return wrapPage(clazz, null, webDriver);
    }

    /**
     * 包装Element或Elements，实现方法拦截
     */
    private static void wrapElement(PageObject pageObject, Field elementField) {
        Class<?> elementFieldType = elementField.getType();
        if (elementFieldType == Element.class || elementFieldType == Elements.class) {
            Object elementFieldObj = ReflectUtil.getFieldValue(pageObject, elementField);
            Object elementOperate = ReflectUtil.getFieldValue(elementFieldObj, "elementOperate");
            Enhancer enhancer = new Enhancer();
            if (elementFieldType == Element.class) {
                enhancer.setSuperclass(Element.class);
                enhancer.setCallback(new ElementInterceptor());
                Element wrapElement = (Element) enhancer.create(new Class[]{ElementOperate.class}, new Object[]{elementOperate});
                ReflectUtil.setFieldValue(pageObject, elementField, wrapElement);
            } else {
                enhancer.setSuperclass(Elements.class);
                enhancer.setCallback(new ElementsInterceptor());
                Elements wrapElement = (Elements) enhancer.create(new Class[]{ElementOperate.class}, new Object[]{elementOperate});
                ReflectUtil.setFieldValue(pageObject, elementField, wrapElement);
            }
        }
    }

    /**
     * 元素预加载
     */
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

    private static boolean needPreLoading(Class<?> clazz, Field field) {
        // 必要条件
        Class<?> fieldType = field.getType();
        boolean b1 = (fieldType == Element.class || fieldType == Elements.class);
        if (!b1) return false;

        // 存在则直接false
        boolean b2 = field.getAnnotationsByType(NoLoading.class).length == 1;
        if (b2) return false;

        boolean b3 = clazz.getAnnotationsByType(PreLoading.class).length == 1;
        boolean b4 = field.getAnnotationsByType(PreLoading.class).length == 1;
        return b3 || b4;
    }

}
