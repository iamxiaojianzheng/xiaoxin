package cn.xiaojianzheng.xiaoxin.video;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

/**
 * @author JIAHE
 * @since 1.0
 */
public class WindowsScreenRecording {
    private static FFmpegFrameGrabber grabber;
    private static FFmpegFrameRecorder recorder;

    private static final int frameRate = 24;
    private static volatile boolean isRunning = false;

    static {
        try {
            FFmpegFrameGrabber.tryLoad();
            FFmpegFrameRecorder.tryLoad();
        } catch (Exception ignored) {
        }
    }

    private void initGrabber() throws FFmpegFrameGrabber.Exception {
        grabber = new FFmpegFrameGrabber("desktop");
        grabber.setFormat("gdigrab");
        grabber.setOption("fflags", "nobuffer");
        grabber.start();
    }

    private void initRecorder(String savePath) throws FFmpegFrameRecorder.Exception {
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        recorder = new FFmpegFrameRecorder(savePath, grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());
        // 是否支持flv
        if (supportFlvFormatCodec()) {
            // 设置封装格式
            recorder.setFormat("flv");
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
        }
        // 开始录制
        recorder.start();
    }

    private void recordFrame() {
//        int poll = 1000 / frameRate;
//        ScheduledThreadPoolExecutor executorService = ThreadUtil.createScheduledExecutor(1);
//        executorService.scheduleAtFixedRate(() -> {
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
//        }, poll, poll, TimeUnit.MILLISECONDS);
    }

    public void start(String savePath) {
        try {
            initGrabber();
            initRecorder(savePath);
            isRunning = true;
            recordFrame();
        } catch (FFmpegFrameGrabber.Exception | FFmpegFrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (grabber != null) grabber.close();
                if (recorder != null) recorder.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void stop() {
        try {
            recorder.close();
            grabber.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isRunning() {
        return isRunning;
    }

    /**
     * 是否支持flv的音视频编码
     */
    private boolean supportFlvFormatCodec() {
        int vcodec = grabber.getVideoCodec();
        int acodec = grabber.getAudioCodec();
        return (avcodec.AV_CODEC_ID_H264 == vcodec || avcodec.AV_CODEC_ID_H263 == vcodec)
                && (avcodec.AV_CODEC_ID_AAC == acodec || avcodec.AV_CODEC_ID_AAC_LATM == acodec);
    }

}
