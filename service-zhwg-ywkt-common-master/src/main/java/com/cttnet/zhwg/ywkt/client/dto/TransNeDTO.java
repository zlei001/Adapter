package com.cttnet.zhwg.ywkt.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>传输网元</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-17
 * @since jdk 1.8
 */
@Data
public class TransNeDTO implements Serializable {

    private static final long serialVersionUID = -7313145603052944962L;

    /**
     * 网元ID
     */
    private String neId;

    /**
     * 网元名称
     */
    private String fullName;

    /**
     * fdn
     */
    private String vendorObjName;

    /**
     * 网元型号
     */
    private String neModel;

    /**
     * 网元制式
     */
    private String neCategory;

    /**
     * 数据域
     */
    private String dataDomainId;

    /**
     * 厂家
     */
    private String manufacturer;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

}
