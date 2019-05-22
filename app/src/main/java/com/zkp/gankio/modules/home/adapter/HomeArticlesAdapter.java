package com.zkp.gankio.modules.home.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.beans.BaseGankBean;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.utils.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home.adapter
 * @time: 2019/5/20 15:23
 * @description:
 */
public class HomeArticlesAdapter extends BaseQuickAdapter<BaseGankBean, BaseViewHolder> {

    private List<Article> articleList;
    private List<Boolean> isCollectList;

    public HomeArticlesAdapter(int layoutResId, @Nullable List<BaseGankBean> data, List<Article> articleList) {
        super(layoutResId, data);
        this.isCollectList = new ArrayList<>(getData().size());
        this.articleList = articleList;
    }


    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public void addData(@NonNull Collection<? extends BaseGankBean> newData) {
        this.isCollectList = new ArrayList<>();
        for (int i = 0; i < newData.size(); i++) {
            isCollectList.add(false);
        }
        super.addData(newData);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseGankBean item) {

        helper.setText(R.id.tvArticleTitle, item.getDesc());
        helper.setText(R.id.tvArticleNiceDate, item.getPublishedAt().substring(0, 10));
        helper.setText(R.id.tvArticleAuthor, item.getWho());

        if (item.getImages() != null && item.getImages().size() > 0) {
            helper.getView(R.id.ivArticleThumbnail).setVisibility(View.VISIBLE);
            ImageLoader.load(mContext, item.getImages().get(0), helper.getView(R.id.ivArticleThumbnail));
        } else {
            helper.getView(R.id.ivArticleThumbnail).setVisibility(View.GONE);
        }

        if (isCollectList.size() == 0) {
            helper.setImageResource(R.id.ivArticleLike, R.drawable.ic_like_not);
        } else {
            isCollectList.set(helper.getAdapterPosition(), false);
            for (int i = 0; i < articleList.size(); i++) {
                if (articleList.get(i).getArticleId().equals(item.get_id())) {
                    isCollectList.set(helper.getAdapterPosition(), true);
                    break;
                }
            }
            helper.setImageResource(R.id.ivArticleLike, isCollectList.get(helper.getAdapterPosition()) ? R.drawable.ic_like : R.drawable.ic_like_not);
        }
        helper.addOnClickListener(R.id.ivArticleLike);
    }

    public List<Boolean> getIsCollectList() {
        if (isCollectList == null) {
            return new ArrayList<>();
        }
        return isCollectList;
    }
}
