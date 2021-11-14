package cn.xiaojianzheng.xiaoxin.video.recorder.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class PlayLocalVideoFile {
    public static void main(String[] args) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("C:\\Users\\JIAHE\\github\\xiaoxin\\video\\test.mp4");
        grabber.start();

        // getFrameRate()方法：获取视频文件信息,总帧数
        int ftp = grabber.getLengthInFrames();
        log.info("时长 " + ftp / grabber.getFrameRate() / 60);

        int flag = 0;
        CanvasFrame canvas = new CanvasFrame("摄像头");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        while (flag <= ftp) {
            // 获取帧
            canvas.showImage(grabber.grab());
            flag++;
        }

        canvas.dispose();
        grabber.close();

    }
}
