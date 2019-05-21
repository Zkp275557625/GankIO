package com.zkp.gankio.modules.home.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.NestedScrollAgentWebView;
import com.zkp.gankio.R;
import com.zkp.gankio.base.activity.BaseActivity;
import com.zkp.gankio.db.entity.Article;

import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home.detail
 * @time: 2019/5/21 9:56
 * @description:
 */
public class ArticleActivity extends BaseActivity<ArticlePresenter> implements ArticleActivityContract.View {

    @BindView(R.id.content_layout)
    CoordinatorLayout mContent;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    private AgentWeb mAgentWeb;
    private MenuItem mCollectItem;

    /**
     * 文章ID
     */
    private String articleId;
    /**
     * 文章Url
     */
    private String articleLink;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 分类
     */
    private String type;
    /**
     * 是否收藏
     */
    private boolean isCollected;
    /**
     * 是否显示收藏icon
     */
    private boolean isShowCollectIcon;

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressedSupport() {
        if (!mAgentWeb.back()) {
            super.onBackPressedSupport();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            if (!TextUtils.isEmpty(title)) {
                mTitle.setText(Html.fromHtml(title));
            }
            mTitle.setSelected(true);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {
        getBundleData();

        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setText(Html.fromHtml(title));
            }
        };

        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
        layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        NestedScrollAgentWebView mNestedWebView = new NestedScrollAgentWebView(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContent, layoutParams)
                .useDefaultIndicator()
                .setWebView(mNestedWebView)
                .setWebChromeClient(webChromeClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(articleLink);

        mPresenter = new ArticlePresenter();
        mPresenter.attachView(this);

    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        title = bundle.getString("title");
        author = bundle.getString("author");
        type = bundle.getString("type");
        articleLink = bundle.getString("articleLink");
        articleId = bundle.getString("articleId");
        isCollected = bundle.getBoolean("isCollected");
        isShowCollectIcon = bundle.getBoolean("isShowCollectIcon");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acticle, menu);
        mCollectItem = menu.findItem(R.id.item_collect);
        mCollectItem.setVisible(isShowCollectIcon);
        mCollectItem.setIcon(isCollected ? R.drawable.ic_like_white : R.drawable.ic_like_not_white);
        mPresenter.loadArticle(articleId);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                SmartToast.show("分享");
                break;
            case R.id.item_collect:
                if (isCollected) {
                    mPresenter.unCollectArticle(articleId);
                } else {
                    Article article = new Article(articleId, articleLink, title, author, type);
                    mPresenter.collectArticle(article);
                }
                isCollected = !isCollected;
                break;
            case R.id.item_system_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu      Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void loadArticleSuccess(Article article) {
        if (article.getArticleId().equals(articleId)) {
            isCollected = true;
            mCollectItem.setIcon(R.drawable.ic_like_white);
        }
    }

    @Override
    public void collectArticleSuccess() {
        mCollectItem.setIcon(R.drawable.ic_like_white);
    }

    @Override
    public void unCollectArticleSuccess() {
        mCollectItem.setIcon(R.drawable.ic_like_not_white);
    }

}
