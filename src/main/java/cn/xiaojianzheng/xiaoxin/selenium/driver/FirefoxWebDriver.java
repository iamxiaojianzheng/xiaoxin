package cn.xiaojianzheng.xiaoxin.selenium.driver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.time.Duration;

public class FirefoxWebDriver extends AbstractWebDriver {

    private final FirefoxDriver firefoxDriver;

    private static final FirefoxOptions DEFAULT_OPTIONS;

    static {
        DEFAULT_OPTIONS = new FirefoxOptions();
    }

    public FirefoxWebDriver(String driverPath, Duration globalWaitTime, FirefoxOptions options) {
        super(new FirefoxDriver(ObjectUtil.isNotEmpty(options) ? DEFAULT_OPTIONS.merge(options) : DEFAULT_OPTIONS));
        super.setGlobalWaitTime(globalWaitTime);

        this.firefoxDriver = (FirefoxDriver) super.getWebDriver();

        if (StrUtil.isNotBlank(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
    }

    /**
     * Capture the full page screenshot and store it in the specified location.
     *
     * @param file file for saving image
     */
    public void getFullPageScreenshotAs(File file) {
        byte[] bytes = firefoxDriver.getFullPageScreenshotAs(OutputType.BYTES);
        FileUtil.writeBytes(bytes, file);
    }

}
