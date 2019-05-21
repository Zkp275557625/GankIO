package com.zkp.gankio.modules.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zkp.gankio.R;
import com.zkp.gankio.app.App;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.beans.BaseGankBean;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.db.entity.Category;
import com.zkp.gankio.modules.home.adapter.HomeArticlesAdapter;
import com.zkp.gankio.modules.home.detail.ArticleActivity;
import com.zkp.gankio.utils.itemdecoration.GroupItem;
import com.zkp.gankio.utils.itemdecoration.GroupItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private HomeArticlesAdapter mAdapter;
    private List<BaseGankBean> mBaseGankBeanList;
    private GroupItemDecoration mGroupItemDecoration;

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
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getTodayGank();
            refreshLayout.finishRefresh();
        });

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
    }

    @Override
    public void getTodayGankSuceess(TodayGankBean data) {
        if (mGroupItemDecoration == null) {
            @SuppressLint("InflateParams")
            View groupView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, null);
            mGroupItemDecoration = new GroupItemDecoration(getContext(), groupView, new GroupItemDecoration.DecorationCallback() {
                @Override
                public void setGroup(List<GroupItem> groupList) {
                    addDataToGroup(groupList, data);
                }

                @Override
                public void buildGroupView(View groupView, GroupItem groupItem) {
                    TextView textName = groupView.findViewById(R.id.tvCategory);
                    textName.setText(groupItem.getData("category").toString());
                }
            });

            mRecyclerView.addItemDecoration(mGroupItemDecoration);
        }

        addDataToAdapter(data);

        addCategories(data.getCategory());

        mAdapter.addData(mBaseGankBeanList);
    }

    @Override
    public void getTodayGankError(String errMsg) {
        SmartToast.info(errMsg);
    }

    @Override
    public void addCategoriesSuccess() {

    }

    private void addDataToGroup(List<GroupItem> groupList, TodayGankBean data) {
        for (int i = 0; i < data.getCategory().size(); i++) {
            GroupItem groupItem;
            if (i == 0) {
                groupItem = new GroupItem(0);
                groupItem.setData("category", data.getCategory().get(0));
                groupList.add(groupItem);
            } else {
                switch (data.getCategory().get(i)) {
                    case "iOS":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getiOS().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "拓展资源":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get拓展资源().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "瞎推荐":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get瞎推荐().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "Android":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getAndroid().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "App":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().getApp().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "前端":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get前端().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        break;
                    case "福利":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get福利().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    case "休息视频":
                        groupItem = new GroupItem(groupList.get(groupList.size() - 1).getStartPosition() + data.getResults().get休息视频().size());
                        groupItem.setData("category", data.getCategory().get(i));
                        groupList.add(groupItem);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void addDataToAdapter(TodayGankBean data) {

        if (mBaseGankBeanList == null) {
            mBaseGankBeanList = new ArrayList<>();
        } else {
            mBaseGankBeanList.clear();
        }

        for (int i = 0; i < data.getCategory().size(); i++) {
            switch (data.getCategory().get(i)) {
                case "iOS":
                    mBaseGankBeanList.addAll(data.getResults().getiOS());
                    break;
                case "拓展资源":
                    mBaseGankBeanList.addAll(data.getResults().get拓展资源());
                    break;
                case "瞎推荐":
                    mBaseGankBeanList.addAll(data.getResults().get瞎推荐());
                    break;
                case "Android":
                    mBaseGankBeanList.addAll(data.getResults().getAndroid());
                    break;
                case "App":
                    mBaseGankBeanList.addAll(data.getResults().getApp());
                    break;
                case "前端":
                    mBaseGankBeanList.addAll(data.getResults().get前端());
                    break;
                case "福利":
                    mBaseGankBeanList.addAll(data.getResults().get福利());
                    break;
                case "休息视频":
                    mBaseGankBeanList.addAll(data.getResults().get休息视频());
                    break;
                default:
                    break;
            }
        }
    }

    private void addCategories(List<String> categories) {
        List<Category> categoryList = new ArrayList<>();
        for (String categoryStr : categories) {
            Category category = new Category(categoryStr);
            categoryList.add(category);
        }
        mPresenter.addCategories(categoryList);
    }

    public void jumpToTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
}
