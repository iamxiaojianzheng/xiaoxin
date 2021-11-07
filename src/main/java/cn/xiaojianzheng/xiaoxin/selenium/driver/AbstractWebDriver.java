package cn.xiaojianzheng.xiaoxin.selenium.driver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.base.ElementBehavior;
import cn.xiaojianzheng.xiaoxin.selenium.base.UntilCondition;
import cn.xiaojianzheng.xiaoxin.selenium.base.WindowBehavior;
import cn.xiaojianzheng.xiaoxin.selenium.listener.*;
import cn.xiaojianzheng.xiaoxin.selenium.listener.decorator.WindowDecorator;
import cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static cn.xiaojianzheng.xiaoxin.selenium.location.ElementOperate.location;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Base class for all implementations of drivers
 *
 * @author JIAHE
 * @since 1.0
 */
@SuppressWarnings("unused")
public abstract class AbstractWebDriver
        implements WindowBehavior<AbstractWebDriver>, ElementBehavior<AbstractWebDriver>, UntilCondition<AbstractWebDriver> {

    private final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(60);

    private final Duration DEFAULT_SLEEP = Duration.ofSeconds(1);

    @Getter
    private final WebDriver webDriver;

    @Getter
    private final Actions actions;

    @Getter
    private final WebDriver.Navigation navigation;

    @Getter
    private WebDriverWait defaultWebDriverWait;

    private final JavascriptExecutor javascriptExecutor;

    protected AbstractWebDriver(WebDriver webDriver) {
        WebDriverListener[] listeners = new WebDriverListener[]{
                new WebDriverLogEventListener(), new ErrorEventListener(), new FocusColorListener(),
                new TimeoutsLogEventListener(), new AlertLogEventListener(), new WindowLogEventListener(),
                new NavigationLogEventListener(), new WebElementLogEventListener(), new OptionsLogEventListener()
        };
        EventFiringDecorator eventFiringDecorator = new EventFiringDecorator(listeners);

        this.webDriver = eventFiringDecorator.decorate(webDriver);
        this.actions = new Actions(this.webDriver);
        this.navigation = this.webDriver.navigate();
        this.javascriptExecutor = ((JavascriptExecutor) this.webDriver);
    }

    protected void setGlobalWaitTime(Duration globalWaitTime) {
        this.defaultWebDriverWait = new WebDriverWait(this.webDriver, globalWaitTime, this.DEFAULT_SLEEP);
    }

    protected void setGlobalWaitTime(Duration globalWaitTime, Duration sleep) {
        this.defaultWebDriverWait = new WebDriverWait(this.webDriver, globalWaitTime, sleep);
    }

    // ================= until =================

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * <ol>
     * <li>该函数既不返回 null 也不返回 false</li>
     * <li>该函数抛出一个未忽略的异常</li>
     * <li>超时到期</li>
     * <li>当前线程被中断</li>
     * </ol>
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     * @return This driver focused on the given window
     */
    public <T> AbstractWebDriver until(ExpectedCondition<T> expectedCondition) {
        this.defaultWebDriverWait.until(expectedCondition);
        return this;
    }

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     * @return 如果函数在超时到期之前返回了不同于 null 或 false 的内容，则该函数的返回值。
     */
    public <T> T untilReturn(ExpectedCondition<T> expectedCondition) {
        return this.defaultWebDriverWait.until(expectedCondition);
    }

    /**
     * 如果条件成立，则执行后续流程。
     *
     * @param expectedCondition 预期条件表达式
     * @param ifTrue            条件成立下执行{@code ifTrue.done()}
     * @param <T>               函数的预期返回类型
     */
    public <T> AbstractWebDriver untilDoIfTrue(ExpectedCondition<T> expectedCondition, ConditionDo ifTrue) {
        try {
            this.defaultWebDriverWait.until(expectedCondition);
            ifTrue.done();
        } catch (Exception ignored) {
        }
        return this;
    }

    /**
     * 条件成立与否，都会执行后续流程。
     *
     * @param expectedCondition 预期条件表达式
     * @param ifTrue            条件成立下执行{@code ifTrue.done()}，否则执行{@code ifFalse.done()}
     * @param <T>               函数的预期返回类型
     */
    public <T> AbstractWebDriver untilDo(ExpectedCondition<T> expectedCondition, ConditionDo ifTrue, ConditionDo ifFalse) {
        try {
            this.defaultWebDriverWait.until(expectedCondition);
            ifTrue.done();
        } catch (Exception e) {
            ifFalse.done();
        }
        return this;
    }

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * 如果条件成立，则执行action操作。<b>忽略异常</b>
     *
     * @param expectedCondition 预期条件表达式
     * @param <T>               函数的预期返回类型
     */
    public <T> void untilDo(ExpectedCondition<T> expectedCondition, Consumer<T> action) {
        Objects.requireNonNull(action);
        T t = null;
        try {
            t = this.defaultWebDriverWait.until(expectedCondition);
        } catch (Exception ignored) {
        }
        action.accept(t);
    }

    /**
     * 重复将此实例的输入值应用于给定函数，直到发生以下情况之一：
     * 如果条件成立，则执行action操作。<b>忽略异常</b>
     *
     * @param expectedCondition 预期条件表达式
     * @param <V>               函数的预期返回类型
     * @return 如果函数在超时到期之前返回了不同于 null 或 false 的内容，则该函数的返回值。
     */
    public <T, V> V untilDoAndReturn(ExpectedCondition<T> expectedCondition, Function<? super T, V> action) {
        try {
            T result = this.defaultWebDriverWait.until(expectedCondition);
            return action.apply(result);
        } catch (Exception ignored) {
        }
        return null;
    }

    // ========== open windows / tab ===========

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
    public AbstractWebDriver openWindow(String url) {
        webDriver.get(url);
        return this;
    }

    /**
     * 创建一个新的浏览器窗口，并且将焦点切换到它上，以便于之后在这个新窗口上执行命令。
     *
     * @param url 要加载的 URL。必须是完全限定的 URL
     */
    public AbstractWebDriver newWindow(String url) {
        webDriver.switchTo().newWindow(WindowType.WINDOW).get(url);
        return this;
    }

    /**
     * 创建一个新的浏览器标签页，并且将焦点切换到它上，以便于之后在这个新标签页上执行命令。
     *
     * @param url 要加载的 URL。必须是完全限定的 URL
     */
    public AbstractWebDriver newTab(String url) {
        webDriver.switchTo().newWindow(WindowType.TAB).get(url);
        return this;
    }

    /**
     * 如果当前窗口尚未最大化，则最大化当前窗口
     */
    public AbstractWebDriver maximize() {
        webDriver.manage().window().maximize();
        return this;
    }

    /**
     * 如果尚未最小化，则最小化当前窗口
     */
    public AbstractWebDriver minimize() {
        webDriver.manage().window().minimize();
        return this;
    }

    // ================= close =================

    /**
     * 关闭当前窗口，如果它是当前打开的最后一个窗口，则退出浏览器。
     *
     * @return This driver focused on the given window
     */
    public AbstractWebDriver close() {
        webDriver.close();
        return this;
    }

    /**
     * 关闭当前窗口并将此驱动程序的未来命令的焦点切换到具有给定名称句柄的窗口。
     *
     * @param nameOrHandle {@link WebDriver#getWindowHandle()}返回的窗口或句柄的名称
     * @throws NoSuchWindowException 如果找不对对应的窗口
     */
    public AbstractWebDriver closeAndSwitch(String nameOrHandle) {
        return close().switchWindow(nameOrHandle);
    }

    /**
     * 退出这个驱动程序，关闭所有相关的窗口
     */
    public void quit() {
        webDriver.quit();
    }

    // ============= switch windows ============

    /**
     * 将此驱动程序的后续执行命令的焦点切换到具有给定名称句柄的窗口。
     *
     * @param nameOrHandle {@link WebDriver#getWindowHandle()}返回的窗口或句柄的名称
     * @throws NoSuchWindowException 如果窗口不存在则抛出异常
     */
    public AbstractWebDriver switchWindow(String nameOrHandle) {
        webDriver.switchTo().window(nameOrHandle);
        return this;
    }

    /**
     * 将此驱动程序的后续执行命令的焦点切换到具有给定URL关键字的窗口。
     *
     * @param urlKeyword url关键字，根据包含关系进行匹配。
     */
    public AbstractWebDriver switchWindowByUrl(String urlKeyword) {
        Set<String> windowHandles = webDriver.getWindowHandles();
        for (String handle : windowHandles) {
            WebDriver window = webDriver.switchTo().window(handle);
            String currentUrl = window.getCurrentUrl();
            if (currentUrl.contains(urlKeyword)) {
                return this;
            }
        }
        throw new NoSuchWindowException("Cannot find the window whose url contains keyword");
    }

    /**
     * 将此驱动程序的后续执行命令的焦点切换到具有给定URL关键字的窗口。
     *
     * @param nameKeyword {@link WebDriver#getTitle()}窗口名称关键字，根据包含关系进行匹配。
     */
    public AbstractWebDriver switchWindowByName(String nameKeyword) {
        Set<String> windowHandles = webDriver.getWindowHandles();
        for (String handle : windowHandles) {
            WebDriver window = webDriver.switchTo().window(handle);
            String title = window.getTitle();
            if (title.contains(nameKeyword)) {
                return this;
            }
        }
        throw new NoSuchWindowException(StrUtil.format("Cannot find the window whose title contains keyword[%s] ", nameKeyword));
    }

    // ============== switch iframe ==============

    /**
     * 根据name属性或id属性切换iframe，name属性优先。
     *
     * @param nameOrId name属性或id属性值
     */
    public AbstractWebDriver switchIframe(String nameOrId) {
        webDriver.switchTo().frame(nameOrId);
        return this;
    }

    /**
     * 根据索引切换iframe，索引从0开始。 如果存在多个索引，则一层层往下切换。
     *
     * @param indexList 索引列表
     */
    public AbstractWebDriver switchIframe(Integer... indexList) {
        for (Integer i : indexList) {
            webDriver.switchTo().frame(i);
        }
        return this;
    }

    /**
     * 根据{@code WebElement}切换iframe
     *
     * @param webElement iframe元素
     */
    public AbstractWebDriver switchIframe(WebElement webElement) {
        webDriver.switchTo().frame(webElement);
        return this;
    }

    // ================= click =================

    /**
     * 单击此元素。 如果这会导致加载新页面，则应放弃对此元素的所有引用，并且对该元素执行的任何进一步操作都将引发 {@link StaleElementReferenceException}。
     * 请注意，如果 click() 是通过发送本机事件（这是大多数浏览器/平台上的默认值）完成的，则该方法将_不_等待下一页加载，调用者应自行验证。
     * 单击元素有一些先决条件。 元素必须是可见的，并且它的高度和宽度必须大于 0。
     */
    public AbstractWebDriver click(By by) {
        return click(location().by(by).build());
    }

//    /**
//     * 单击此元素，详情查看{@link AbstractWebDriver#click(By by)}方法。
//     */
//    public AbstractWebDriver click(ElementOperate elementOperate) {
//    }

    /**
     * 单击此元素，详情查看{@link AbstractWebDriver#click(By by)}方法。
     */
    public AbstractWebDriver click(ElementOperate elementOperate) {
        WebElement element = findElement(elementOperate);
        int clickTimes = Math.max(elementOperate.getClickTimes(), 1);

        for (int i = 0; i < clickTimes; i++) {
            if (elementOperate.isJsClick()) {
                this.executeJs("arguments[0].click()", element);

            } else {
                By by = elementOperate.getBy();
                this.defaultWebDriverWait.until(presenceOfElementLocated(by));      // 元素是否在页面，不一定可见
                this.defaultWebDriverWait.until(elementToBeClickable(by));          // 元素是否可见，需要有宽高，元素是否可点击

                if (elementOperate.isActionClick()) {
                    this.actions.click(element).perform();

                } else if (elementOperate.isDoubleClick()) {
                    this.actions.doubleClick(element).perform();

                } else if (elementOperate.isMoveToClick()) {
                    this.actions.moveToElement(element).perform();
                    this.scrollToElement(element);
                    this.actions.click().perform();

                } else if (elementOperate.isRightClick()) {
                    this.actions.contextClick(element).perform();

                } else {
                    element.click();
                }
            }
        }

        return this;
    }

    // ================= clear / input =================

    /**
     * 如果此元素是表单条目元素，则将重置其值。
     */
    public AbstractWebDriver clear(By by) {
        return clear(location().by(by).build());
    }

    /**
     * 如果此元素是表单条目元素，则将重置其值。
     */
    public AbstractWebDriver clear(ElementOperate elementOperate) {
        WebElement element = findElement(elementOperate);
        element.clear();
        return this;
    }

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     */
    public AbstractWebDriver input(By by, String text) {
        return input(location().by(by).inputText(text).build());
    }

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     */
    public AbstractWebDriver input(ElementOperate elementOperate) {
        WebElement element = findElement(elementOperate);

        boolean removeReadonly = elementOperate.isRemoveReadonly();
        if (removeReadonly) {
            javascriptExecutor.executeScript("arguments[0].removeAttribute('readonly')", element);
        }

        boolean clear = elementOperate.isClear();
        if (clear) {
            element.clear();
        }

        return input(element, elementOperate.getInputText());
    }

    /**
     * 使用此方法模拟输入元素，这可能会设置其值。
     *
     * @param element   元素
     * @param inputText 带输入的文本内容
     */
    public AbstractWebDriver input(WebElement element, String inputText) {
        element.sendKeys(inputText);
        return this;
    }

    // ============== find element ==============

    /**
     * 使用给定的方法查找第一个{@link WebElement} 。 此方法受执行时有效的“隐式等待”时间的影响。
     * findElement(..) 调用将返回匹配的行，或者重复尝试直到达到配置的超时。
     * findElement 不应用于查找不存在的元素，而应使用findElements(By)并断言零长度响应。
     *
     * @param by 要使用的定位机制
     * @return 当前页面的第一个匹配元素
     */
    public WebElement findElement(By by) {
        return findElement(location().by(by).build());
    }

    /**
     * 使用给定的机制查找当前页面中的所有元素。 此方法受执行时有效的“隐式等待”时间的影响。
     * 当隐式等待时，只要找到的集合中有 0 个以上的项目，此方法就会返回，或者如果超时将返回一个空列表。
     *
     * @param by 要使用的定位机制
     * @return 所有匹配的WebElement的列表，如果没有匹配，则为空列表
     */
    public List<WebElement> findElements(By by) {
        return findElements(location().by(by).build());
    }

    /**
     * 查找页面元素，匹配第一个。（详情查看 {@link AbstractWebDriver#findElement(By) })
     *
     * @param elementOperate 元素操作规则
     * @return 当前页面的第一个匹配元素
     */
    public WebElement findElement(ElementOperate elementOperate) {
        By by = elementOperate.getBy();
        if (elementOperate.isNoWait()) {
            return webDriver.findElement(by);
        }
        return getWebDriverWait(elementOperate).until(driver -> webDriver.findElement(by));
    }

    /**
     * 查找页面元素，匹配所有。（详情查看 {@link AbstractWebDriver#findElements(By)}）
     *
     * @param elementOperate 元素操作规则
     * @return 所有匹配的WebElement的列表，如果没有匹配，则为空列表
     */
    public List<WebElement> findElements(ElementOperate elementOperate) {
        By by = elementOperate.getBy();
        if (elementOperate.isNoWait()) {
            return webDriver.findElements(by);
        }
        return getWebDriverWait(elementOperate).until(driver -> webDriver.findElements(by));
    }

    // ================= scroll =================

    /**
     * 向上滚动
     *
     * @param distance 滚动的距离
     */
    public AbstractWebDriver scrollUp(int distance) {
        return this.executeJs("window.scrollBy(0, -" + distance + ")");
    }

    /**
     * 滚动到窗口顶部（不支持IE）
     */
    public AbstractWebDriver scrollToTop() {
        return this.executeJs("window.scrollTo({ behavior: 'smooth', left: 0, top: 0})");
    }

    /**
     * 向上滚动
     *
     * @param distance 滚动的距离
     */
    public AbstractWebDriver scrollDown(int distance) {
        return this.executeJs("window.scrollBy(0, " + distance + ")");
    }

    /**
     * 滚动到窗口底部（不支持IE）
     */
    public AbstractWebDriver scrollToBottom() {
        return this.executeJs("window.scrollTo({ behavior: 'smooth', left: 0, top: Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight)})");
    }

    /**
     * 滚动到指定元素
     *
     * @param by 定位器
     */
    public AbstractWebDriver scrollToElement(By by) {
        WebElement element = findElement(by);
        return scrollToElement(element);
    }

    public AbstractWebDriver scrollToElement(WebElement element) {
        return this.executeJs("arguments[0].scrollIntoView(false)", element);
    }

    // =============== execute js ===============

    /**
     * 在当前选定的框架或窗口的上下文中执行 JavaScript。 提供的脚本片段将作为匿名函数的主体执行。
     * 在脚本中，使用document来引用当前文档。 请注意，一旦脚本完成执行，局部变量将不可用，但全局变量将持续存在。
     * 如果脚本有返回值（即如果脚本包含return语句），则将采取以下步骤：
     * <ul>
     * <li>对于 HTML 元素，此方法返回一个 WebElement
     * <li>对于小数，返回一个 Double
     * <li>对于非十进制数，返回一个 Long
     * <li>对于布尔值，返回一个布尔值
     * <li>对于所有其他情况，返回一个字符串。对于数组，返回一个 {@code List<Object>}，每个对象都遵循上述规则。 我们支持嵌套列表。
     * <li>对于地图，返回具有遵循上述规则的值的 {@code Map<String, Object> }。
     * <li>除非值为null或者没有返回值，其中返回null
     * </ul>
     * 参数必须是数字、布尔值、字符串、WebElement 或上述任意组合的列表。 如果参数不满足这些条件，则会抛出异常。 参数将通过“arguments”魔法变量提供给 JavaScript，就像通过“Function.apply”调用函数一样
     *
     * @param js 要执行的 JavaScript
     */
    public AbstractWebDriver executeJs(String js) {
        javascriptExecutor.executeScript(js);
        return this;
    }

    /**
     * 执行JavaScript，并返回执行结果。（详情查看{@link AbstractWebDriver#executeJs(String)}方法）
     *
     * @param js 待执行的脚本代码
     * @return Boolean、Long、Double、String、List、Map 或 WebElement 之一。 或为空。
     */
    public Object executeJsAndReturn(String js) {
        return javascriptExecutor.executeScript(js);
    }

    /**
     * 执行JavaScript。（详情查看{@link AbstractWebDriver#executeJs(String)}方法）
     *
     * @param js   待执行的脚本代码
     * @param args 脚本的参数。 可能是空的
     * @return Boolean、Long、Double、String、List、Map 或 WebElement 之一。 或为空。
     */
    public AbstractWebDriver executeJs(String js, Object... args) {
        javascriptExecutor.executeScript(js, args);
        return this;
    }

    /**
     * 执行JavaScript，并返回执行结果。（详情查看{@link AbstractWebDriver#executeJs(String)}方法）
     *
     * @param js 待执行的脚本代码
     * @return Boolean、Long、Double、String、List、Map 或 WebElement 之一。 或为空。
     */
    public Object executeJsAndReturn(String js, Object... args) {
        return javascriptExecutor.executeScript(js, args);
    }

    // ================= alert ==================

    public AbstractWebDriver alert(boolean accept) {
        return accept ? alert(location().acceptAlert().build()) : alert(location().dismissAlert().build());
    }

    public AbstractWebDriver alert(ElementOperate elementOperate) {
        WebDriverWait webDriverWait = getWebDriverWait(elementOperate);
        Alert alert = webDriverWait.until(alertIsPresent());
        if (elementOperate.isAcceptAlert()) alert.accept();
        else alert.dismiss();
        return this;
    }

    // =============== navigation ===============

    /**
     * 在历史纪录上先前移动一个单位
     */
    public AbstractWebDriver back() {
        this.navigation.back();
        return this;
    }

    /**
     * 刷新当前页面
     */
    public AbstractWebDriver refresh() {
        this.navigation.refresh();
        return this;
    }

    /**
     * 在历史纪录上先前移动一个单位，如果当前处于最新记录，则啥都不做。
     */
    public AbstractWebDriver forward() {
        this.navigation.forward();
        return this;
    }

    /**
     * 在当前浏览器窗口中加载一个新网页
     * 这是使用 HTTP POST 操作完成的，该方法将阻塞，直到加载完成。
     * 这将遵循服务器发出的重定向或作为返回 HTML 中的元重定向发出的重定向。
     * 如果元重定向在任何持续时间内“休息”，最好等到此超时结束，
     * 因为如果您在测试执行时基础页面发生变化，未来对此接口的调用结果将与新加载的结果有所不同。
     *
     * @param url 要加载的 URL。 必须是完全限定的 URL
     */
    public AbstractWebDriver to(String url) {
        this.navigation.to(url);
        return this;
    }

    // ================== get ===================

    /**
     * 获取当前页面的标题
     */
    public String getTitle() {
        return webDriver.getTitle();
    }

    /**
     * 获取上次加载页面的源代码
     * <p>
     * 如果页面在加载后被修改（例如，通过 Javascript），则无法保证返回的文本是修改后的页面的文本。
     */
    public String getPageSource() {
        return webDriver.getPageSource();
    }

    /**
     * 获取当前窗口的URL
     */
    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    /**
     * 获取当前具有焦点的窗口句柄
     */
    public String getWindowHandle() {
        return webDriver.getWindowHandle();
    }

    /**
     * 获取所有的窗口句柄
     */
    public Set<String> getWindowHandles() {
        return webDriver.getWindowHandles();
    }

    /**
     * 获取当前上下文环境中，窗口的总数量
     */
    public int getWindowNumber() {
        return webDriver.getWindowHandles().size();
    }

    // ================== cookie ===================

    /**
     * 获取当前域的所有 cookie
     */
    public Set<Cookie> getCookies() {
        return webDriver.manage().getCookies();
    }

    /**
     * 获取具有给定名称的 cookie
     *
     * @param cookieName cookie 的名称
     */
    public Cookie getCookies(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    /**
     * 添加特定的 cookie。 如果 cookie 的域名留空，则假定 cookie 用于当前文档的域。
     *
     * @param cookie 要添加的 cookie
     */
    public AbstractWebDriver addCookie(Cookie cookie) {
        webDriver.manage().addCookie(cookie);
        return this;
    }

    /**
     * 从当前域中删除命名的 cookie。 这相当于将命名 cookie 的到期日期设置为过去的某个时间。
     *
     * @param cookieName 要删除的 cookie 的名称
     */
    public AbstractWebDriver deleteCookie(String cookieName) {
        webDriver.manage().deleteCookieNamed(cookieName);
        return this;
    }

    /**
     * 从浏览器的“cookie jar”中删除一个cookie。 cookie 的域将被忽略。
     *
     * @param cookie 待删除的Cookie对象
     */
    public AbstractWebDriver deleteCookie(Cookie cookie) {
        webDriver.manage().deleteCookie(cookie);
        return this;
    }

    /**
     * 删除当前域的所有 cookie
     */
    public AbstractWebDriver deleteAllCookies() {
        webDriver.manage().deleteAllCookies();
        return this;
    }

    // ============== screenshot =================

    /**
     * 截图保存为文件
     *
     * @param file 文件的绝对路径，包含name和ext。
     */
    public void screenshotAsFile(File file) {
        byte[] bytes = webDriver.findElement(By.tagName("body")).getScreenshotAs(OutputType.BYTES);
        FileUtil.writeBytes(bytes, file);
    }

    // =============== private method ============

    private WebDriverWait getWebDriverWait(ElementOperate elementOperate) {
        Duration waitTime = elementOperate.getWaitTime();
        Duration pollTime = elementOperate.getPollTime();
        // 如果这两个条件都为空，就用默认的
        if (ObjectUtil.isAllEmpty(waitTime, pollTime)) {
            return this.defaultWebDriverWait;
        }
        waitTime = ObjectUtil.isEmpty(waitTime) ? DEFAULT_WAIT_TIME : waitTime;
        pollTime = ObjectUtil.isEmpty(pollTime) ? DEFAULT_SLEEP : pollTime;
        return new WebDriverWait(webDriver, waitTime, pollTime);
    }

}
