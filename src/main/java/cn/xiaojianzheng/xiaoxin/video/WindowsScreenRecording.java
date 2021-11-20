package cn.xiaojianzheng.xiaoxin.video;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author JIAHE
 * @since 1.0
 */
@Slf4j
public class WindowsScreenRecording {
    private static FFmpegFrameGrabber grabber;
    private static FFmpegFrameRecorder recorder;

    private static final ExecutorService executorService = ThreadUtil.newExecutor(1);
    private static final int frameRate = 24;

    public static volatile boolean isRunning = false;

    static {
        try {
            FFmpegFrameGrabber.tryLoad();
            FFmpegFrameRecorder.tryLoad();
        } catch (Exception ignored) {
        }

        Runtime.getRuntime().addShutdownHook(new Thread(WindowsScreenRecording::close, "closeScreenRecording"));
    }

    private static void initGrabber() throws FrameGrabber.Exception {
        if (grabber == null) {
            grabber = new FFmpegFrameGrabber("desktop");
            grabber.setFormat("gdigrab");
            grabber.setOption("fflags", "nobuffer");
            grabber.start();
        } else {
            grabber.restart();
        }
    }

    private static void initRecorder(String savePath) throws FrameRecorder.Exception {
        if (recorder == null) {
            recorder = new FFmpegFrameRecorder(savePath, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        }

        // 是否支持flv
        if (supportFlvFormatCodec()) {
            // 设置封装格式
            recorder.setFormat("flv");
        }

        recorder.setFrameRate(frameRate);
        recorder.setGopSize(frameRate); // 设置gop,与帧率相同，相当于间隔1秒产生一个关键帧
        recorder.setVideoOption("crf", "26");
        recorder.setVideoOption("threads", "1");
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setVideoOption("preset", "ultrafast");
        recorder.setMetadata(grabber.getMetadata());
        // 设置视频格式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        // 启用RDOQ算法，优化视频质量
        // 1：在视频码率和视频质量之间取得平衡
        // 2：最大程度优化视频质量（会降低编码速度和提高码率）
        recorder.setTrellis(1);
        recorder.setMaxDelay(0);

        // 开始录制
        recorder.start();
    }

    private static void recordFrame() {
        isRunning = true;

        Frame frame;

        // 时间戳计算
        long startTime = 0;
        long videoTS;

        try {
            while (isRunning && (frame = grabber.grab()) != null) {
                if (startTime == 0) {
                    startTime = System.currentTimeMillis();
                }
                videoTS = 1000 * (System.currentTimeMillis() - startTime);
                // 判断时间偏移
                if (videoTS > recorder.getTimestamp()) {
                    recorder.setTimestamp((videoTS));
                }
                recorder.record(frame);
            }
        } catch (FrameGrabber.Exception | FrameRecorder.Exception ignored) {
        }
    }

    public static void start(Path savePath) {
        if (!isRunning) {
            FileUtil.mkdir(savePath.getParent());
            String canonicalPath = FileUtil.getCanonicalPath(savePath.toFile());

            try {
                initGrabber();
                initRecorder(canonicalPath);
                executorService.execute(WindowsScreenRecording::recordFrame);
                log.info("开始录屏：{}", canonicalPath);
            } catch (FrameGrabber.Exception | FrameRecorder.Exception e) {
                log.error(e.getMessage());
                close();
            }

        }
    }

    public static void stop() {
        synchronized (log) {
            isRunning = false;
            log.info("close screen recording: {}", isRunning);
            while (executorService.isShutdown()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }
            close();
        }
    }

    private static void close() {
        try {
            if (recorder != null) {
                recorder.close();
                recorder = null;
            }
            if (grabber != null) {
                grabber.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 是否支持flv的音视频编码
     */
    private static boolean supportFlvFormatCodec() {
        int vcodec = grabber.getVideoCodec();
        int acodec = grabber.getAudioCodec();
        return (avcodec.AV_CODEC_ID_H264 == vcodec || avcodec.AV_CODEC_ID_H263 == vcodec)
                && (avcodec.AV_CODEC_ID_AAC == acodec || avcodec.AV_CODEC_ID_AAC_LATM == acodec);
    }

}
