package cn.xiaojianzheng.xiaoxin.selenium.driver;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;

import java.io.File;
import java.time.Duration;
import java.util.Collections;

public class ChromeWebDriver extends AbstractWebDriver {

    private final ChromeDriver chromeDriver;

    private static final ChromeOptions DEFAULT_OPTIONS;

    static {
        DEFAULT_OPTIONS = new ChromeOptions();
        // 启用开发者模式
        DEFAULT_OPTIONS.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        DEFAULT_OPTIONS.setExperimentalOption("useAutomationExtension", false);
    }

    public ChromeWebDriver(String driverPath, Duration globalWaitTime, ChromeOptions options) {
        super(new ChromeDriver(ObjectUtil.isNotEmpty(options) ? DEFAULT_OPTIONS.merge(options) : DEFAULT_OPTIONS));
        super.setGlobalWaitTime(globalWaitTime);

        this.chromeDriver = (ChromeDriver) super.originWebDriver;

        // 屏蔽网站对selenium的检测
        this.chromeDriver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                MapUtil.of("source", "Object.defineProperty(navigator,'webdriver',{get:()=>undefined})"));

        if (StrUtil.isNotBlank(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
    }

    /**
     * 将当前窗口内容另存为PDF(<b>基于headless模式</b>)
     *
     * @param pdfSavePath pdf 保存路径
     * @return file object
     */
    public File getPage(String pdfSavePath) {
        Pdf pdf = chromeDriver.print(new PrintOptions());
        byte[] bytes = Base64.decode(pdf.getContent());
        File file = FileUtil.newFile(pdfSavePath);
        FileUtil.writeBytes(bytes, file);
        return file;
    }

    /**
     * 将当前窗口内容另存为PDF(<b>基于headless模式</b>)
     *
     * @param printOptions 打印的相关参数
     * @return pdf object
     */
    public Pdf getPage(PrintOptions printOptions) {
        return chromeDriver.print(printOptions);
    }

}
