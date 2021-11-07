package cn.xiaojianzheng.xiaoxin.selenium.base;

import org.openqa.selenium.Cookie;

import java.util.Set;

/**
 * Behavior interface of the window
 *
 * @author JIAHE
 * @since 1.0
 */
public interface WindowBehavior<T> {

    /**
     * 在历史纪录上先前移动一个单位
     */
    T back();

    /**
     * 刷新当前页面
     */
    T refresh();

    /**
     * 在历史纪录上先前移动一个单位，如果当前处于最新记录，则啥都不做。
     */
    T forward();

    /**
     * 在当前浏览器窗口中加载一个新网页
     * 这是使用 HTTP POST 操作完成的，该方法将阻塞，直到加载完成。
     * 这将遵循服务器发出的重定向或作为返回 HTML 中的元重定向发出的重定向。
     * 如果元重定向在任何持续时间内“休息”，最好等到此超时结束，
     * 因为如果您在测试执行时基础页面发生变化，未来对此接口的调用结果将与新加载的结果有所不同。
     *
     * @param url 要加载的 URL。 必须是完全限定的 URL
     */
    T to(String url);

    /**
     * 获取当前页面的标题
     */
    String getTitle();

    /**
     * 获取上次加载页面的源代码
     * <p>
     * 如果页面在加载后被修改（例如，通过 Javascript），则无法保证返回的文本是修改后的页面的文本。
     */
    String getPageSource();

    /**
     * 获取当前窗口的URL
     */
    String getCurrentUrl();

    /**
     * 获取当前具有焦点的窗口句柄
     */
    String getWindowHandle();

    /**
     * 获取所有的窗口句柄
     */
    Set<String> getWindowHandles();

    /**
     * 获取当前域的所有 cookie
     */
    Set<Cookie> getCookies();

    /**
     * 获取具有给定名称的 cookie
     *
     * @param cookieName cookie 的名称
     */
    Cookie getCookies(String cookieName);

    /**
     * 添加特定的 cookie。 如果 cookie 的域名留空，则假定 cookie 用于当前文档的域。
     *
     * @param cookie 要添加的 cookie
     */
    T addCookie(Cookie cookie);

    /**
     * 从当前域中删除命名的 cookie。 这相当于将命名 cookie 的到期日期设置为过去的某个时间。
     *
     * @param cookieName 要删除的 cookie 的名称
     */
    T deleteCookie(String cookieName);

    /**
     * 从浏览器的“cookie jar”中删除一个cookie。 cookie 的域将被忽略。
     *
     * @param cookie 待删除的Cookie对象
     */
    T deleteCookie(Cookie cookie);

    /**
     * 删除当前域的所有 cookie
     */
    T deleteAllCookies();

    /**
     * 在当前浏览器窗口加载新的网页。这是一个HTTP POST操作，并且该方法将阻塞，直到加载完成（使用默认的“页面加载策略”。
     * 这将遵循服务器发出的重定向或作为返回HTML中的元重定向发出的重定向。
     * 如果元重定向在任何持续时间内“休息”，最好等到此超时结束，
     * 因为如果在您的测试执行时基础页面发生变化，未来对此接口的调用结果将与新加载的结果有所不同。
     * 相似的方法 {@link org.openqa.selenium.WebDriver.Navigation#to(String)}.
     *
     * @param url 要加载的 URL。必须是完全限定的 URL
     * @see org.openqa.selenium.PageLoadStrategy
     */
    T openWindow(String url);

    /**
     * 创建一个新的浏览器窗口，并且将焦点切换到它上，以便于之后在这个新窗口上执行命令。
     *
     * @param url 要加载的 URL。必须是完全限定的 URL
     */
    T newWindow(String url);

    /**
     * 创建一个新的浏览器标签页，并且将焦点切换到它上，以便于之后在这个新标签页上执行命令。
     *
     * @param url 要加载的 URL。必须是完全限定的 URL
     */
    T newTab(String url);

    /**
     * 如果当前窗口尚未最大化，则最大化当前窗口
     */
    T maximize();

    /**
     * 如果尚未最小化，则最小化当前窗口
     */
    T minimize();

    /**
     * 关闭当前窗口，如果它是当前打开的最后一个窗口，则退出浏览器。
     */
    T close();

    /**
     * 关闭当前窗口并将此驱动程序的未来命令的焦点切换到具有给定名称句柄的窗口。
     */
    T closeAndSwitch(String nameOrHandle);

    /**
     * 退出这个驱动程序，关闭所有相关的窗口
     */
    void quit();

}
