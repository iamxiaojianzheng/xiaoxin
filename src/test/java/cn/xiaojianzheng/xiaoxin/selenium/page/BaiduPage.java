package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.page.PageObject;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author JIAHE
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class BaiduPage extends PageObject {

    protected BaiduPage(AbstractWebDriver webDriver) {
        super(webDriver);
    }

    private final Element searchInput = new Element(ElementOperate.location().id("kw").build());

    private final Element searchButton = new Element(ElementOperate.location().id("su").build());

    public void baidu(String text) {
        getSearchInput().input(text);
        webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
        getSearchButton().click();
        webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
    }

}
