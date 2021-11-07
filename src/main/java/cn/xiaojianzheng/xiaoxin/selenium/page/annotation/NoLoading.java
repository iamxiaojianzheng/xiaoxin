package cn.xiaojianzheng.xiaoxin.selenium.page.annotation;

import cn.xiaojianzheng.xiaoxin.selenium.page.PageObject;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link PageObject} 页面加载完成后不需要预加载的元素
 *
 * @author JIAHE
 * @since 1.0
 */
@Inherited
@Target({FIELD})
@Retention(RUNTIME)
public @interface NoLoading {
}
