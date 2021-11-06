package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.DriverTest;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PageObjectUtilTest extends DriverTest {

    @Test
    @Order(1)
    void wrapPage() {
        PageObjectUtil.enablePreloading = true;

        InternetWebDriver driver = ie();
        BaiduPage baiduPage = PageObjectUtil.wrapPage(BaiduPage.class, "http://www.baidu.com/", driver);
        baiduPage.baidu("java");
    }

    @Test
    @Order(2)
    void testWrapPage() {
        int size = driver.getWindowNumber();
        driver.click(location().containsText("a", "(计算机编程语言) - 百度百科").jsClick().build())
                .until(visibilityOfAllElementsLocatedBy(ByLocation.containsText("a", "")));
//                .switchWindowByName("百度百科");

        BaiduBaikePage baiduBaikePage = PageObjectUtil.wrapPage(BaiduBaikePage.class, driver);
        String text = baiduBaikePage.getSummary().getText();
        log.info(text);
    }

    static class BaiduBaikePage extends PageObject {

        @Getter
        private final Element summary = new Element(location().className("lemma-summary").build());

        protected BaiduBaikePage(AbstractWebDriver webDriver) {
            super(webDriver);
        }

    }

}