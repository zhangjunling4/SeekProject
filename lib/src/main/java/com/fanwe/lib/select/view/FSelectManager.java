package com.fanwe.lib.select.view;

import com.fanwe.lib.select.callback.FIterateCallback;
import com.fanwe.lib.select.holder.ISDObjectsHolder;
import com.fanwe.lib.select.holder.SDObjectsHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/1/4.
 */

public class FSelectManager<T> {
    private List<T> mListItem = new ArrayList();
    private int mCurrentIndex = -1;
    private int mLastIndex = -1;
    private Map<Integer, T> mMapSelectedIndexItem = new LinkedHashMap();
    private FSelectManager.Mode mMode;
    private boolean mIsEnable;
    private FSelectManager.ReSelectCallback<T> mReSelectCallback;
    private ISDObjectsHolder<FSelectManager.SelectCallback<T>> mSelectCallbackHolder;

    public FSelectManager() {
        this.mMode = FSelectManager.Mode.SINGLE_MUST_ONE_SELECTED;
        this.mIsEnable = true;
        this.mSelectCallbackHolder = new SDObjectsHolder();
    }

    public void addSelectCallback(FSelectManager.SelectCallback<T> selectCallback) {
        this.mSelectCallbackHolder.add(selectCallback);
    }

    public void removeSelectCallback(FSelectManager.SelectCallback<T> selectCallback) {
        this.mSelectCallbackHolder.remove(selectCallback);
    }

    public void setReSelectCallback(FSelectManager.ReSelectCallback<T> reSelectCallback) {
        this.mReSelectCallback = reSelectCallback;
    }

    public void setEnable(boolean enable) {
        this.mIsEnable = enable;
    }

    public boolean isEnable() {
        return this.mIsEnable;
    }

    public int getLastIndex() {
        return this.mLastIndex;
    }

    public FSelectManager.Mode getMode() {
        return this.mMode;
    }

    public void setMode(FSelectManager.Mode mode) {
        if(mode != null) {
            this.clearSelected();
            this.mMode = mode;
        }

    }

    public boolean isSingleMode() {
        boolean single = false;
        switch(this.mMode.ordinal()) {
            case 1:
            case 2:
                single = true;
                break;
            case 3:
            case 4:
                single = false;
        }

        return single;
    }

    public boolean isSelected(T item) {
        int index = this.indexOfItem(item);
        return this.isSelected(index);
    }

    public boolean isSelected(int index) {
        boolean selected = false;
        if(index >= 0) {
            switch(this.mMode.ordinal()) {
                case 1:
                case 2:
                    if(index == this.mCurrentIndex) {
                        selected = true;
                    }
                    break;
                case 3:
                case 4:
                    if(this.mMapSelectedIndexItem.containsKey(Integer.valueOf(index))) {
                        selected = true;
                    }
            }
        }

        return selected;
    }

    public void synchronizeSelected() {
        this.synchronizeSelected(this.mListItem);
    }

    public void synchronizeSelected(List<T> items) {
        if(items != null) {
            Iterator var2 = items.iterator();

            while(var2.hasNext()) {
                Object item = var2.next();
                this.synchronizeSelected((T) item);
            }
        }

    }

    public void synchronizeSelected(T item) {
        this.synchronizeSelected(this.indexOfItem(item));
    }

    private void synchronizeSelected(int index) {
        Object model = this.getItem(index);
        if(model instanceof FSelectManager.Selectable) {
            FSelectManager.Selectable sModel = (FSelectManager.Selectable)model;
            this.setSelected(index, sModel.isSelected());
        }

    }

    public int getSelectedIndex() {
        return this.mCurrentIndex;
    }

    public T getSelectedItem() {
        return this.getItem(this.mCurrentIndex);
    }

    public List<Integer> getSelectedIndexs() {
        ArrayList listIndex = new ArrayList();
        if(!this.mMapSelectedIndexItem.isEmpty()) {
            Iterator var2 = this.mMapSelectedIndexItem.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry item = (Map.Entry)var2.next();
                listIndex.add(item.getKey());
            }
        }

