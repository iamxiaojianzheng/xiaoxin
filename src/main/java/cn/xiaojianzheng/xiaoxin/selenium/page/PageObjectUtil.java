package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.hutool.core.util.ReflectUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.*;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Chrome;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Edge;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Firefox;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.IE;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import cn.xiaojianzheng.xiaoxin.selenium.page.interceptor.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;

/**
 * 用于包装 {@link PageObject } 子类以实现预加载功能。
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class PageObjectUtil extends AbstractInterceptor {

    public static <T extends PageObject<ChromeWebDriver>> T wrapPage(Class<T> clazz, ChromeWebDriver driver) {
        T instance = Guice.createInjector(new PageObjectModel(), new AbstractModule() {
            @Provides
            @Chrome
            ChromeWebDriver chromeWebDriver() {
                return driver;
            }
        }).getInstance(clazz);
        wrapElementOrElements(clazz, instance);
        return instance;
    }

    public static <T extends PageObject<InternetWebDriver>> T wrapPage(Class<T> clazz, InternetWebDriver driver) {
        T instance = Guice.createInjector(new PageObjectModel(), new AbstractModule() {
            @Provides
            @IE
            InternetWebDriver driver() {
                return driver;
            }
        }).getInstance(clazz);
        wrapElementOrElements(clazz, instance);
        return instance;
    }

    public static <T extends PageObject<EdgeWebDriver>> T wrapPage(Class<T> clazz, EdgeWebDriver driver) {
        T instance = Guice.createInjector(new PageObjectModel(), new AbstractModule() {
            @Provides
            @Edge
            EdgeWebDriver driver() {
                return driver;
            }
        }).getInstance(clazz);
        wrapElementOrElements(clazz, instance);
        return instance;
    }

    public static <T extends PageObject<FirefoxWebDriver>> T wrapPage(Class<T> clazz, FirefoxWebDriver driver) {
        T instance = Guice.createInjector(new PageObjectModel(), new AbstractModule() {
            @Provides
            @Firefox
            FirefoxWebDriver driver() {
                return driver;
            }
        }).getInstance(clazz);
        wrapElementOrElements(clazz, instance);
        return instance;
    }

    /**
     * 包装Element或Elements，实现WebDriver回填、方法拦截
     */
    private static <T extends PageObject<?>> void wrapElementOrElements(Class<T> clazz, Object pageObject) {
        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            Class<?> elementFieldType = field.getType();
            if (elementFieldType == Element.class || elementFieldType == Elements.class) {
                Object elementFieldObj = ReflectUtil.getFieldValue(pageObject, field);
                ElementOperate elementOperate = getElementOperate.apply(elementFieldObj);
                AbstractWebDriver webDriver = getWebDriver.apply(pageObject);
                Enhancer enhancer = new Enhancer();
                if (elementFieldType == Element.class) {
                    enhancer.setSuperclass(Element.class);
                    enhancer.setCallback(new ElementInterceptor());
                    Element wrapElement = (Element) enhancer.create(new Class[]{ElementOperate.class}, new Object[]{elementOperate});
                    ReflectUtil.setFieldValue(pageObject, field, wrapElement);
                    setWebDriver.accept(wrapElement, webDriver);
                } else {
                    enhancer.setSuperclass(Elements.class);
                    enhancer.setCallback(new ElementsInterceptor());
                    Elements wrapElements = (Elements) enhancer.create(new Class[]{ElementOperate.class}, new Object[]{elementOperate});
                    ReflectUtil.setFieldValue(pageObject, field, wrapElements);
                    setWebDriver.accept(wrapElements, webDriver);
                }
            }
        }
    }

    static class PageObjectModel extends AbstractModule {
        @Override
        protected void configure() {
            bindInterceptor(
                    Matchers.subclassesOf(PageObject.class),
                    Matchers.any(),
                    new WindowAutoSwitchInterceptor(),
                    new IframeAutoSwitchInterceptor(),
                    new PageObjectInterceptor()
            );
        }
    }

}
