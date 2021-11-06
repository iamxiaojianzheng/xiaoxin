package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import org.openqa.selenium.WebElement;

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

    private ElementOperate elementOperate;

    public Element(ElementOperate elementOperate) {
        this.elementOperate = elementOperate;
    }

    public void click() {
        if (element == null) {
            webDriver.click(elementOperate);
        } else {
            webDriver.click(elementOperate, element);
        }
    }

    public void input(String text) {
        if (element == null) {
            webDriver.input(elementOperate);
        } else {
            webDriver.input(element, text);
        }
    }

    public String getText() {
        return element.getText();
    }

}