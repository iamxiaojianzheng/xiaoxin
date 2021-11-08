package cn.xiaojianzheng.xiaoxin.selenium.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * get browser version on current pc
 *
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class BrowserVersionUtil {

    private final static Map<String, String> commands = new HashMap<>();

    static {
        try {
            BufferedReader reader = ResourceUtil.getUtf8Reader("commands.properties");
            Properties properties = new Properties();
            properties.load(new StringReader(IoUtil.read(reader).replace("\\", "\\\\")));
            ArrayList<String> windowsCommand = new ArrayList<>();
            properties.stringPropertyNames().forEach(name -> {
                if (name.matches("command\\..*?\\.windows.\\d+")) windowsCommand.add(name);
            });
            windowsCommand.forEach(commandKey -> {
                String value = properties.getProperty(commandKey);
                commands.put(commandKey, value);
            });
        } catch (IOException e) {
            log.error("加载commands.properties失败", e);
        }
    }

    public static String chrome() {
        return getVersion("chrome");
    }

    public static String firefox() {
        return getVersion("firefox");
    }

    public static String edge() {
        return getVersion("edge");
    }

    private static String getVersion(String browser) {
        for (Map.Entry<String, String> entry : commands.entrySet().stream()
                .filter(entry -> entry.getKey().matches("command." + browser + ".windows.\\d+"))
                .collect(Collectors.toList())) {
            String command = entry.getValue();
            String text = StrUtil.cleanBlank(String.join("", RuntimeUtil.execForLines(command.split(" "))));
            if (StrUtil.isNotBlank(text) && text.matches("Version=[\\d.]+")) {
                return text.replace("Version=", "");
            }
        }
        return null;
    }

}
