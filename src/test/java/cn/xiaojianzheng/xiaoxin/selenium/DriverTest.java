package cn.xiaojianzheng.xiaoxin.selenium;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.*;
import cn.xiaojianzheng.xiaoxin.selenium.driver.builder.DriverBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
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

    protected static ChromeWebDriver chrome() {
        ChromeWebDriver chrome = DriverBuilder.driver()
                .driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).chrome();
        driver = chrome;
        return chrome;
    }

    protected static InternetWebDriver ie() {
        InternetWebDriver ie = DriverBuilder.driver()
                .driverPath(driverPath).globalWaitTime(Duration.ofSeconds(30)).ie();
        driver = ie;
        return ie;
    }

    protected static FirefoxWebDriver firefox() {
        FirefoxWebDriver firefox = DriverBuilder.driver()
                .driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).firefox();
        driver = firefox;
        return firefox;
    }

    protected static EdgeWebDriver edge() {
        EdgeWebDriver edge = DriverBuilder.driver()
                .driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).edge();
        driver = edge;
        return edge;
    }

}
