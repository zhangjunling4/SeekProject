package com.fanwe.lib.select.holder;

import com.fanwe.lib.select.callback.FIterateCallback;
import com.fanwe.lib.select.utils.SDCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/4.
 */

public class SDObjectsHolder<T> implements ISDObjectsHolder<T> {
    private List<T> mListObject = new ArrayList();

    public SDObjectsHolder() {
    }

    public synchronized void add(T object) {
        if (object != null) {
            if (!this.mListObject.contains(object)) {
                this.mListObject.add(object);
            }

        }
    }

    public synchronized boolean remove(T object) {
        return object == null ? false : this.mListObject.remove(object);
    }

    public synchronized boolean contains(T object) {
        return object == null ? false : this.mListObject.contains(object);
    }

    public int size() {
        return this.mListObject.size();
    }

    public synchronized void clear() {
        this.mListObject.clear();
    }

    @Override
    public boolean foreach(FIterateCallback<T> callback) {
        return SDCollectionUtil.foreach(this.mListObject, callback);
    }

    @Override
    public boolean foreachReverse(FIterateCallback<T> callback) {
        return SDCollectionUtil.foreachReverse(this.mListObject, callback);
    }
}
