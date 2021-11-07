package cn.xiaojianzheng.xiaoxin.selenium.base;

import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Behavioral interface of the element
 *
 * @author JIAHE
 * @since 1.0
 */
public interface ElementBehavior<T> {

    /**
     * 使用给定的方法查找第一个{@link WebElement} 。 此方法受执行时有效的“隐式等待”时间的影响。
     * findElement(..) 调用将返回匹配的行，或者重复尝试直到达到配置的超时。
     * findElement 不应用于查找不存在的元素，而应使用findElements(By)并断言零长度响应。
     *
     * @param by 要使用的定位机制
     * @return 当前页面的第一个匹配元素
     */
    WebElement findElement(By by);

    /**
     * 使用给定的机制查找当前页面中的所有元素。 此方法受执行时有效的“隐式等待”时间的影响。
     * 当隐式等待时，只要找到的集合中有 0 个以上的项目，此方法就会返回，或者如果超时将返回一个空列表。
     *
     * @param by 要使用的定位机制
     * @return 所有匹配的WebElement的列表，如果没有匹配，则为空列表
     */
    List<WebElement> findElements(By by);

    /**
     * 查找页面元素，匹配第一个。（详情查看 {@link ElementBehavior#findElement(By) })
     *
     * @param elementOperate 元素操作规则
     * @return 当前页面的第一个匹配元素
     */
    default WebElement findElement(ElementOperate elementOperate) {
        return null;
    }

    /**
     * 查找页面元素，匹配所有。（详情查看 {@link ElementBehavior#findElements(By)}）
     *
     * @param elementOperate 元素操作规则
     * @return 所有匹配的WebElement的列表，如果没有匹配，则为空列表
     */
    default List<WebElement> findElements(ElementOperate elementOperate) {
        return null;
    }

    /**
     * 如果此元素是表单条目元素，则将重置其值。
     */
    T clear(By by);

    /**
     * 如果此元素是表单条目元素，则将重置其值。
     */
    default T clear(ElementOperate elementOperate) {
        return null;
    }

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     */
    T input(By by, String text);

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     */
    default T input(ElementOperate elementOperate) {
        return null;
    }

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     *
     * @param element   元素
     * @param inputText 带输入的文本内容
     */
    T input(WebElement element, String inputText);

    /**
     * 单击此元素。 如果这会导致加载新页面，则应放弃对此元素的所有引用，
     * 并且对该元素执行的任何进一步操作都将引发 {@link org.openqa.selenium.StaleElementReferenceException}。
     * 请注意，如果 click() 是通过发送本机事件（这是大多数浏览器/平台上的默认值）完成的，则该方法将_不_等待下一页加载，调用者应自行验证。
     * 单击元素有一些先决条件。 元素必须是可见的，并且它的高度和宽度必须大于 0。
     */
    T click(By by);

    /**
     * 单击此元素，详情查看{@link ElementBehavior#click(By by)}方法。
     */
    default T click(ElementOperate elementOperate) {
        return null;
    }

    /**
     * 单击此元素，详情查看{@link ElementBehavior#click(By by)}方法。
     */
    default T click(ElementOperate elementOperate, WebElement element) {
        return null;
    }

    /**
     * 滑动
     */
    default void swipe() {
    }

    /**
     * 坐标
     */
    default void position() {
    }

    /**
     * 截图
     */
    default void screenshots() {
    }

    /**
     * 长按
     */
    default void tapHold() {
    }

    /**
     * 滑动
     */
    default void sliding() {
    }

}
