package com.day.l.video.widgets;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


public class LoadRecyclerView extends RecyclerView {

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public LoadRecyclerView(Context context) {
        this(context, null);
    }

    public LoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }


    /**
     * 设置加载更多的回调监听.
     *
     * @param rvLoadMoreListener
     */
    public void setRvLoadMoreListener(EndlessRecyclerOnScrollListener.RvLoadMoreListener rvLoadMoreListener) {
        if (null == rvLoadMoreListener) {
            return;
        }
        LayoutManager layoutManager = getLayoutManager();
        if (null == layoutManager) {
            throw new NullPointerException("请您先调用 {@link RecyclerView#setLayoutManager(LayoutManager layout)} 方法");
        }
        if (null == endlessRecyclerOnScrollListener) {
            if (layoutManager instanceof LinearLayoutManager) {
                endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) layoutManager);
            } else if (layoutManager instanceof GridLayoutManager) {
                endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener((GridLayoutManager) layoutManager);
            } else {
                throw new RuntimeException("LoadRecyclerView目前只支持LinearLayoutManager和GridLayoutManager");
            }
        }
        addOnScrollListener(endlessRecyclerOnScrollListener);

        endlessRecyclerOnScrollListener.setRvLoadMoreListener(rvLoadMoreListener);
    }

    /**
     * 没有更多数据了
     */
    public void setNoMore() {
        endlessRecyclerOnScrollListener.isLoadMore(false);
    }

    /**
     * 下拉刷新复位
     */
    public void resetMore() {
        endlessRecyclerOnScrollListener.resetDefault();
        endlessRecyclerOnScrollListener.isLoadMore(true);
    }

    /**
     * 最好调用一下，为了销毁对象资源
     */
    public void destroyRes() {
        if (null != endlessRecyclerOnScrollListener) {
            endlessRecyclerOnScrollListener.setLinearLayoutManager(null);
            endlessRecyclerOnScrollListener.setRvLoadMoreListener(null);
            removeOnScrollListener(endlessRecyclerOnScrollListener);
        }
    }

    public int getScrollDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        View firstVisibleItem = this.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemHeight = firstVisibleItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return (firstItemPosition + 1) * itemHeight - firstItemBottom;
    }

}
