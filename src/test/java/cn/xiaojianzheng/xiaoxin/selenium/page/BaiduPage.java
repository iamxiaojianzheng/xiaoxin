package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.NoLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.PreLoading;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author JIAHE
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@PreLoading
public class BaiduPage extends PageObject {

    protected BaiduPage(AbstractWebDriver webDriver) {
        super(webDriver);
    }

    private final Element searchInput = new Element(location().id("kw").build());

    private final Element searchButton = new Element(location().id("su").build());

    @NoLoading
    private final Element baiduBaike = new Element(location().containsText("a", "(计算机编程语言) - 百度百科").moveToClick().build());

    public void baidu(String text) {
        this.searchInput.input(text);
        webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
        this.searchButton.click();
        webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
    }

}
