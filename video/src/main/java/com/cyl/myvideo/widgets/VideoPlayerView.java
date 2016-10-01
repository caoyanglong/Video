package com.cyl.myvideo.widgets;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.cyl.myvideo.R;
import com.cyl.myvideo.utils.MyLog;
import com.cyl.myvideo.utils.TimeUtils;

import java.util.Random;
/**
 * Created by cyl
 * on 2016/9/6.
 * email:670654904@qq.com
 */
public class VideoPlayerView extends RelativeLayout implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {

    private static final long TOTAL_TIME = 5000;

    private Context context;

    private MyVideoView vv;
    private TextView tv_loading;
    private LinearLayout ll_loading;
    private VideoPlayerListener videoPlayerListener;

    private int mPositionWhenPaused = -1;
    private int duration = -1;
    /**
     * 默认 5s
     */
    public final static long HIDE_CONTROL_BUTTON_TIME = 1000 * 5;


    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg;
    private ImageView mOperationPercent;
    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mVolume = -1;
    private float mBrightness = -1f;
    private GestureDetector mGestureDetector;
    private CheckBox pauseButton;
    private ImageView fullScreenButton;
    private SeekBar seekBar;
    //用于显示 视频播放的时间
    private TextView videoTime;
    private ImageView videoBack;
    //视频 标题 返回 顶部的控制按钮
    private View titleContainer;
    //底端控制栏
    private View controlButtonContainer;

    //工作handler
    private WorkHandler workHandler;
    /**
     * 视频开始更新
     */
    public static final int VIDEO_RUN = 1;
    /**
     * 更新进度通过系统
     */
    public static final int UPDATE_PROGRESS_AUTO = 2;
    /**
     * 更新进度通过用户
     */
    public static final int UPDATE_PROGRESS_USER = 3;
    /**
     * 显示控制栏
     */
    public static final int SHOW_CONTROL_BUTTON = 4;
    /**
     * 隐藏控制栏
     */
    public static final int HIDE_CONTROL_BUTTOBN = 5;
    /**
     * 顶部 以及 底部控制按钮的  监听器
     */
    private VideoControlListener listener;

    private TextView titleTv;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_CONTROL_BUTTOBN:
                    controlButtonContainer.setVisibility(GONE);
                    titleContainer.setVisibility(GONE);
                    break;
            }
        }
    };
    /**
     * 更新进度的 线程
     */
    private Runnable progressRunThread = new Runnable() {
        int buffer, currentPosition, duration;

        public void run() {
            // 获得当前播放时间和当前视频的长度
            currentPosition = vv.getCurrentPosition();
            duration = vv.getDuration();
            // 设置进度条的主要进度，表示当前的播放时间
            seekBar.setProgress(currentPosition);
            // 设置进度条的次要进度，表示视频的缓冲进度
            buffer = vv.getBufferPercentage();
            seekBar.setSecondaryProgress(duration * buffer);
            videoTime.setText(TimeUtils.getVideoTime(duration, currentPosition));
            handler.postDelayed(progressRunThread, 1000);
        }
    };


    private Activity activity;

    public VideoPlayerView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    public VideoPlayerView(Context context, VideoPlayerListener videoPlayerListener) {
        super(context);
        this.context = context;
        init();
        this.videoPlayerListener = videoPlayerListener;


    }

    /**
     * 注意设置这个方法 否则亮度控制不可用
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }


    /**
     * 初始化 声音控制
     */
    private void initAudioManager() {
        mVolumeBrightnessLayout = findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView) findViewById(R.id.operation_percent);

        mAudioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    }

    /**
     * 工作线程用于  进度同步
     */
    private class WorkHandler extends Handler {
        public WorkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case VIDEO_RUN:
                    break;
                case UPDATE_PROGRESS_AUTO:
                    break;
                case UPDATE_PROGRESS_USER:
                    break;
            }
        }
    }


    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            MyLog.d("<ontouch> %s", "video_view ------>onVolumeSlide");
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width
                * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    private void onBrightnessSlide(float percent) {
        if (activity == null) {
            MyLog.d("<获取亮度失败> %s", "没有传递activity");
            return;
        }
        if (mBrightness < 0) {
            mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }

    public void init() {
        HandlerThread workThread = new HandlerThread(getClass().getSimpleName());
        workThread.start();
        workHandler = new WorkHandler(workThread.getLooper());
        LayoutInflater.from(getContext()).inflate(R.layout.video_player_view_layout, this);

        vv = (MyVideoView) findViewById(R.id.vv);
        tv_loading = (TextView) findViewById(R.id.tv_loading);
        String[] loadingStrs = getResources().getStringArray(R.array.video_loading_str);
        tv_loading.setText(loadingStrs[new Random().nextInt(loadingStrs.length)]);

        ll_loading = (LinearLayout) findViewById(R.id.fl_loading);
        titleTv = (TextView) findViewById(R.id.video_title);
/********************************控制栏初始化 start **************************************************/
        controlButtonContainer = findViewById(R.id.bottom_control_container);
        pauseButton = (CheckBox) findViewById(R.id.control_button);
        fullScreenButton = (ImageView) findViewById(R.id.full_screen);
        seekBar = (SeekBar) findViewById(R.id.video_progress);
        titleContainer = findViewById(R.id.video_title_container);

        videoTime = (TextView) findViewById(R.id.video_time);
        videoBack = (ImageView) findViewById(R.id.video_back);
        videoBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickedTitleBackButton(vv);
                }
            }
        });

        pauseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vv.pause();
                } else {
                    vv.start();
                }
            }
        });
        fullScreenButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickedFullScreenButton(vv);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(this);
