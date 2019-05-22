package com.zkp.gankio.modules.mine.collect;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zkp.gankio.R;
import com.zkp.gankio.base.activity.BaseActivity;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.modules.home.detail.ArticleActivity;
import com.zkp.gankio.modules.mine.collect.adapter.CollectListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.collect
 * @time: 2019/5/22 10:03
 * @description:
 */
public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectActivityContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private CollectListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.mine_collect);
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

        List<Article> articleList = new ArrayList<>();
        mAdapter = new CollectListAdapter(R.layout.item_collect_list, articleList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initEventAndData() {
        mPresenter = new CollectPresenter();
        mPresenter.attachView(this);
        mPresenter.refresh();

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refresh();
            refreshLayout.finishRefresh();
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(CollectActivity.this, ArticleActivity.class);
            intent.putExtra("articleId", Objects.requireNonNull(mAdapter.getItem(position)).getArticleId());
            intent.putExtra("articleLink", Objects.requireNonNull(mAdapter.getItem(position)).getArticleLink());
            intent.putExtra("title", Objects.requireNonNull(mAdapter.getItem(position)).getArticleTitle());
            intent.putExtra("author", Objects.requireNonNull(mAdapter.getItem(position)).getArticleAuthor());
            intent.putExtra("type", Objects.requireNonNull(mAdapter.getItem(position)).getArticleTyppe());
            intent.putExtra("isCollected", true);
            intent.putExtra("isShowCollectIcon", true);
            startActivity(intent);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //收藏页面只要取消收藏
            mPresenter.unCollectArticle(Objects.requireNonNull(mAdapter.getItem(position)).getArticleId());
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.refresh();
    }

    @Override
    public void loadArticlesSuccess(List<Article> articleList, boolean isFresh) {
        if (mAdapter == null) {
            return;
        }
        if (isFresh) {
            mAdapter.replaceData(articleList);
        } else {
            mAdapter.addData(articleList);
        }
    }

    @Override
    public void unCollectArticleSuccess() {
        mPresenter.refresh();
    }
}
