package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
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
    private WebElement webElement;

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
        return webDriver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public void clear() {
        webDriver.clear(elementOperate);
    }

    public void input(String text) {
        elementOperate.setInputText(text);
        webDriver.input(elementOperate);
    }

    public void click() {
        webDriver.click(elementOperate);
    }

    public WebElement getWebElement() {
        return webElement;
    }

}