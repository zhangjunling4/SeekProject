package com.fanwe.lib.select.utils;

import com.fanwe.lib.select.callback.FIterateCallback;
import com.fanwe.lib.select.callback.FSimpleIterateCallback;
import com.fanwe.lib.select.view.FSelectManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2018/1/4.
 */

public class SDCollectionUtil {
    public SDCollectionUtil() {
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isIndexLegal(List<T> list, int index) {
        return !isEmpty(list) && index >= 0 && index < list.size();
    }

    public static <T> boolean iterate(Iterable<T> iterable, FIterateCallback<T> callback) {
        if(iterable != null && callback != null) {
            Iterator it = iterable.iterator();

            for(int i = 0; it.hasNext(); ++i) {
                Object item = it.next();
                if(callback.next(i, (FSelectManager.SelectCallback) item, it)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> boolean foreach(List<T> list, FIterateCallback<T> callback) {
        if(!isEmpty(list) && callback != null) {
            int size = list.size();

            for(int i = 0; i < size; ++i) {
                if(callback.next(i, (FSelectManager.SelectCallback) list.get(i), (Iterator)null)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> boolean foreachReverse(List<T> list, FIterateCallback<T> callback) {
        if(!isEmpty(list) && callback != null) {
            int size = list.size();

            for(int i = size - 1; i >= 0; --i) {
                if(callback.next(i, (FSelectManager.SelectCallback) list.get(i), (Iterator)null)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> boolean foreach(int count, FSimpleIterateCallback callback) {
        if(count > 0 && callback != null) {
            for(int i = 0; i < count; ++i) {
                if(callback.next(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> boolean foreachReverse(int count, FSimpleIterateCallback callback) {
        if(count > 0 && callback != null) {
            for(int i = count - 1; i >= 0; --i) {
                if(callback.next(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <T> T get(List<T> list, int index) {
        Object t = null;
        if(isIndexLegal(list, index)) {
            t = list.get(index);
        }

        return (T) t;
    }

    public static <T> T getLast(List<T> list, int index) {
        Object t = null;
        if(isIndexLegal(list, index)) {
            index = list.size() - 1 - index;
            t = list.get(index);
        }

        return (T) t;
    }

    public static <T> List<T> getTempList(List<T> list) {
        ArrayList listReturn = null;
        if(list != null) {
            listReturn = new ArrayList();
            listReturn.addAll(list);
        }

        return listReturn;
    }

    public static <T> List<T> subListToSize(List<T> list, int size) {
        ArrayList listReturn = null;
        if(!isEmpty(list) && list.size() >= size && size > 0) {
            listReturn = new ArrayList();

            for(int i = 0; i < size; ++i) {
                Object t = list.get(i);
                listReturn.add(t);
            }
        }

        return listReturn;
    }

    public static <T> List<T> subListToSizeAvailable(List<T> list, int size) {
        ArrayList listReturn = null;
        if(!isEmpty(list) && size > 0) {
            boolean loopCount = false;
            int listSize = list.size();
            int var7;
            if(size <= listSize) {
                var7 = size;
            } else {
                var7 = listSize;
            }

            listReturn = new ArrayList();

            for(int i = 0; i < var7; ++i) {
                Object t = list.get(i);
                listReturn.add(t);
            }
        }

        return listReturn;
    }

    public static <T> T[] toArray(List<T> list) {
        Object[] arr = null;
        if(!isEmpty(list)) {
            Object item = list.get(0);
            if(item != null) {
                Class clazzItem = item.getClass();
                arr = (Object[])((Object[]) Array.newInstance(clazzItem, list.size()));
                list.toArray(arr);
            }
        }

        return (T[]) arr;
    }

    public static <T> List<List<T>> splitList(List<T> listModel, int countPerList) {
        ArrayList listGroupModel = new ArrayList();
        ArrayList listPageModel = new ArrayList();
        if(listModel != null && !listModel.isEmpty()) {
            for(int i = 0; i < listModel.size(); ++i) {
                listPageModel.add(listModel.get(i));
                if(i != 0 && (i + 1) % countPerList == 0) {
                    listGroupModel.add(listPageModel);
                    listPageModel = new ArrayList();
                }
            }

            if(listPageModel.size() > 0) {
                listGroupModel.add(listPageModel);
            }
        }

        return listGroupModel;
    }

    public static <T> List<List<T>> splitListLinked(List<T> listModel, int countPerList) {
        ArrayList listGroupModel = new ArrayList();
        ArrayList listPageModel = new ArrayList();
        if(listModel != null && !listModel.isEmpty()) {
            boolean needBackIndex = false;

            for(int i = 0; i < listModel.size(); ++i) {
                if(needBackIndex) {
                    needBackIndex = false;
                    listPageModel.add(listModel.get(i - 1));
                } else {
                    listPageModel.add(listModel.get(i));
                }

                if(i != 0 && (i + 1) % countPerList == 0) {
                    needBackIndex = true;
                    listGroupModel.add(listPageModel);
                    listPageModel = new ArrayList();
                }
            }

            if(listPageModel.size() > 0) {
                listGroupModel.add(listPageModel);
            }
        }

        return listGroupModel;
    }

    public static <T> List<T> subList(List<T> list, int start, int end) {
        ArrayList listReturn = null;
        if(end >= start && isIndexLegal(list, start) && isIndexLegal(list, end)) {
            listReturn = new ArrayList();

            for(int i = start; i <= end; ++i) {
                Object t = list.get(i);
                listReturn.add(t);
            }
        }

        return listReturn;
    }

    public static <T> List<T> subList(List<T> list, int start) {
        ArrayList listReturn = null;
        if(isIndexLegal(list, start)) {
            listReturn = new ArrayList();

            for(int i = start; i < list.size(); ++i) {
                Object t = list.get(i);
                listReturn.add(t);
            }
        }

        return listReturn;
    }

    public static <T> void removeList(List<T> list, int start, int end) {
        if(end >= start && isIndexLegal(list, start) && isIndexLegal(list, end)) {
            Iterator it = list.iterator();

            for(int i = 0; it.hasNext(); ++i) {
                if(i >= start) {
                    if(i > end) {
                        break;
                    }

                    it.next();
                    it.remove();
                }
            }
        }

    }
}
