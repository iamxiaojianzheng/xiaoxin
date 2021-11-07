package cn.xiaojianzheng.xiaoxin.selenium.driver.module;

import cn.hutool.core.io.FileUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.ChromeWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.EdgeWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.FirefoxWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.builder.DriverBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * @author JIAHE
 * @since 1.0
 */
public class DriverModule extends AbstractModule {

    @Override
    protected void configure() {
        try (FileInputStream inputStream = new FileInputStream("driver.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException("加载driver.properties失败");
        }
    }

    @Provides
    @Chrome
    public ChromeWebDriver chromeWebDriver(@Named("chrome.driver.path") String driverPath,
                                           @Named("chrome.wait.timeout") String timeout) {
        return DriverBuilder.driver()
                .driverPath(driverPath)
                .globalWaitTime(Duration.parse(timeout))
                .chrome();
    }

    @Provides
    @IE
    public InternetWebDriver internetWebDriver(@Named("ie.driver.path") String driverPath,
                                               @Named("ie.wait.timeout") String timeout) {
        return DriverBuilder.driver()
                .driverPath(driverPath)
                .globalWaitTime(Duration.parse(timeout))
                .ie();
    }

    @Provides
    @Edge
    public EdgeWebDriver edgeWebDriver(@Named("edge.driver.path") String driverPath,
                                       @Named("edge.wait.timeout") String timeout) {
        return DriverBuilder.driver()
                .driverPath(driverPath)
                .globalWaitTime(Duration.parse(timeout))
                .edge();
    }

    @Provides
    @Firefox
    public FirefoxWebDriver firefoxWebDriver(@Named("firefox.driver.path") String driverPath,
                                             @Named("firefox.wait.timeout") String timeout) {
        return DriverBuilder.driver()
                .driverPath(driverPath)
                .globalWaitTime(Duration.parse(timeout))
                .firefox();
    }

}
