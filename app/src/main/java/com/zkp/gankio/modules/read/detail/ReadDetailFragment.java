package com.zkp.gankio.modules.read.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zkp.gankio.R;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.beans.ReadDetailBean;
import com.zkp.gankio.modules.home.detail.ArticleActivity;
import com.zkp.gankio.modules.read.detail.adapter.ReadDetailListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.detail
 * @time: 2019/5/21 17:05
 * @description:
 */
public class ReadDetailFragment extends BaseFragment<ReadDetailPresenter> implements ReadDetailFragmentContract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    /**
     * 闲读子分类的id
     */
    private String id;
    private ReadDetailListAdapter mAdapter;

    public static ReadDetailFragment newInstance(Bundle bundle) {
        ReadDetailFragment fragment = new ReadDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read_detail;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        List<ReadDetailBean.ResultsBean> resultsBeanList = new ArrayList<>();
        mAdapter = new ReadDetailListAdapter(R.layout.item_read_detail, resultsBeanList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            intent.putExtra("articleId", Objects.requireNonNull(mAdapter.getItem(position)).get_id());
            intent.putExtra("articleLink", Objects.requireNonNull(mAdapter.getItem(position)).getUrl());
            intent.putExtra("title", Objects.requireNonNull(mAdapter.getItem(position)).getTitle());
            intent.putExtra("author", Objects.requireNonNull(mAdapter.getItem(position)).getSite().getName());
            intent.putExtra("type", Objects.requireNonNull(mAdapter.getItem(position)).getSite().getType());
            intent.putExtra("isCollected", false);
            intent.putExtra("isShowCollectIcon", true);
            startActivity(intent);
        });
    }

    @Override
    protected void initEventAndData() {
        assert getArguments() != null;
        id = getArguments().getString("id");

        mPresenter = new ReadDetailPresenter();
        mPresenter.attachView(this);
        mPresenter.refresh(id);

        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(id);
            refreshLayout.finishRefresh();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }

    @Override
    public void getReadDetailListSuccess(ReadDetailBean data, boolean isFresh) {

        if (mAdapter == null) {
            return;
        }
        if (isFresh) {
            mAdapter.replaceData(data.getResults());
        } else {
            mAdapter.addData(data.getResults());
        }
    }

    @Override
    public void getReadDetailListError(String errMsg) {
        SmartToast.show(errMsg);
    }

    public void jumpToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

}
