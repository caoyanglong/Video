package com.day.l.video.widgets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private static final int INDEX_PAGE = 1;

    /**
     * 是否还有更多数据
     */
    private boolean isLoadMore = true;

    private int previousTotal = 0;
    private boolean loading = true;
    int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

    private int currentPage = INDEX_PAGE;

    private RvLoadMoreListener rvLoadMoreListener;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener() {
    }

    /**
     * 是否还有更多数据
     *
     * @param isLoadMore
     */
    public void isLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public void setRvLoadMoreListener(RvLoadMoreListener rvLoadMoreListener) {
        this.rvLoadMoreListener = rvLoadMoreListener;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(RvLoadMoreListener rvLoadMoreListener) {
        this.rvLoadMoreListener = rvLoadMoreListener;
    }

    public EndlessRecyclerOnScrollListener(RvLoadMoreListener rvLoadMoreListener, LinearLayoutManager mLinearLayoutManager) {
        this.rvLoadMoreListener = rvLoadMoreListener;
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastCompletelyVisiableItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (visibleItemCount > 0) && (lastCompletelyVisiableItemPosition >= totalItemCount - 1) && null != rvLoadMoreListener) {
            if (isLoadMore) {
                currentPage++;
                rvLoadMoreListener.onLoadMore(currentPage);
                loading = true;
            }
        }
    }

    public void resetDefault() {
        currentPage = INDEX_PAGE;
        previousTotal = 0;
        loading = true;
        lastCompletelyVisiableItemPosition = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
    }

    /**
     * 加载更多的回调
     */
    public interface RvLoadMoreListener {
        void onLoadMore(int currentPage);
    }


}