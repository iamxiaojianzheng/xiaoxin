package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.DriverTest;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.IE;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.NoLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.PreLoading;
import cn.xiaojianzheng.xiaoxin.selenium.page.annotation.Window;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PageObjectUtilTest extends DriverTest {

    private static InternetWebDriver ie;

    private static BaiduPage baiduPage;

    private static BaiduBaikePage baiduBaikePage;

    @Test
    @Order(1)
    void step1() {
        ie = ie();
        baiduPage = PageObjectUtil.wrapPage(BaiduPage.class, ie);
        baiduPage.open("http://www.baidu.com/");
        baiduPage.baidu("java");
        Element baiduBaike = baiduPage.getBaiduBaike();
        driver.scrollToElement(baiduBaike.getWebElement());
        baiduBaike.click();
    }

    @Test
    @Order(2)
    void step2() {
        baiduBaikePage = PageObjectUtil.wrapPage(BaiduBaikePage.class, ie);
        String summary = baiduBaikePage.getSummary();
        log.info(summary);
        baiduBaikePage.close();
    }

    @Test
    @Order(2)
    void step3() {
        baiduPage.baidu("python");
    }

    @Getter
    @PreLoading
    @Window("百度")
    static class BaiduPage extends PageObject {
        @Inject
        public BaiduPage(@IE InternetWebDriver webDriver) {
            super(webDriver);
        }

        private final Element searchInput = new Element(location().id("kw").clear().build());
        private final Element searchButton = new Element(location().id("su").build());
        @NoLoading
        private final Element baiduBaike = new Element(location().containsText("a", "(计算机编程语言) - 百度百科").moveToClick().build());

        public void baidu(String text) {
            searchInput.input(text);
            webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
            this.searchButton.click();
            webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
        }
    }

    @Getter
    @PreLoading
    @Window("百度百科")
    static class BaiduBaikePage extends PageObject {
        private final Element summary = new Element(location().className("lemma-summary").build());

        @Inject
        protected BaiduBaikePage(@IE InternetWebDriver webDriver) {
            super(webDriver);
        }

        public String getSummary() {
            return summary.getWebElement().getText();
        }
    }

}