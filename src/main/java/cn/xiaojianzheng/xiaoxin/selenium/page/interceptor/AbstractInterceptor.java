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

    protected static final Function<Object, AbstractWebDriver> getWebDriver =
            obj -> (AbstractWebDriver) ReflectUtil.getFieldValue(obj, "webDriver");

    protected static final BiConsumer<Object, AbstractWebDriver> setWebDriver =
            (obj, driver) -> ReflectUtil.setFieldValue(obj, "webDriver", driver);

    protected static final Function<Object, WebElement> getElement =
            obj -> (WebElement) ReflectUtil.getFieldValue(obj, "webElement");

    protected static final BiConsumer<Object, WebElement> setElement =
            (obj, element) -> ReflectUtil.setFieldValue(obj, "webElement", element);

    protected static final Function<Object, List<WebElement>> getElements =
            obj -> (List<WebElement>) ReflectUtil.getFieldValue(obj, "webElements");

    protected static final BiConsumer<Object, List<WebElement>> setElements =
            (obj, elements) -> ReflectUtil.setFieldValue(obj, "webElements", elements);

    protected static final Function<Object, ElementOperate> getElementOperate =
            obj -> (ElementOperate) ReflectUtil.getFieldValue(obj, "elementOperate");

    protected static final BiConsumer<Object, ElementOperate> setElementOperate =
            (obj, elementOperate) -> ReflectUtil.setFieldValue(obj, "elementOperate", elementOperate);

}
