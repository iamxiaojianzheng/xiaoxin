package cn.xiaojianzheng.xiaoxin.selenium.driver;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.print.PrintOptions;

import java.io.File;
import java.time.Duration;
import java.util.Collections;

public class EdgeWebDriver extends AbstractWebDriver {

    private final EdgeDriver edgeDriver;

    private static final EdgeOptions DEFAULT_OPTIONS;

    static {
        DEFAULT_OPTIONS = new EdgeOptions();
        // 启用开发者模式
        DEFAULT_OPTIONS.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        DEFAULT_OPTIONS.setExperimentalOption("useAutomationExtension", false);
    }

    public EdgeWebDriver(String driverPath) {
        this(driverPath, null, null);
    }

    public EdgeWebDriver(String driverPath, EdgeOptions edgeOptions) {
        this(driverPath, null, edgeOptions);
    }

    public EdgeWebDriver(String driverPath, Duration globalWaitTime, EdgeOptions edgeOptions) {
        super(new EdgeDriver(ObjectUtil.isNotEmpty(edgeOptions) ? DEFAULT_OPTIONS.merge(edgeOptions) : DEFAULT_OPTIONS));
        super.setGlobalWaitTime(globalWaitTime);

        this.edgeDriver = (EdgeDriver) super.originWebDriver;

        // 屏蔽网站对selenium的检测
        this.edgeDriver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                MapUtil.of("source", "Object.defineProperty(navigator,'webdriver',{get:()=>undefined})"));

        if (StrUtil.isNotBlank(driverPath)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
    }

    /**
     * 将当前窗口内容另存为PDF
     *
     * @param pdfSavePath pdf 保存路径
     * @return file object
     */
    public File getPage(String pdfSavePath) {
        Pdf pdf = edgeDriver.print(new PrintOptions());
        byte[] bytes = Base64.decode(pdf.getContent());
        File file = FileUtil.newFile(pdfSavePath);
        FileUtil.writeBytes(bytes, file);
        return file;
    }

    /**
     * 将当前窗口内容另存为PDF
     *
     * @param printOptions 打印的相关参数
     * @return pdf object
     */
    public Pdf getPage(PrintOptions printOptions) {
        return edgeDriver.print(printOptions);
    }

}
