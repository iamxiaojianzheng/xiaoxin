package cn.xiaojianzheng.xiaoxin.selenium.driver.builder;

import cn.xiaojianzheng.xiaoxin.selenium.DriverTest;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.location.ByLocation;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author JIAHE
 * @since 1.0
 */
class DriverBuilderTest extends DriverTest {

    @Test
    void test() {
        AbstractWebDriver driver = DriverBuilder.driver()
                .driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).ie();
        driver.openWindow("http://www.baidu.com/")
                .click(location().id("kw1").moveToClick())
                .input(location().id("kw").as("百度搜索框").inputText("java"))
                .until(visibilityOfElementLocated(ByLocation.className("bdsug")))
                .click(ByLocation.id("su"));
        driver.quit();
    }

}