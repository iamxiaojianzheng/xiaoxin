package cn.xiaojianzheng.xiaoxin.selenium.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 启用内置driver
 *
 * @author JIAHE
 * @since 1.0
 */
public class EnableBuiltInDriver {

    public static void ie() {
        InputStream resourceAsStream = ResourceUtil.getStream("driver/IEDriverServer-3.150.1.exe");
        String driverPath = shutdown("IEDriverServer", resourceAsStream);
        System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
    }

    public static void chrome() {
        InputStream resourceAsStream = ResourceUtil.getStream("driver/IEDriverServer-3.150.1.exe");
        String driverPath = shutdown("chromedriver", resourceAsStream);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
    }

    private static String shutdown(String driverName, InputStream resourceAsStream) {
        File tempFile = FileUtil.createTempFile(driverName, ".exe", new File(System.getProperty("user.dir")), true);
        String driverPath = tempFile.getAbsolutePath();
        FileUtil.writeFromStream(resourceAsStream, tempFile);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RuntimeUtil.execForLines("tasklist").stream()
                    .filter(line -> line.contains(driverName)).findFirst()
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
        return driverPath;
    }

}
