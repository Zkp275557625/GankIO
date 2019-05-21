package com.zkp.gankio.modules.category.list.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.beans.CategoryBean;
import com.zkp.gankio.utils.ImageLoader;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category.list.adapter
 * @time: 2019/5/21 15:06
 * @description:
 */
public class CategoryWelfareAdapter extends BaseQuickAdapter<CategoryBean.ResultsBean, BaseViewHolder> {

    public CategoryWelfareAdapter(int layoutResId, @Nullable List<CategoryBean.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean.ResultsBean item) {
        ImageLoader.load(mContext, item.getUrl(), helper.getView(R.id.imageView));
    }
}
