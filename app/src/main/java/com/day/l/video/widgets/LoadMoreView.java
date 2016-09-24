package com.day.l.video.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.day.l.video.R;


public class LoadMoreView {

    private LoadRecyclerView loadRecyclerView;

    private ProgressBar loadProgressBar;

    private TextView tvLoadMore;

    private View footerView;

    public LoadMoreView(Context context, LoadRecyclerView loadRecyclerView) {
        init(context, loadRecyclerView);
    }

    private void init(Context context, LoadRecyclerView loadRecyclerView) {
        this.loadRecyclerView = loadRecyclerView;
        footerView = LayoutInflater.from(context).inflate(R.layout.custom_load_more, loadRecyclerView, false);
        loadProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);
        tvLoadMore = (TextView) footerView.findViewById(R.id.tv_load_more);
        footerView.setVisibility(View.GONE);
    }

    public void setNoMore() {
        footerView.setVisibility(View.GONE);
        loadRecyclerView.setNoMore();
        if (View.VISIBLE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.GONE);
        }
        tvLoadMore.setText("没有更多");
//        tvLoadMore.setText("");
    }

    public void setNoMore(String toast) {
        loadRecyclerView.setNoMore();
        if (View.VISIBLE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.GONE);
        }
        tvLoadMore.setText(toast);
    }

    public void setNoMore(int toast) {
        loadRecyclerView.setNoMore();
        if (View.VISIBLE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.GONE);
        }
        tvLoadMore.setText(toast);
    }

    public void resetMore() {
        footerView.setVisibility(View.VISIBLE);
        loadRecyclerView.resetMore();
        if (View.GONE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.VISIBLE);
        }
        tvLoadMore.setText("加载更多");
    }

    public void resetMore(String toast) {
        loadRecyclerView.resetMore();
        if (View.GONE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.VISIBLE);
        }
        tvLoadMore.setText(toast);
    }

    public void resetMore(int toast) {
        loadRecyclerView.resetMore();
        if (View.GONE == loadProgressBar.getVisibility()) {
            loadProgressBar.setVisibility(View.VISIBLE);
        }
        tvLoadMore.setText(toast);
    }


    public View getFooterView() {
        return footerView;
    }

    public void setVisible(int visible) {
        footerView.setVisibility(visible);
    }
}
