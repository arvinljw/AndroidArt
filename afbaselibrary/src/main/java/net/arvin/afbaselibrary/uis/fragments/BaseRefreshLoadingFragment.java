package net.arvin.afbaselibrary.uis.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.uis.adapters.MultiItemTypeAdapter;
import net.arvin.afbaselibrary.uis.adapters.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * created by arvin on 16/11/21 20:54
 * email：1035407623@qq.com
 */
public abstract class BaseRefreshLoadingFragment<T> extends BaseFragment implements SwipeRefreshHelper.OnSwipeRefreshListener,
        OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener<T> {
    protected final int FIRST_PAGE = 0;
    protected SwipeRefreshHelper mSwipeRefreshHelper;
    protected SwipeRefreshLayout mLayoutRefresh;
    protected RecyclerView mRecyclerView;

    protected List<T> mItems = new ArrayList<>();
    protected MultiItemTypeAdapter<T> mInnerAdapter;
    protected EmptyWrapper<T> mEmptyWrapper;
    protected RecyclerAdapterWithHF mAdapter;

    protected RecyclerView.LayoutManager mLayoutManager;
    protected int mCurrPage = FIRST_PAGE;

    @Override
    public void init(Bundle savedInstanceState) {
        mLayoutRefresh = getView(R.id.pre_refresh);
        mRecyclerView = getView(R.id.pre_recycler_view);

        mLayoutRefresh.setColorSchemeResources(R.color.colorAccent);

        setupRecyclerView();

        setupRefreshAndLoadMore();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(initLayoutManager());
        mRecyclerView.setAdapter(initAdapter());
        if (isShowDivider()) setDivider();
    }


    protected void setDivider() {
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.black_divider))
                .sizeResId(R.dimen.spacing_divider)
                .build());
    }

    protected boolean isShowDivider() {
        return true;
    }

    protected RecyclerView.LayoutManager initLayoutManager() {
        mLayoutManager = getLayoutManager();
        return mLayoutManager;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    private RecyclerAdapterWithHF initAdapter() {
        mInnerAdapter = getAdapter();
        mInnerAdapter.setOnItemClickListener(this);
        mEmptyWrapper = new EmptyWrapper<>(mInnerAdapter);
        mEmptyWrapper.setEmptyView(getEmptyViewId());
        mAdapter = new RecyclerAdapterWithHF(mEmptyWrapper);
        return mAdapter;
    }

    protected int getEmptyViewId() {
        return R.layout.ui_layout_empty;
    }

    private void setupRefreshAndLoadMore() {
        mSwipeRefreshHelper = new SwipeRefreshHelper(mLayoutRefresh);
        mSwipeRefreshHelper.setOnSwipeRefreshListener(this);
        mSwipeRefreshHelper.setOnLoadMoreListener(this);

        autoRefresh();
    }

    protected void autoRefresh() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshHelper.autoRefresh();
            }
        }, 100);
    }

    @Override
    public void onfresh() {
        mCurrPage = FIRST_PAGE;
        loadData(mCurrPage);
    }

    @Override
    public void loadMore() {
        loadData(++mCurrPage);
    }

    protected void refreshComplete(boolean loadSuccess) {
        if (!loadSuccess && mCurrPage > FIRST_PAGE) {
            mCurrPage--;
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshHelper.refreshComplete();
        mSwipeRefreshHelper.setLoadMoreEnable(loadSuccess && mItems.size() >= 15);
        if (mCurrPage > FIRST_PAGE) {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, T item, int position) {
        Logger.d(position);
    }

    protected abstract MultiItemTypeAdapter<T> getAdapter();

    protected abstract void loadData(int page);

}
