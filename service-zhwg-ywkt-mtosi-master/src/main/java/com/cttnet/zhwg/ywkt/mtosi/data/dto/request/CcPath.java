package com.cttnet.zhwg.ywkt.mtosi.data.dto.request;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  交叉源宿端ctpID、
 * </pre>
 *
 * @author dengkaihong
 * @date 2020/6/23
 * @since java 1.8
 */
@Data
public class CcPath {
    // 源端
    private List<String> sourceCtpIds;
    // 宿端
    private List<String> destCtpIds;
}
