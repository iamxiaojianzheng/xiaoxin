package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Used to build element
 *
 * @author JIAHE
 * @since 1.0
 */
public class Element {

    /**
     * Inject by reflection
     */
    private AbstractWebDriver webDriver;

    /**
     * Inject by reflection, if you need preloading element.
     */
    private WebElement element;

    /**
     * Used for reflection to complete the findElement operation
     */
    private ElementOperate elementOperate;

    public Element(By by) {
        this(ElementOperate.location().by(by).build());
    }

    public Element(ElementOperate elementOperate) {
        this.elementOperate = elementOperate;
    }

    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    public void clear() {
        element.clear();
    }

    public void input(String text) {
        element.sendKeys(text);
    }

    public void click() {
        element.click();
    }

    public String getText() {
        return element.getText();
    }

    public WebElement getElement() {
        return element;
    }
}