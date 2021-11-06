package cn.xiaojianzheng.xiaoxin.selenium.driver.builder;

import cn.hutool.core.util.StrUtil;
import cn.xiaojianzheng.xiaoxin.selenium.driver.*;
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

        private String driverType;
        private String driverPath;
        private Duration globalWaitTime = Duration.ofSeconds(60);
        private String firefoxBinPath;

        public Builder chrome() {
            this.driverType = "chrome";
            return this;
        }

        public Builder chromeOptions(ChromeOptions options) {
            this.chromeOptions = options;
            return this;
        }

        public Builder edge() {
            this.driverType = "edge";
            return this;
        }

        public Builder edgeOptions(EdgeOptions edgeOptions) {
            this.edgeOptions = edgeOptions;
            return this;
        }

        public Builder ie() {
            this.driverType = "ie";
            return this;
        }

        public Builder ieOptions(InternetExplorerOptions options) {
            this.ieOptions = options;
            return this;
        }

        public Builder firefox() {
            this.driverType = "firefox";
            return this;
        }

        public Builder firefoxBinPath(String firefoxBinPath) {
            this.firefoxBinPath = firefoxBinPath;
            return this;
        }

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
            this.driverPath = driverPath;
            return this;
        }

        public Builder globalWaitTime(Duration globalWaitTime) {
            this.globalWaitTime = globalWaitTime;
            return this;
        }

        public AbstractWebDriver build() {
            AbstractWebDriver driver;
            switch (this.driverType) {
                case "chrome":
                    if (StrUtil.isNotBlank(driverPath)) {
                        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
                    }
                    driver = new ChromeWebDriver(driverPath, globalWaitTime, chromeOptions);
                    break;

                case "edge":
                    if (StrUtil.isNotBlank(driverPath)) {
                        System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, driverPath);
                    }
                    driver = new EdgeWebDriver(driverPath, globalWaitTime, edgeOptions);
                    break;

                case "ie":
                    if (StrUtil.isNotBlank(driverPath)) {
                        System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
                    }
                    driver = new InternetWebDriver(driverPath, globalWaitTime, ieOptions);
                    break;

                case "firefox":
                    if (StrUtil.isNotBlank(firefoxBinPath)) {
                        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_XPI_PROPERTY, firefoxBinPath);
                    }
                    if (StrUtil.isNotBlank(driverPath)) {
                        System.setProperty("webdriver.gecko.driver", driverPath);
                    }
                    driver = new FirefoxWebDriver(driverPath, globalWaitTime, firefoxOptions);
                    break;

                default:
                    driver = null;
            }
            return driver;
        }
    }

}
