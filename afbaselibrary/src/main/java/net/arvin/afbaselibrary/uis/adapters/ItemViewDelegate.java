package net.arvin.afbaselibrary.uis.adapters;


import net.arvin.afbaselibrary.uis.adapters.holders.CommonHolder;

/**
 * Created by zhy on 16/6/22
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(CommonHolder holder, T t, int position);
}
