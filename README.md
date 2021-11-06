# xiaoxin

基于 selenium 的 Page Objects 设计模式测试库。

# Sample1

```java
/**
 * 构建Driver
 * @author JIAHE
 * @since 1.0
 */
public class DriverTest {

    protected static String driverPath;

    protected static AbstractWebDriver driver;

    @BeforeAll
    static void before() {
        InputStream resourceAsStream = ResourceUtil.getStream("IEDriverServer-3.150.1.exe");
        File tempFile = FileUtil.createTempFile("IEDriverServer", ".exe", new File(System.getProperty("user.dir")), true);
        driverPath = tempFile.getAbsolutePath();
        FileUtil.writeFromStream(resourceAsStream, tempFile);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RuntimeUtil.execForLines("tasklist").stream()
                    .filter(line -> line.contains("IEDriverServer")).findFirst()
                    .ifPresent(line -> {
                        String pid = line.split("\\s+")[1];
                        try {
                            RuntimeUtil.exec("taskkill", "/f", "/pid", pid).waitFor();
                        } catch (InterruptedException ignored) {
                        }
                    });
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception ignored) {
            }
            FileUtil.del(tempFile);
        }, "closeTempDriverFile"));
    }

    @AfterAll
    static void after() {
        if (driver != null) driver.quit();
    }

    protected ChromeWebDriver chrome() {
        driver = DriverBuilder.driver()
                .chrome().driverPath(driverPath)
                .globalWaitTime(Duration.ofSeconds(10)).build();
        return (ChromeWebDriver) driver;
    }

    protected InternetWebDriver ie() {
        driver = DriverBuilder.driver()
                .ie().driverPath(driverPath)
                .globalWaitTime(Duration.ofSeconds(30)).build();
        return (InternetWebDriver) driver;
    }

    protected FirefoxWebDriver firefox() {
        driver = DriverBuilder.driver()
                .chrome().driverPath(driverPath)
                .globalWaitTime(Duration.ofSeconds(10)).build();
        return (FirefoxWebDriver) driver;
    }

}
```

```java
/**
 * driver使用案例
 * @author JIAHE
 * @since 1.0
 */
class DriverBuilderTest extends DriverTest {
    @Test
    void test() {
        AbstractWebDriver driver = ie();
        driver.openWindow("http://www.baidu.com/")
                .input(location().id("kw").inputText("java").as("百度搜索框").build())
                .until(visibilityOfElementLocated(ByLocation.className("bdsug")))
                .click(ByLocation.id("su"));
        
        driver.quit();
    }
}
```

# Sample2

```java
/**
 * Page Objects 模式
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PageObjectUtilTest extends DriverTest {

    @Test
    @Order(1)
    void wrapPage() {
        // 开启Page中Element元素预加载
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
                .switchWindowByName("百度百科");

        BaiduBaikePage baiduBaikePage = PageObjectUtil.wrapPage(BaiduBaikePage.class, driver);
        String text = baiduBaikePage.getSummary().getText();
        log.info(text);
    }

    @Getter
    static class BaiduPage extends PageObject {
        protected BaiduPage(AbstractWebDriver webDriver) {
            super(webDriver);
        }
        
        private final Element searchInput = new Element(ElementOperate.location().id("kw").build());
        private final Element searchButton = new Element(ElementOperate.location().id("su").build());

        public void baidu(String text) {
            getSearchInput().input(text);
            webDriver.until(visibilityOfElementLocated(ByLocation.className("bdsug")));
            getSearchButton().click();
            webDriver.until(visibilityOfElementLocated(ByLocation.className("result-op")));
        }
    }

    static class BaiduBaikePage extends PageObject {
        @Getter
        private final Element summary = new Element(location().className("lemma-summary").build());
        protected BaiduBaikePage(AbstractWebDriver webDriver) {
            super(webDriver);
        }
    }

}
```