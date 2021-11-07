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
        BaiduPage baiduPage = new BaiduPage(driver);
        baiduPage.open("http://www.baidu.com/");
        // 这里会报错，请使用 PageObjectUtil.wrapPage 包装 BaiduPage
        baiduPage.baidu("java");
    }
}