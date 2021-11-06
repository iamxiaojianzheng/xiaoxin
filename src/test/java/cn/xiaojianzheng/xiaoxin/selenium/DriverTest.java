package cn.xiaojianzheng.xiaoxin.selenium;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.AbstractWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.ChromeWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.FirefoxWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.builder.DriverBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

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

    protected ChromeWebDriver chrome() {
        driver = DriverBuilder.driver()
                .chrome().driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).build();
        return (ChromeWebDriver) driver;
    }

    protected InternetWebDriver ie() {
        driver = DriverBuilder.driver()
                .ie().driverPath(driverPath).globalWaitTime(Duration.ofSeconds(30)).build();
        return (InternetWebDriver) driver;
    }

    protected FirefoxWebDriver firefox() {
        driver = DriverBuilder.driver()
                .chrome().driverPath(driverPath).globalWaitTime(Duration.ofSeconds(10)).build();
        return (FirefoxWebDriver) driver;
    }

}
