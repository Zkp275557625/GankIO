package com.zkp.gankio.modules.home.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.beans.BaseGankBean;
import com.zkp.gankio.utils.ImageLoader;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home.adapter
 * @time: 2019/5/20 15:23
 * @description:
 */
public class HomeArticlesAdapter extends BaseQuickAdapter<BaseGankBean, BaseViewHolder> {

    private String lastCategory;

    public HomeArticlesAdapter(int layoutResId, @Nullable List<BaseGankBean> data) {
        super(layoutResId, data);
        lastCategory = "";
    }


    @Override
    protected void convert(BaseViewHolder helper, BaseGankBean item) {

//        if (lastCategory.equals(item.getType())) {
//            helper.getView(R.id.tvCategory).setVisibility(View.GONE);
//        } else {
//            helper.getView(R.id.tvCategory).setVisibility(View.VISIBLE);
//            helper.setText(R.id.tvCategory, item.getType());
//            lastCategory = item.getType();
//        }
        helper.setText(R.id.tvArticleTitle, item.getDesc());
        helper.setText(R.id.tvArticleNiceDate, item.getPublishedAt().substring(0, 9));
        helper.setText(R.id.tvArticleAuthor, item.getWho());

        if (item.getImages() != null && item.getImages().size() > 0) {
            helper.getView(R.id.ivArticleThumbnail).setVisibility(View.VISIBLE);
            ImageLoader.load(mContext, item.getImages().get(0), helper.getView(R.id.ivArticleThumbnail));
        } else {
            helper.getView(R.id.ivArticleThumbnail).setVisibility(View.GONE);
        }
    }
}
