package com.zkp.gankio.modules.category.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zkp.gankio.R;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.beans.CategoryBean;
import com.zkp.gankio.modules.category.list.adapter.CategoryListAdapter;
import com.zkp.gankio.modules.category.list.adapter.CategoryWelfareAdapter;
import com.zkp.gankio.modules.home.detail.ArticleActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category.list
 * @time: 2019/5/21 14:22
 * @description:
 */
public class CategoryListFragment extends BaseFragment<CategoryListPresenter> implements CategoryListFragmentContract.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    /**
     * 当前分类
     */
    private String mCategory;
    private CategoryListAdapter mAdapter;
    private CategoryWelfareAdapter mAdapterWelfare;

    public static CategoryListFragment newInstance(Bundle bundle) {
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_list;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initEventAndData() {
        assert getArguments() != null;
        mCategory = getArguments().getString("category");

        switchAdapter();

        mPresenter = new CategoryListPresenter();
        mPresenter.attachView(this);
        mPresenter.refresh(mCategory);

        initRefreshLayout();
    }

    /**
     * 福利、休息视频，使用的adapter不一样
     */
    private void switchAdapter() {
        List<CategoryBean.ResultsBean> resultsBeanList = new ArrayList<>();
        switch (mCategory) {
            case "福利":
                mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mAdapterWelfare = new CategoryWelfareAdapter(R.layout.item_catrgory_welfare, resultsBeanList);
                mRecyclerView.setAdapter(mAdapterWelfare);
                break;
            case "休息视频":
            case "iOS":
            case "拓展资源":
            case "瞎推荐":
            case "Android":
            case "App":
            case "前端":
                mAdapter = new CategoryListAdapter(R.layout.item_home_article, resultsBeanList);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("articleId", Objects.requireNonNull(mAdapter.getItem(position)).get_id());
                    intent.putExtra("articleLink", Objects.requireNonNull(mAdapter.getItem(position)).getUrl());
                    intent.putExtra("title", Objects.requireNonNull(mAdapter.getItem(position)).getDesc());
                    intent.putExtra("author", Objects.requireNonNull(mAdapter.getItem(position)).getWho());
                    intent.putExtra("type", Objects.requireNonNull(mAdapter.getItem(position)).getType());
                    intent.putExtra("isCollected", false);
                    intent.putExtra("isShowCollectIcon", true);
                    startActivity(intent);
                });

                break;
            default:
                break;
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh(mCategory);
            refreshLayout.finishRefresh();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }

    public void jumpToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void getCategorySuccess(CategoryBean data, boolean isFresh) {
        if ("福利".equals(mCategory)) {
            if (mAdapterWelfare == null) {
                return;
            }
            if (isFresh) {
                mAdapterWelfare.replaceData(data.getResults());
            } else {
                mAdapterWelfare.addData(data.getResults());
            }
        } else {
            if (mAdapter == null) {
                return;
            }
            if (isFresh) {
                mAdapter.replaceData(data.getResults());
            } else {
                mAdapter.addData(data.getResults());
            }
        }
    }

    @Override
    public void getCategoryError(String errMsg) {
        SmartToast.show(errMsg);
    }
}
