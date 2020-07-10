package com.cttnet.zhwg.api.impl;

import com.cttnet.common.util.StringUtil;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.api.AbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * <pre>
 *  板卡服务
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/7/3
 * @since java 1.8
 */
public class TransBoardApi extends AbstractService {


    /**
     * 根据板卡ID查询板卡
     * @param boardId boardId
     * @return board
     */
    public Map<String, Object> loadBoardById(String boardId) {

        List<String> boardIds = new ArrayList<>();
        boardIds.add(boardId);
        List<Map<String, Object>> list = loadBoardListByIds(boardIds);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : MapUtils.EMPTY_MAP;

    }

    /**
     * 根据板卡IDs查询板卡集合
     * @param boardIds boardIds
     * @return boardList
     */
    public List<Map<String, Object>> loadBoardListByIds(List<String> boardIds) {

        String url = SysConfig.getProperty("ywkt.api.trans-conf.url.board", "/wgjk/cswg/conf/1.0/trans-board-api/query/ids");
        Map<String, Object> params = new HashMap<>(1);
        params.put("boardIds", StringUtil.appendBySplit(new HashSet<>(boardIds), ","));
        return list(url, params);

    }

    /**
     * 获取应用名称
     *
     * @return
     */
    @Override
    protected String getApplication() {
        return SysConfig.getProperty("ywkt.api.trans-conf.application", "service-zhwg-jk-trans-conf");
    }
}
