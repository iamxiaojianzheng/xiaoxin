package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.IE;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Window;
import com.google.inject.Inject;
import lombok.Getter;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;

/**
 * @author JIAHE
 * @since 1.0
 */
@Getter
@Window(titleContains = "百度百科")
public class BaiduBaikePage extends PageObject<InternetWebDriver> {
    private final Element summary = new Element(location().className("lemma-summary").build());

    @Inject
    protected BaiduBaikePage(@IE InternetWebDriver webDriver) {
        super(webDriver);
    }

    public String getSummary() {
        return summary.getWebElement().getText();
    }
}