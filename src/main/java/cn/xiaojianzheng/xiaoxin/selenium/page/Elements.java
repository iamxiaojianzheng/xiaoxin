package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Used to build element list
 *
 * @author JIAHE
 * @since 1.0
 */
public class Elements {

    /**
     * Inject by reflection
     */
    private AbstractWebDriver webDriver;

    /**
     * Inject by reflection, if you need preloading element.
     */
    private List<WebElement> elements;

    private ElementOperate elementOperate;

    public Elements(By by) {
        this(ElementOperate.location().by(by).build());
    }

    public Elements(ElementOperate elementOperate) {
        this.elementOperate = elementOperate;
    }

    public List<WebElement> getElements() {
        return elements;
    }

}
