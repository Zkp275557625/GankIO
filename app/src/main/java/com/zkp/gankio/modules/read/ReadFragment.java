package com.zkp.gankio.modules.read;

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
import com.zkp.gankio.beans.ReadCategoryMainBean;
import com.zkp.gankio.modules.read.child.ReadChildFragment;
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
 * @package: com.zkp.gankio.modules.read
 * @time: 2019/5/21 15:46
 * @description:
 */
public class ReadFragment extends BaseFragment<ReadPresenter> implements ReadFragmentContract.View {

    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<ReadCategoryMainBean.ResultsBean> mResultsBeanList;
    private SparseArray<ReadChildFragment> fragmentSparseArray = new SparseArray<>();

    public static ReadFragment newInstance() {
        return new ReadFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initView() {
        mPresenter = new ReadPresenter();
        mPresenter.attachView(this);
        mPresenter.getReadCategoryMain();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void getReadCategoryMainSuccess(ReadCategoryMainBean data) {
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
                simplePagerTitleView.setText(mResultsBeanList.get(index).getName());
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
                ReadChildFragment readChildFragment = fragmentSparseArray.get(position);
                if (readChildFragment != null) {
                    return readChildFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("enName", mResultsBeanList.get(position).getEn_name());
                    readChildFragment = ReadChildFragment.newInstance(bundle);
                    fragmentSparseArray.put(position, readChildFragment);
                    return readChildFragment;
                }
            }

            @Override
            public int getCount() {
                return mResultsBeanList == null ? 0 : mResultsBeanList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Html.fromHtml(mResultsBeanList.get(position).getName());
            }
        });
    }

    @Override
    public void getReadCategoryMainError(String errMsg) {
        SmartToast.show(errMsg);
    }
}
