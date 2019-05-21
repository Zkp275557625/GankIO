package com.zkp.gankio.modules.category;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.zkp.gankio.R;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.db.entity.Category;
import com.zkp.gankio.modules.category.list.CategoryListFragment;
import com.zkp.gankio.utils.DensityUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category
 * @time: 2019/5/21 11:38
 * @description:
 */
public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryFragmentContract.View {

    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<Category> mCategoryList;
    private SparseArray<CategoryListFragment> fragmentSparseArray = new SparseArray<>();

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        mPresenter = new CategoryPresenter();
        mPresenter.attachView(this);
        mPresenter.loadCategories();

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void loadCategoriesSuccess(List<Category> categoryList) {
        mCategoryList = categoryList;
        initMagicIndicator();

        initViewPager();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mCategoryList == null ? 0 : mCategoryList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(DensityUtils.dip2px(context, 15), 0, DensityUtils.dip2px(context, 15), 0);
                simplePagerTitleView.setLayoutParams(params);
                simplePagerTitleView.setText(mCategoryList.get(index).getCategoryName());
                simplePagerTitleView.setNormalColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.Grey800));
                simplePagerTitleView.setSelectedColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorWhiteTitle));
                simplePagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });

        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CategoryListFragment categoryListFragment = fragmentSparseArray.get(position);
                if (categoryListFragment != null) {
                    return categoryListFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("category", mCategoryList.get(position).getCategoryName());
                    categoryListFragment = CategoryListFragment.newInstance(bundle);
                    fragmentSparseArray.put(position, categoryListFragment);
                    return categoryListFragment;
                }
            }

            @Override
            public int getCount() {
                return mCategoryList == null ? 0 : mCategoryList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Html.fromHtml(mCategoryList.get(position).getCategoryName());
            }
        });
    }

    public void jumpToTop() {
        CategoryListFragment currentFragment = fragmentSparseArray.get(mViewPager.getCurrentItem());
        if (currentFragment != null) {
            currentFragment.jumpToTop();
        }
    }
}