/********************************控制栏初始化 end **************************************************/
        vv.setVideoListener(videoPlayerListener);
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onVideoComplete();
            }
        });

        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                onVideoError();
                return true;
            }
        });

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                handler.post(progressRunThread);
                ll_loading.setVisibility(GONE);
                if (videoPlayerListener != null) {
                    videoPlayerListener.onPrepared(vv);
                }
            }
        });

        initAudioManager();

        vv.setOnTouchListener(this);
        workHandler.obtainMessage(VIDEO_RUN).sendToTarget();

    }

    /**
     * 设置视频 名字
     * @param name
     */
    public void setVideoTitle(String name){
        titleTv.setText(name);
    }

    public void onStop() {
        vv.stopPlayback();
    }

    public void onPrepareAsync(String url) {
        if (TextUtils.isEmpty(url)) {
            onVideoError();
            return;
        }
        vv.setVideoPath(url);
    }

    public void onStartPlay() {
        vv.start();
    }

    public void onPausePlay() {
        mPositionWhenPaused = vv.getCurrentPosition();
        duration = vv.getDuration();
        vv.suspend();
    }

    public void onResumePlay() {
        if (mPositionWhenPaused >= 0) {
            vv.seekTo(mPositionWhenPaused);
            vv.resume();
            mPositionWhenPaused = -1;
        }
    }

    public void onVideoComplete() {
        if (videoPlayerListener != null) {
            videoPlayerListener.onVideoComplete();
        }
    }

    public void onVideoError() {
        if (videoPlayerListener != null) {
            videoPlayerListener.onVideoError();
        }
    }

    public interface VideoPlayerListener {
        void onVideoComplete();

        void onVideoError();

        void onPause();

        void onStart();

        void onPrepared(MyVideoView vv);
    }

    public void setVideoPlayerListener(VideoPlayerListener listener) {
        this.videoPlayerListener = listener;
        vv.setVideoListener(listener);
    }

    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mVolumeBrightnessLayout.setVisibility(View.GONE);
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        MyLog.d("<ontouch> %s", "video_view ------>onTouch");
//        if (mGestureDetector.onTouchEvent(event))
//            return true;
//
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
//        return true;
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    //返回false的话只能响应长摁事件
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    MyLog.d("test_gesture %s", "长嗯");
                    super.onLongPress(e);
                }

                @Override
                public boolean onSingleTapUp(MotionEvent ev) {
//                    switch (ev.getAction() & MotionEvent.ACTION_MASK) {
//                        case MotionEvent.ACTION_UP:
//                            MyLog.d("<test_gesture> %s","endGesture");
//
//                            break;
//                    }
//                    endGesture();
                    return super.onSingleTapUp(ev);
                }


                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    MyLog.d("test_gesture %s", "onScroll:" + distanceX + "   " + distanceY);
//                    return super.onScroll(e1, e2, distanceX, distanceY);
                    float mOldX = e1.getX(), mOldY = e1.getY();
                    int y = (int) e2.getRawY();
//            Display disp = context.getWindowManager().getDefaultDisplay();
                    int windowWidth = vv.getWidth();
                    int windowHeight = vv.getHeight();

                    if (mOldX > windowWidth * 4.0 / 5 && mOldY < windowHeight * 4.0 / 5)
                        onVolumeSlide((mOldY - y) / windowHeight);
                    else if (mOldX < windowWidth / 5.0 && mOldY < windowHeight * 4.0 / 5)
                        onBrightnessSlide((mOldY - y) / windowHeight);
                    switch (e1.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            MyLog.d("<test_gesture> %s", "endGesture");
                            endGesture();
                            break;
                    }
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
            });
            mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    //连续点击 先移除上次定时发出去的消息
                    handler.removeMessages(HIDE_CONTROL_BUTTOBN);
                    if (controlButtonContainer.getVisibility() == VISIBLE) {
                        controlButtonContainer.setVisibility(GONE);
                        titleContainer.setVisibility(GONE);
                    } else {
                        controlButtonContainer.setVisibility(VISIBLE);
                        titleContainer.setVisibility(VISIBLE);
                        handler.sendEmptyMessageDelayed(HIDE_CONTROL_BUTTOBN, HIDE_CONTROL_BUTTON_TIME);
                    }

                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    MyLog.d("test_gesture %s", "双击");
                    return true;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return false;
                }
            });
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (vv != null) {
            vv.seekTo(seekBar.getProgress());
        }
    }

    /**
     * 监听  title back 按钮  以及 全屏按钮
     */
    public interface VideoControlListener {
        /**
         * 点击全屏按钮的 时候回调此函数
         *
         * @param vv
         */
        void onClickedFullScreenButton(VideoView vv);

        /**
         * 点击视频返回按钮的时候   回调此函数
         *
         * @param vv
         */
        void onClickedTitleBackButton(VideoView vv);
    }

    public void setOnVideoControlListener(VideoControlListener listener) {
        this.listener = listener;
    }

    public MyVideoView getVv() {
        return vv;
    }
}
