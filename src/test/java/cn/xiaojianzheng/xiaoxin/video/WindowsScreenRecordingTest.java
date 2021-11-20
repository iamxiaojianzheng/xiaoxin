package cn.xiaojianzheng.xiaoxin.video;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
class WindowsScreenRecordingTest {
    @Test
    void stop() {
        String savePath = new File("video/test.mp4").getAbsolutePath();
        WindowsScreenRecording recording = new WindowsScreenRecording();

        ThreadUtil.execute(() -> recording.start(Paths.get(savePath)));

        log.info("****stop停止录制****");
        while (true) {
            Scanner sc = new Scanner(System.in);
            if (sc.hasNext()) {
                String cmd = sc.next();
                if (cmd.equalsIgnoreCase("stop")) {
                    recording.stop();
                    log.info("****已经停止录制****");
                    break;
                }
            }
        }
    }
}