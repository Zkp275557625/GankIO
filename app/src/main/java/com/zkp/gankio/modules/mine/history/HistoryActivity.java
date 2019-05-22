package com.zkp.gankio.modules.mine.history;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zkp.gankio.R;
import com.zkp.gankio.base.activity.BaseActivity;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.HistoryContentBean;
import com.zkp.gankio.beans.HistoryGankBean;
import com.zkp.gankio.beans.HistoryGankItem;
import com.zkp.gankio.modules.mine.history.adapter.HistoryListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.history
 * @time: 2019/5/22 13:57
 * @description:
 */
public class HistoryActivity extends BaseActivity<HistoryPresenter> implements HistoryActivityContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private HistoryListAdapter mAdapter;

    private List<String> mHistoryGankDateList;
    private List<HistoryGankItem> mHistoryGankItemList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.gank_history);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initView() {
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mHistoryGankDateList = new ArrayList<>();
        mHistoryGankItemList = new ArrayList<>();
        mAdapter = new HistoryListAdapter(R.layout.item_history_list, mHistoryGankItemList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {

        mPresenter = new HistoryPresenter();
        mPresenter.attachView(this);
        mAdapter.setmPresenter(mPresenter);
        mPresenter.getHistoryGank();

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh();
            refreshLayout.finishRefresh();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }

    @Override
    public void getImagesSuccess(BannerBean data, boolean isFresh) {
        if (mAdapter == null) {
            return;
        }

        if (isFresh) {
            mHistoryGankItemList.clear();
            for (int i = 0; i < data.getResults().size(); i++) {
                HistoryGankItem item = new HistoryGankItem();
                item.setIamgeUrl(data.getResults().get(i).getUrl());
                item.setTime(mHistoryGankDateList.get(i));
                mHistoryGankItemList.add(item);
            }
            mAdapter.replaceData(mHistoryGankItemList);

        } else {

            int size = mHistoryGankItemList.size();
            for (int i = 0; i < data.getResults().size(); i++) {
                HistoryGankItem item = new HistoryGankItem();
                item.setIamgeUrl(data.getResults().get(i).getUrl());
                item.setTime(mHistoryGankDateList.get(size + i));
                mHistoryGankItemList.add(item);
            }
            mAdapter.replaceData(mHistoryGankItemList);
        }
    }

    @Override
    public void getImagesError(String errMsg) {
        SmartToast.show(errMsg);
    }

    @Override
    public void getHistoryGankSuccess(HistoryGankBean historyGankBean) {
        if (mAdapter == null) {
            return;
        }
        mHistoryGankDateList = historyGankBean.getResults();
        mPresenter.refresh();
    }

    @Override
    public void getHistoryGankError(String errMsg) {
        SmartToast.show(errMsg);
    }

    @Override
    public void getHistoryGankContentSuccess(HistoryContentBean data, BaseViewHolder helper) {
        Log.d("qwe", data.getResults().get(0).getTitle());
        helper.setText(R.id.tvTitle, data.getResults().get(0).getTitle());
    }

    @Override
    public void getHistoryGankContentError(String errMsg) {
        SmartToast.show(errMsg);
    }
}
