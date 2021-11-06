package cn.xiaojianzheng.xiaoxin.selenium.driver;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.time.Duration;

public class InternetWebDriver extends AbstractWebDriver {

    private static final InternetExplorerOptions DEFAULT_OPTIONS;

    static {
        DEFAULT_OPTIONS = new InternetExplorerOptions();
        DEFAULT_OPTIONS.introduceFlakinessByIgnoringSecurityDomains();
        DEFAULT_OPTIONS.withInitialBrowserUrl("about:blank");
        DEFAULT_OPTIONS.ignoreZoomSettings();
    }

    public InternetWebDriver(String driverPath) {
        this(driverPath, null, null);
    }

    public InternetWebDriver(String driverPath, InternetExplorerOptions options) {
        this(driverPath, null, options);
    }

    public InternetWebDriver(String driverPath, Duration globalWaitTime, InternetExplorerOptions options) {
        super(new InternetExplorerDriver(ObjectUtil.isNotEmpty(options) ? DEFAULT_OPTIONS.merge(options) : DEFAULT_OPTIONS));

        if (ObjectUtil.isNotEmpty(globalWaitTime)) {
            super.setGlobalWaitTime(globalWaitTime);
        }

        if (StrUtil.isNotBlank(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
    }

}
