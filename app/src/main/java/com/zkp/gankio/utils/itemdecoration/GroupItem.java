package com.zkp.gankio.utils.itemdecoration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.utils.itemdecoration
 * @time: 2019/5/20 16:55
 * @description:
 */
public class GroupItem {

    /**
     * 起始position
     */
    private int startPosition;
    private Map<String, Object> dataMap;

    public GroupItem(int startPosition) {
        this.startPosition = startPosition;
        dataMap = new HashMap<>();
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setData(String key, Object data) {
        dataMap.put(key, data);
    }

    public Object getData(String key) {
        return dataMap.get(key);
    }

}
