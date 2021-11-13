package cn.xiaojianzheng.xiaoxin.selenium.page.annotation;

import java.lang.annotation.*;

/**
 * 用于在执行操作时，自动切换Iframe。 仅适用于Page Objects模式。
 *
 * @author JIAHE
 * @since 1.0
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Iframe {

    /**
     * Return the name of iframe
     *
     * @return the name of iframe
     */
    String value() default "";

    /**
     * Return the id of iframe
     *
     * @return the id of iframe
     */
    String id() default "";

    /**
     * Return the xpath of iframe
     *
     * @return the xpath of iframe
     */
    String xpath() default "";

    /**
     * Returns the index array of the iframe order in windows
     *
     * @return the index of the current window in all windows
     */
    int[] index() default {};

    /**
     * Returns the iframe need to be reset to defaultContent
     *
     * @return the iframe need to be reset to defaultContent
     */
    boolean needReset() default false;

}
