package com.day.l.video.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenyan on 12/16/14.
 * 自动滚动的ViewPager，可以设置是否自动滚动和滚动间隔
 */

public class AutoScrollViewPager extends ViewPager {
    private static final int DEFAULT_INTERVAL = 3000;
    private static final int SCROLL = 0;

    private int interval = DEFAULT_INTERVAL;
    private boolean isAutoScroll = true;

    private ScrollHandler mHandler;

    public AutoScrollViewPager(Context context) {
        super(context);
        init();
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new ScrollHandler();
        sendScrollMessage();
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setAutoScroll(boolean isAutoScroll) {
        this.isAutoScroll = isAutoScroll;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
            mHandler.removeMessages(SCROLL);
        } else if (action == MotionEvent.ACTION_UP) {
            sendScrollMessage();
        }

        return super.dispatchTouchEvent(ev);
    }

    private class ScrollHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL:
                    if (isAutoScroll == true) {
                        scroll();
                        sendScrollMessage();
                    }
                default:
                    break;
            }
        }
    }

    private void sendScrollMessage() {
        mHandler.removeMessages(SCROLL);
        mHandler.sendEmptyMessageDelayed(SCROLL, interval);
    }

    /**
     * 滚动一个页面
     */
    private void scroll() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = ++currentItem;
        if (nextItem < 0) {
            setCurrentItem(totalCount - 1, true);
        } else if (nextItem == totalCount) {
            setCurrentItem(0, true);
        } else {
            setCurrentItem(nextItem, true);
        }
    }
}
