package com.zkp.gankio.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.coder.zzq.smartshow.core.SmartShow;
import com.zkp.gankio.base.activity.BaseActivity;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.crash.UnCaughtHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.app
 * @time: 2019/5/20 13:49
 * @description:
 */
public class App extends Application {

    private static Context mContext;
    private static App mApplication;

    private List<BaseActivity> mActivityList;
    private List<BaseFragment> mFragmentsList;

    public static App getApplication() {
        return mApplication;
    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mApplication = this;

        mActivityList = new ArrayList<>();
        mFragmentsList = new ArrayList<>();

        SmartShow.init(mApplication);

    }

    public void addActivity(BaseActivity activity) {
        mActivityList.add(activity);
    }

    public void addFragment(BaseFragment fragment) {
        mFragmentsList.add(fragment);
    }

    /**
     * 闪退重启
     */
    public void initUnCaughtHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtHandler(this));
    }

    /**
     * 退出应用
     */
    public void exitApplication() {
        for (BaseActivity activity : mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        for (BaseFragment fragment : mFragmentsList) {
            if (fragment != null) {
                Objects.requireNonNull(fragment.getActivity()).onBackPressed();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
