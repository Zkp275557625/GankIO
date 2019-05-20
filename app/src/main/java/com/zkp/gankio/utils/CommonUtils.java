package com.zkp.gankio.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.utils
 * @time: 2019/5/20 11:34
 * @description:
 */
public class CommonUtils {

    /**
     * 隐藏键盘
     *
     * @param context 上下文
     * @param view    View
     */
    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
