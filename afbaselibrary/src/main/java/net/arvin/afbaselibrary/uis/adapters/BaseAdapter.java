package net.arvin.afbaselibrary.uis.adapters;

import android.content.Context;
import android.view.LayoutInflater;

import net.arvin.afbaselibrary.uis.adapters.holders.CommonHolder;

import java.util.List;

/**
 * created by arvin on 16/10/24 15:01
 * email：1035407623@qq.com
 */
public abstract class BaseAdapter <T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mItems;
    protected LayoutInflater mInflater;

    public BaseAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mItems = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(CommonHolder holder, T t, int position) {
                BaseAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(CommonHolder holder, T item, int position);

}
