package com.day.l.video.video.entity;

/**
 * Created by cyl
 * on 2016/9/25.
 * email:670654904@qq.com
 */
public class SelectorVideoEntity {
    private String index;
    private boolean selected;

    public SelectorVideoEntity() {
    }

    public SelectorVideoEntity(String index, boolean selected) {
        this.index = index;
        this.selected = selected;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
