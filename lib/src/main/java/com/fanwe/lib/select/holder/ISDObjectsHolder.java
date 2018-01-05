package com.fanwe.lib.select.holder;

import com.fanwe.lib.select.callback.FIterateCallback;

/**
 * Created by admin on 2018/1/4.
 */

public interface ISDObjectsHolder<T> {
    void add(T var1);

    boolean remove(T var1);

    boolean contains(T var1);

    int size();

    void clear();

    boolean foreach(FIterateCallback<T> var1);

    boolean foreachReverse(FIterateCallback<T> var1);
}
