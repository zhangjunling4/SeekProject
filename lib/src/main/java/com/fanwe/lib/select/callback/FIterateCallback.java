package com.fanwe.lib.select.callback;

import com.fanwe.lib.select.view.FSelectManager;

import java.util.Iterator;

/**
 * Created by admin on 2018/1/4.
 */

public interface FIterateCallback<T> {
    boolean next(int var1, FSelectManager.SelectCallback var2, Iterator<FSelectManager.SelectCallback> var3);
}
