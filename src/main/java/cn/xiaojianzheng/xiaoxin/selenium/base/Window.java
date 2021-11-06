package cn.xiaojianzheng.xiaoxin.selenium.base;

import java.util.Set;

/**
 * @author JIAHE
 * @since 1.0
 */
public interface Window {

    String getTitle();

    String getPageSource();

    String getCurrentUrl();

    String getWindowHandle();

    Set<String> getWindowHandles();

    void open(String url);

    void newWindow(String url);

    void newTab(String url);

    void back();

    void refresh();

    void forward();

    void to(String url);

    void getCookies();


}
