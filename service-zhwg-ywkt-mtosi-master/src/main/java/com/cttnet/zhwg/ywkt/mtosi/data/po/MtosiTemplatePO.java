package com.cttnet.zhwg.ywkt.mtosi.data.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 报文模板
 * </p>
 *
 * @author zhangyaomin
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_ywkt_mtosi_template")
public class MtosiTemplatePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报文模板id
     */
    @TableId("s_id")
    private String id;

    /**
     * 厂家
     */
    @TableField("s_factory_name")
    private String factoryName;

    /**
     * 方法
     */
    @TableField("s_ws_method")
    private String wsMethod;

    /**
     * 版本
     */
    @TableField("s_version")
    private String version;

    /**
     * 请求模板
     */
    @TableField("s_request_str")
    private String requestStr;

    /**
     * 响应模板
     */
    @TableField("s_response_str")
    private String responseStr;


}
