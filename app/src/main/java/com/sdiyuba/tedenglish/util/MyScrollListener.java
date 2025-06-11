package com.sdiyuba.tedenglish.util;

import androidx.recyclerview.widget.RecyclerView;

public class MyScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 当滑动状态变为空闲状态时执行相应操作
            // 比如显示/隐藏其他视图、加载更多数据等
            // 这里可以添加你想要执行的滑动监听操作

        }
    }
}
