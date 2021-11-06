package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.DriverTest;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
class PageObjectTest extends DriverTest {

    @Test
    void test() {
        InternetWebDriver driver = ie();
        BaiduPage page = new BaiduPage(driver);
        page.open("http://www.baidu.com/");
        page.baidu("java");
    }

}