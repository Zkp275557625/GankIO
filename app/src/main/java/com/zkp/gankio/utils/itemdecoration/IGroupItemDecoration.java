package com.zkp.gankio.utils.itemdecoration;

import android.content.Context;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.utils.itemdecoration
 * @time: 2019/5/20 16:55
 * @description:
 */
public interface IGroupItemDecoration {

    Context getContext();

    /**
     * 判断当前点击位置是否处于GroupItem区域
     *
     * @param x
     * @param y
     * @return
     */
    GroupItem findGroupItemUnder(int x, int y);

}
