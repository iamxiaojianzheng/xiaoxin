package cn.xiaojianzheng.xiaoxin.selenium.page.annotation;

import org.openqa.selenium.WebDriver;

import java.lang.annotation.*;

/**
 * 用于在执行操作时，自动切换窗口。
 * 仅适用于Page Objects模式。
 *
 * @author JIAHE
 * @since 1.0
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Window {

    /**
     * Return the keyword of window title
     *
     * @return the keyword of window title
     */
    String titleContains() default "";

    /**
     * Return the regex pattern of window title
     *
     * @return the regex pattern of window title
     */
    String titleRegex() default "";

    /**
     * Return the keyword of window url
     *
     * @return the keyword of window url
     */
    String urlContains() default "";

    /**
     * Return the regex pattern of window url
     *
     * @return the regex pattern of window url
     */
    String urlRegex() default "";

    /**
     * Returns the index of the current window in all windows
     * look for {@link WebDriver#getWindowHandles()}
     *
     * @return the index of the current window in all windows
     */
    int index() default 0;

}
