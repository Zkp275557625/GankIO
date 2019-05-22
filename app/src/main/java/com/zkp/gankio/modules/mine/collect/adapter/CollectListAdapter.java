package com.zkp.gankio.modules.mine.collect.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.R;
import com.zkp.gankio.db.entity.Article;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.collect.adapter
 * @time: 2019/5/22 10:24
 * @description:
 */
public class CollectListAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public CollectListAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tvArticleTitle, item.getArticleTitle());
        helper.setText(R.id.tvArticleAuthor, item.getArticleAuthor());
        helper.setImageResource(R.id.ivArticleLike, R.drawable.ic_like);
        helper.addOnClickListener(R.id.ivArticleLike);
    }
}