        return listIndex;
    }

    public List<T> getSelectedItems() {
        ArrayList listItem = new ArrayList();
        if(!this.mMapSelectedIndexItem.isEmpty()) {
            Iterator var2 = this.mMapSelectedIndexItem.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry item = (Map.Entry)var2.next();
                listItem.add(item.getValue());
            }
        }

        return listItem;
    }

    public void setItems(T... items) {
        List listItem = null;
        if(items != null && items.length > 0) {
            listItem = Arrays.asList(items);
        }

        this.setItems(listItem);
    }

    public void setItems(List<T> items) {
        if(items != null) {
            this.mListItem = items;
        } else {
            this.mListItem.clear();
        }

        this.resetIndex();
        this.initItems(this.mListItem);
    }

    public void appendItems(List<T> items, boolean addAll) {
        if(items != null && !items.isEmpty()) {
            if(!this.mListItem.isEmpty()) {
                if(addAll) {
                    this.mListItem.addAll(items);
                }

                this.initItems(items);
            } else {
                this.setItems(items);
            }
        }

    }

    public void appendItem(T item, boolean add) {
        if(!this.mListItem.isEmpty() && item != null) {
            if(add) {
                this.mListItem.add(item);
            }

            this.initItem(this.indexOfItem(item), item);
        }

    }

    private void initItems(List<T> items) {
        if(items != null && !items.isEmpty()) {
            Object item = null;
            boolean index = false;

            for(int i = 0; i < items.size(); ++i) {
                item = items.get(i);
                int var5 = this.indexOfItem((T) item);
                this.initItem(var5, (T) item);
            }
        }

    }

    protected void initItem(int index, T item) {
    }

    private void resetIndex() {
        switch(this.mMode.ordinal()) {
            case 1:
            case 2:
                this.mCurrentIndex = -1;
                break;
            case 3:
            case 4:
                this.mMapSelectedIndexItem.clear();
        }

    }

    private boolean isIndexLegal(int index) {
        return index >= 0 && index < this.mListItem.size();
    }

    public void selectLastIndex() {
        this.setSelected(this.mLastIndex, true);
    }

    public void selectAll() {
        for(int i = 0; i < this.mListItem.size(); ++i) {
            this.setSelected(i, true);
        }

    }

    public void performClick(int index) {
        if(this.isIndexLegal(index)) {
            boolean selected = this.isSelected(index);
            this.setSelected(index, !selected);
        }

    }

    public void performClick(T item) {
        this.performClick(this.indexOfItem(item));
    }

    public void setSelected(T item, boolean selected) {
        int index = this.indexOfItem(item);
        this.setSelected(index, selected);
    }

    public void setSelected(int index, boolean selected) {
        if(this.mIsEnable) {
            if(this.isIndexLegal(index)) {
                switch(this.mMode.ordinal()) {
                    case 1:
                        if(selected) {
                            this.selectItemSingle(index);
                        } else if(this.mCurrentIndex == index) {
                            int tempCurrentIndex = this.mCurrentIndex;
                            this.mCurrentIndex = -1;
                            this.notifyNormal(tempCurrentIndex);
                        }
                        break;
                    case 2:
                        if(selected) {
                            this.selectItemSingle(index);
                        } else if(this.mCurrentIndex == index && this.mReSelectCallback != null) {
                            this.mReSelectCallback.onSelected(this.mCurrentIndex, this.getItem(this.mCurrentIndex));
                        }
                        break;
                    case 3:
                        if(selected) {
                            this.selectItemMulti(index);
                        } else if(this.mMapSelectedIndexItem.containsKey(Integer.valueOf(index))) {
                            this.mMapSelectedIndexItem.remove(Integer.valueOf(index));
                            this.notifyNormal(index);
                        }
                        break;
                    case 4:
                        if(selected) {
                            this.selectItemMulti(index);
                        } else if(this.mMapSelectedIndexItem.containsKey(Integer.valueOf(index)) && this.mMapSelectedIndexItem.size() != 1) {
                            this.mMapSelectedIndexItem.remove(Integer.valueOf(index));
                            this.notifyNormal(index);
                        }
                }

            }
        }
    }

    private void selectItemSingle(int index) {
        if(this.mCurrentIndex != index) {
            int tempCurrentIndex = this.mCurrentIndex;
            this.mCurrentIndex = index;
            this.notifyNormal(tempCurrentIndex);
            this.notifySelected(this.mCurrentIndex);
            this.mLastIndex = this.mCurrentIndex;
        }

    }

    private void selectItemMulti(int index) {
        if(!this.mMapSelectedIndexItem.containsKey(Integer.valueOf(index))) {
            this.mMapSelectedIndexItem.put(Integer.valueOf(index), this.getItem(index));
            this.notifySelected(index);
        }

    }

    private void notifyNormal(int index) {
        if(this.isIndexLegal(index)) {
            this.notifyNormal(index, this.getItem(index));
        }

    }

    private void notifySelected(int index) {
        if(this.isIndexLegal(index)) {
            this.notifySelected(index, this.getItem(index));
        }

    }

    protected void notifyNormal(final int index, final T item) {
        this.mSelectCallbackHolder.foreach(new FIterateCallback() {
            @Override
            public boolean next(int var1, SelectCallback callback, Iterator var3) {
                callback.onNormal(index, item);
                return false;
            }

//            @Override
//            public boolean next(int var1, FSelectManager.SelectCallback<T> callback, Iterator<FSelectManager.SelectCallback<T>> it) {
//                callback.onNormal(index, item);
//                return false;
//            }
        });
    }

    protected void notifySelected(final int index, final T item) {
        this.mSelectCallbackHolder.foreach(new FIterateCallback() {
            @Override
            public boolean next(int var1, SelectCallback callback, Iterator var3) {
                callback.onSelected(index, item);
                return false;
            }

//            @Override
//            public boolean next(int var1, FSelectManager.SelectCallback callback, Iterator<FSelectManager.SelectCallback<T>> it) {
//                callback.onSelected(index, item);
//                return false;
//            }
        });
    }

    public T getItem(int index) {
        Object item = null;
        if(this.isIndexLegal(index)) {
            item = this.mListItem.get(index);
        }

        return (T) item;
    }

    public int indexOfItem(T item) {
        int index = -1;
        if(item != null) {
            index = this.mListItem.indexOf(item);
        }

        return index;
    }

    public void clearSelected() {
        switch(this.mMode.ordinal()) {
            case 1:
            case 2:
                if(this.mCurrentIndex >= 0) {
                    int it1 = this.mCurrentIndex;
                    this.resetIndex();
                    this.notifyNormal(it1);
                }
                break;
            case 3:
            case 4:
                if(!this.mMapSelectedIndexItem.isEmpty()) {
                    Iterator it = this.mMapSelectedIndexItem.entrySet().iterator();

                    while(it.hasNext()) {
                        Map.Entry item = (Map.Entry)it.next();
                        it.remove();
                        this.notifyNormal(((Integer)item.getKey()).intValue());
                    }

                    this.resetIndex();
                }
        }

    }

    public interface ReSelectCallback<T> {
        void onSelected(int var1, T var2);
    }

    public interface SelectCallback<T> {
        void onNormal(int var1, T var2);

        void onSelected(int var1, T var2);
    }

    public interface Selectable {
        boolean isSelected();

        void setSelected(boolean var1);
    }

    public static enum Mode {
        SINGLE_MUST_ONE_SELECTED,
        SINGLE,
        MULTI_MUST_ONE_SELECTED,
        MULTI;

        private Mode() {
        }
    }
}
