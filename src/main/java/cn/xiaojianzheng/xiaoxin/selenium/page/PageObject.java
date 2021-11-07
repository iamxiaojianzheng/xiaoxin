package cn.xiaojianzheng.xiaoxin.selenium.page;

import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;

/**
 * base page object
 * <p>
 *
 * use case:
 * <code>
 * public class BaiduPage extends PageObject {
 *
 *     // Need to explicitly declare the constructor
 *     protected BaiduPage(AbstractWebDriver webDriver) {
 *         super(webDriver);
 *     }
 *
 *     // if PageObjectUtil.enablePreloading = true
 *     // The following ElementBehavior or Elements will be pre-searched after Page is initialized
 *     private final ElementBehavior searchInput = new ElementBehavior(ElementOperate.location().id("kw").build());
 *     private final ElementBehavior searchButton = new ElementBehavior(ElementOperate.location().id("su").build());
 *
 *     public void baidu(String text) {
 *         getSearchInput().input(text);
 *         webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
 *         getSearchButton().click();
 *         webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
 *     }
 * }
 * </code>
 *
 * @author JIAHE
 * @since 1.0
 */
public abstract class PageObject {

    protected final AbstractWebDriver webDriver;

    protected PageObject(AbstractWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected void open(String url) {
        webDriver.openWindow(url);
    }

    protected String getTitle() {
        return webDriver.getTitle();
    }

    protected String getUrl() {
        return webDriver.getCurrentUrl();
    }

}
