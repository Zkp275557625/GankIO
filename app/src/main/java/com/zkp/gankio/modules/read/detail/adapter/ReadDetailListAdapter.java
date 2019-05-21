package com.zkp.gankio.modules.read.detail.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.beans.ReadDetailBean;
import com.zkp.gankio.utils.ImageLoader;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.detail.adapter
 * @time: 2019/5/21 17:30
 * @description:
 */
public class ReadDetailListAdapter extends BaseQuickAdapter<ReadDetailBean.ResultsBean, BaseViewHolder> {

    public ReadDetailListAdapter(int layoutResId, @Nullable List<ReadDetailBean.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReadDetailBean.ResultsBean item) {
        helper.setText(R.id.tvArticleTitle, item.getTitle());
        helper.setText(R.id.tvArticleNiceDate, item.getPublished_at().substring(0, 10));
        helper.setText(R.id.tvArticleFrom, item.getSite().getName());
        ImageLoader.load(mContext, item.getCover(), helper.getView(R.id.imageView));
    }
}
