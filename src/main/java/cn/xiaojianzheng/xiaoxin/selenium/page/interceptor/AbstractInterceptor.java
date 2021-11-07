package cn.xiaojianzheng.xiaoxin.selenium.page.interceptor;

import cn.hutool.core.util.ReflectUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.Element;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * base interceptor
 *
 * @author JIAHE
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractInterceptor {

    protected final Function<Object, AbstractWebDriver> getWebDriver =
            obj -> (AbstractWebDriver) ReflectUtil.getFieldValue(obj, "webDriver");

    protected final BiConsumer<Object, AbstractWebDriver> setWebDriver =
            (obj, driver) -> ReflectUtil.setFieldValue(obj, "webDriver", driver);

    protected final Function<Object, WebElement> getElement =
            obj -> (WebElement) ReflectUtil.getFieldValue(obj, "element");

    protected final BiConsumer<Object, WebElement> setElement =
            (obj, element) -> ReflectUtil.setFieldValue(obj, "element", element);

    protected final Function<Object, List<WebElement>> getElements =
            obj -> (List<WebElement>) ReflectUtil.getFieldValue(obj, "elements");

    protected final BiConsumer<Object, List<WebElement>> setElements =
            (obj, elements) -> ReflectUtil.setFieldValue(obj, "elements", elements);

    protected final Function<Object, ElementOperate> getElementOperate =
            obj -> (ElementOperate) ReflectUtil.getFieldValue(obj, "elementOperate");

}
