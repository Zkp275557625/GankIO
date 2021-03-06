package com.zkp.gankio.modules;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.zkp.gankio.R;
import com.zkp.gankio.app.App;
import com.zkp.gankio.base.activity.BaseActivity;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.modules.category.CategoryFragment;
import com.zkp.gankio.modules.home.HomeFragment;
import com.zkp.gankio.modules.mine.MineFragment;
import com.zkp.gankio.modules.read.ReadFragment;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules
 * @time: 2019/5/20 14:00
 * @description:
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.faBtn)
    FloatingActionButton mFaBtn;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    /**
     * 当前Fragment页面索引
     */
    private int mCurrentFgIndex = 0;
    /**
     * 上一次Fragment页面索引
     */
    private int mLastFgIndex = -1;

    private long clickTime;

    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private ReadFragment mReadFragment;
    private MineFragment mMineFragment;

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > AppConfig.EXIT_TIME) {
                SmartToast.show("再按一次退出应用");
                clickTime = System.currentTimeMillis();
            } else {
                App.getApplication().exitApplication();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppConfig.CURRENT_FRAGMENT_KEY, mCurrentFgIndex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.home_pager);
        }
    }

    @Override
    protected void initView() {
        //闪退重启、日志收集，保存在 玩安卓/Crash 下
//        App.getApplication().initUnCaughtHandler();
//        App.getApplication().addActivity(this);

        showFragment(mCurrentFgIndex);
        initBottomNavigationView();

    }

    @Override
    protected void initEventAndData() {
        mFaBtn.setOnClickListener(view -> {
            //跳转到页面顶部
            jumpToTop();
        });
    }

    private void jumpToTop() {
        switch (mCurrentFgIndex) {
            case AppConfig.TYPE_HOME:
                if (mHomeFragment != null) {
                    mHomeFragment.jumpToTop();
                }
                break;
            case AppConfig.TYPE_CATRGORY:
                if (mCategoryFragment != null) {
                    mCategoryFragment.jumpToTop();
                }
                break;
            case AppConfig.TYPE_READ:
                if (mReadFragment != null) {
                    mReadFragment.jumpToTop();
                }
                break;
//            case AppConfig.TYPE_MINE:
//                if (mNavigationFragment != null) {
//                    mNavigationFragment.jumpToTop();
//                }
//                break;
            default:
                break;
        }
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index) {
            case AppConfig.TYPE_HOME:
                mTitle.setText(getString(R.string.home_pager));
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.frameLayout, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            case AppConfig.TYPE_CATRGORY:
                mTitle.setText(getString(R.string.category_pager));
                if (mCategoryFragment == null) {
                    mCategoryFragment = CategoryFragment.newInstance();
                    transaction.add(R.id.frameLayout, mCategoryFragment);
                }
                transaction.show(mCategoryFragment);
                break;
            case AppConfig.TYPE_READ:
                mTitle.setText(getString(R.string.read_pager));
                if (mReadFragment == null) {
                    mReadFragment = ReadFragment.newInstance();
                    transaction.add(R.id.frameLayout, mReadFragment);
                }
                transaction.show(mReadFragment);
                break;
            case AppConfig.TYPE_MINE:
                mTitle.setText(getString(R.string.mine_pager));
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                    transaction.add(R.id.frameLayout, mMineFragment);
                }
                transaction.show(mMineFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void initBottomNavigationView() {
        mNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(AppConfig.TYPE_HOME);
                    break;
                case R.id.navigation_category:
                    showFragment(AppConfig.TYPE_CATRGORY);
                    break;
                case R.id.navigation_read:
                    showFragment(AppConfig.TYPE_READ);
                    break;
                case R.id.navigation_mine:
                    showFragment(AppConfig.TYPE_MINE);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void hideFragment(FragmentTransaction transaction) {
        switch (mLastFgIndex) {
            case AppConfig.TYPE_HOME:
                if (mHomeFragment != null) {
                    transaction.hide(mHomeFragment);
                }
                break;
            case AppConfig.TYPE_CATRGORY:
                if (mCategoryFragment != null) {
                    transaction.hide(mCategoryFragment);
                }
                break;
            case AppConfig.TYPE_READ:
                if (mReadFragment != null) {
                    transaction.hide(mReadFragment);
                }
                break;
            case AppConfig.TYPE_MINE:
                if (mMineFragment != null) {
                    transaction.hide(mMineFragment);
                }
                break;
            default:
                break;
        }
    }

}
