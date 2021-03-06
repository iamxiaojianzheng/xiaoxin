package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.IE;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.NoLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.PreLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Window;
import com.google.inject.Inject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author JIAHE
 * @since 1.0
 */
@Getter
@Window(titleContains = "百度")
public class BaiduPage extends PageObject<InternetWebDriver> {
    @Inject
    public BaiduPage(@IE InternetWebDriver webDriver) {
        super(webDriver);
    }

    private final Element searchInput = new Element(location().id("kw").clear().build());
    private final Element searchButton = new Element(location().id("su").build());
    private final Element baiduBaike = new Element(location().containsText("a", "(计算机编程语言) - 百度百科").moveToClick());

    public void baidu(String text) {
        searchInput.input(text);
        webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
        this.searchButton.click();
        webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
    }
}
