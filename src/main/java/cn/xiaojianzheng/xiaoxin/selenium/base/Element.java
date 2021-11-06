package cn.xiaojianzheng.xiaoxin.selenium.base;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author JIAHE
 * @since 1.0
 */
public interface Element {

    void click();

    void input(String text);

    WebElement findElement();

    List<WebElement> findElements();

    void clear();

    String getText();

    /**
     *
     */
    void swipe();

    /**
     * 坐标
     */
    void position();

    /**
     * 滚动
     */
    void scroll();

    /**
     * 滚动到指定元素
     */
    void scrollSearch();

    /**
     * 截图
     */
    void screenshots();

    /**
     * 长按
     */
    void tapHold();

    /**
     * 滑动
     */
    void sliding();

}
