package cn.xiaojianzheng.xiaoxin.selenium.driver.builder;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.ChromeWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.EdgeWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.FirefoxWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.InternetWebDriver;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Chrome;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Edge;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.Firefox;
import cn.xiaojianzheng.xiaoxin.selenium.driver.module.IE;
import cn.xiaojianzheng.xiaoxin.selenium.exception.DriverNotFoundException;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provides;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;

/**
 * Driver builder
 *
 * @author JIAHE
 * @since 1.0
 */
public class DriverBuilder {

    public static Builder driver() {
        return new Builder();
    }

    public static class Builder {

        private ChromeOptions chromeOptions;
        private FirefoxOptions firefoxOptions;
        private InternetExplorerOptions ieOptions;
        private EdgeOptions edgeOptions;

        private String driverPath;
        private Duration globalWaitTime = Duration.ofSeconds(60);
        private String firefoxBinPath;

        public ChromeWebDriver chrome() {
            if (StrUtil.isNotBlank(driverPath)) {
                System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
            }
            return Guice.createInjector(new AbstractModule() {
                @Provides
                @Chrome
                ChromeWebDriver chromeWebDriver() {
                    return new ChromeWebDriver(driverPath, globalWaitTime, chromeOptions);
                }
            }).getInstance(ChromeWebDriver.class);
        }

        public Builder chromeOptions(ChromeOptions options) {
            this.chromeOptions = options;
            return this;
        }

        public EdgeWebDriver edge() {
            if (StrUtil.isNotBlank(driverPath)) {
                System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, driverPath);
            }
            return Guice.createInjector(new AbstractModule() {
                @Provides
                @Edge
                EdgeWebDriver edge() {
                    return new EdgeWebDriver(driverPath, globalWaitTime, edgeOptions);
                }
            }).getInstance(EdgeWebDriver.class);
        }

        public Builder edgeOptions(EdgeOptions edgeOptions) {
            this.edgeOptions = edgeOptions;
            return this;
        }

        public InternetWebDriver ie() {
            if (StrUtil.isNotBlank(driverPath)) {
                System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
            }
            return new InternetWebDriver(driverPath, globalWaitTime, ieOptions);
        }

        public Builder ieOptions(InternetExplorerOptions options) {
            this.ieOptions = options;
            return this;
        }

        public FirefoxWebDriver firefox() {
            if (StrUtil.isNotBlank(firefoxBinPath)) {
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_XPI_PROPERTY, firefoxBinPath);
            }
            if (StrUtil.isNotBlank(driverPath)) {
                System.setProperty("webdriver.gecko.driver", driverPath);
            }
            return Guice.createInjector(new AbstractModule() {
                @Provides
                @Firefox
                FirefoxWebDriver driver() {
                    return new FirefoxWebDriver(driverPath, globalWaitTime, firefoxOptions);
                }
            }).getInstance(FirefoxWebDriver.class);
        }

        /**
         * 火狐浏览器可执行文件路径
         */
        public Builder firefoxBinPath(String firefoxBinPath) {
            this.firefoxBinPath = firefoxBinPath;
            return this;
        }

        /**
         * firefox options
         */
        public Builder firefoxOptions(FirefoxOptions options) {
            this.firefoxOptions = options;
            return this;
        }

        /**
         * 驱动路径
         *
         * @param driverPath 驱动的路径（建议绝对路径）
         * @return this
         * @see <a href="https://npm.taobao.org/mirrors/chromedriver">chrome</a>
         * @see <a href="https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver">edge</a>
         * @see <a href="https://npm.taobao.org/mirrors/selenium">ie</a>
         * @see <a href="https://npm.taobao.org/mirrors/geckodriver">firefox</a>
         */
        public Builder driverPath(String driverPath) {
            if (FileUtil.exist(driverPath)) {
                this.driverPath = driverPath;
                return this;
            }
            throw new DriverNotFoundException("driver not found: " + driverPath);
        }

        public Builder globalWaitTime(Duration globalWaitTime) {
            if (ObjectUtil.isNotEmpty(globalWaitTime)) {
                this.globalWaitTime = globalWaitTime;
            }
            return this;
        }
    }

}
