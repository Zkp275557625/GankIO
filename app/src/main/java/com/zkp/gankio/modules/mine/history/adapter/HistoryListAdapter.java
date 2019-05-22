package com.zkp.gankio.modules.mine.history.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.beans.HistoryGankItem;
import com.zkp.gankio.modules.mine.history.HistoryPresenter;
import com.zkp.gankio.utils.ImageLoader;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.history.adapter
 * @time: 2019/5/22 14:13
 * @description:
 */
public class HistoryListAdapter extends BaseQuickAdapter<HistoryGankItem, BaseViewHolder> {

    private HistoryPresenter mPresenter;

    public HistoryListAdapter(int layoutResId, @Nullable List<HistoryGankItem> data) {
        super(layoutResId, data);
    }

    public void setmPresenter(HistoryPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryGankItem item) {
        helper.setText(R.id.tvDate, item.getTime().replace("-", "/"));
        ImageLoader.load(mContext, item.getIamgeUrl(), helper.getView(R.id.imageView));

        if (mPresenter != null) {
            mPresenter.getHistoryGankContent(item.getTime().replace("-", "/"), helper);
        }
    }
}
