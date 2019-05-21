package com.zkp.gankio.modules.read.child;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.zkp.gankio.R;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.beans.ReadCategoryChildBean;
import com.zkp.gankio.modules.read.detail.ReadDetailFragment;
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
 * @package: com.zkp.gankio.modules.read.child
 * @time: 2019/5/21 16:32
 * @description:
 */
public class ReadChildFragment extends BaseFragment<ReadChildPresenter> implements ReadChildFragmentContract.View {

    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    /**
     * 主分类英文名称
     */
    private String enName;
    private List<ReadCategoryChildBean.ResultsBean> mResultsBeanList;
    private SparseArray<ReadDetailFragment> fragmentSparseArray = new SparseArray<>();

    public static ReadChildFragment newInstance(Bundle bundle) {
        ReadChildFragment fragment = new ReadChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read_child;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {
        assert getArguments() != null;
        enName = getArguments().getString("enName");

        mPresenter = new ReadChildPresenter();
        mPresenter.attachView(this);
        mPresenter.getReadCategoryChild(enName);
    }

    @Override
    public void getReadCategoryChildSuccess(ReadCategoryChildBean data) {
        mResultsBeanList = data.getResults();
        initMagicIndicator();
        initViewPager();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mResultsBeanList == null ? 0 : mResultsBeanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(DensityUtils.dip2px(context, 15), 0, DensityUtils.dip2px(context, 15), 0);
                simplePagerTitleView.setLayoutParams(params);
                simplePagerTitleView.setText(mResultsBeanList.get(index).getTitle());
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
                ReadDetailFragment readDetailFragment = fragmentSparseArray.get(position);
                if (readDetailFragment != null) {
                    return readDetailFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mResultsBeanList.get(position).getId());
                    readDetailFragment = ReadDetailFragment.newInstance(bundle);
                    fragmentSparseArray.put(position, readDetailFragment);
                    return readDetailFragment;
                }
            }

            @Override
            public int getCount() {
                return mResultsBeanList == null ? 0 : mResultsBeanList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Html.fromHtml(mResultsBeanList.get(position).getTitle());
            }
        });
    }

    @Override
    public void getReadCategoryChildError(String errMsg) {
        SmartToast.show(errMsg);
    }
}
