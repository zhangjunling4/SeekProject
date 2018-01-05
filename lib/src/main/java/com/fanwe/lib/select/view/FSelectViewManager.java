package com.fanwe.lib.select.view;

import android.view.View;

/**
 * Created by admin on 2018/1/4.
 */

public class FSelectViewManager<T extends View> extends FSelectManager<T> {
    private boolean mClickAble = true;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(FSelectViewManager.this.mClickAble) {
                FSelectViewManager.this.performClick((T) v);
            }

        }
    };

    public FSelectViewManager() {
    }

    protected void initItem(int index, T item) {
        item.setOnClickListener(this.mOnClickListener);
        this.notifyNormal(index, item);
        super.initItem(index, item);
    }

    public boolean isClickAble() {
        return this.mClickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.mClickAble = clickAble;
    }

    protected void notifyNormal(int index, T item) {
        item.setSelected(false);
        super.notifyNormal(index, item);
    }

    protected void notifySelected(int index, T item) {
        item.setSelected(true);
        super.notifySelected(index, item);
    }
}
