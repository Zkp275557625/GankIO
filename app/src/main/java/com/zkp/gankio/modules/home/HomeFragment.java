package com.zkp.gankio.modules.home;

import android.annotation.SuppressLint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zkp.gankio.R;
import com.zkp.gankio.app.App;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.BaseGankBean;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.modules.home.adapter.HomeArticlesAdapter;
import com.zkp.gankio.utils.GlideImageLoader;
import com.zkp.gankio.utils.itemdecoration.GroupItem;
import com.zkp.gankio.utils.itemdecoration.GroupItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home
 * @time: 2019/5/20 14:55
 * @description:
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeFragmentContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private Banner mBanner;

    private List<String> mImageUrlList;
    private HomeArticlesAdapter mAdapter;
    private List<BaseGankBean> baseGankBeanList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        App.getApplication().addFragment(this);

        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        List<BaseGankBean> resultsBeanList = new ArrayList<>();
        mAdapter = new HomeArticlesAdapter(R.layout.item_home_article, resultsBeanList);
        mRecyclerView.setAdapter(mAdapter);

        @SuppressLint("InflateParams")
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.frgament_home_banner, null);
        mBanner = layout.findViewById(R.id.banner);
        layout.removeView(mBanner);
        mAdapter.setHeaderView(mBanner);

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mPresenter.registerEventBus();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    mPresenter.getTodayGank();
                })
                .onDenied(permissions -> {
                    AndPermission.with(this)
                            .runtime()
                            .permission(Permission.Group.STORAGE)
                            .start();
                })
                .start();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void getBannerSuccess(BannerBean data) {
        if (mImageUrlList == null) {
            mImageUrlList = new ArrayList<>();
        } else {
            mImageUrlList.clear();
        }


        for (BannerBean.ResultsBean dataBean : data.getResults()) {
            mImageUrlList.add(dataBean.getUrl());
        }

        //设置图片集合
        mBanner.setImages(mImageUrlList);
        mBanner.start();
    }

    @Override
    public void getBannerError(String errMsg) {
        SmartToast.info(errMsg);
    }

    @Override
    public void getTodayGankSuceess(TodayGankBean data) {
        Log.d("qwe", data.toString());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams")
        View groupView = layoutInflater.inflate(R.layout.item_group, null);
        mRecyclerView.addItemDecoration(new GroupItemDecoration(getContext(), groupView, new GroupItemDecoration.DecorationCallback() {
            @Override
            public void setGroup(List<GroupItem> groupList) {

                for (int i = 0; i < data.getCategory().size(); i++) {
                    GroupItem groupItem;
                    switch (data.getCategory().get(i)) {
                        case "iOS":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getiOS().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "拓展资源":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get拓展资源().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "瞎推荐":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get瞎推荐().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "Android":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getAndroid().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "App":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getApp().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "前端":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get前端().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            break;
                        case "福利":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get福利().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        case "休息视频":
                            if (groupList.size() == 0) {
                                groupItem = new GroupItem(1);
                            } else {
                                groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get休息视频().size());
                            }
                            groupItem.setData("category", data.getCategory().get(i));
                            groupList.add(groupItem);
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void buildGroupView(View groupView, GroupItem groupItem) {
                TextView textName = groupView.findViewById(R.id.tvCategory);
                textName.setText(groupItem.getData("category").toString());
            }
        }));

        if (baseGankBeanList == null) {
            baseGankBeanList = new ArrayList<>();
        } else {
            baseGankBeanList.clear();
        }

        for (int i = 0; i < data.getCategory().size(); i++) {
            switch (data.getCategory().get(i)) {
                case "iOS":
                    baseGankBeanList.addAll(data.getResults().getiOS());
                    break;
                case "拓展资源":
                    baseGankBeanList.addAll(data.getResults().get拓展资源());
                    break;
                case "瞎推荐":
                    baseGankBeanList.addAll(data.getResults().get瞎推荐());
                    break;
                case "Android":
                    baseGankBeanList.addAll(data.getResults().getAndroid());
                    break;
                case "App":
                    baseGankBeanList.addAll(data.getResults().getApp());
                    break;
                case "前端":
                    baseGankBeanList.addAll(data.getResults().get前端());
                    break;
                case "福利":
                    baseGankBeanList.addAll(data.getResults().get福利());
                    break;
                case "休息视频":
                    baseGankBeanList.addAll(data.getResults().get休息视频());
                    break;
                default:
                    break;
            }
        }

        mAdapter.addData(baseGankBeanList);
        mPresenter.getBanner();
    }

    @Override
    public void getTodayGankError(String errMsg) {
        SmartToast.info(errMsg);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mBanner != null) {
                mBanner.stopAutoPlay();
            }
        } else {
            if (mBanner != null) {
                mBanner.startAutoPlay();
            }
        }
    }

    public void jumpToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
