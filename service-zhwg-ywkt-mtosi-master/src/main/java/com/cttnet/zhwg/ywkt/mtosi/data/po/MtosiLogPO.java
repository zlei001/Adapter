package com.cttnet.zhwg.ywkt.mtosi.data.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * i2日志表
 * </p>
 *
 * @author zhangyaomin
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_ywkt_mtosi_log")
public class MtosiLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志表id
     */
    @TableId("s_id")
    private String id;

    /**
     * url
     */
    @TableField("s_ws_url")
    private String wsUrl;

    /**
     * 方法
     */
    @TableField("s_ws_method")
    private String wsMethod;

    /**
     * 时延
     */
    @TableField("i_delay")
    private Long delay;

    /**
     * 结果
     */
    @TableField("s_result")
    private String result;

    /**
     * 备注
     */
    @TableField("s_remark")
    private String remark;

    /**
     * 请求报文
     */
    @TableField("s_request_str")
    private String requestStr;

    /**
     * 响应报文
     */
    @TableField("s_response_str")
    private String responseStr;

    /**
     * 请求时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss.S",timezone="GMT+8")
    @TableField("d_request_time")
    private Date requestTime;

    /**
     * 响应时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss.S",timezone="GMT+8")
    @TableField("d_response_time")
    private Date responseTime;

    /**
     * 插入时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss.S",timezone="GMT+8")
    @TableField("d_insert_time")
    private Date insertTime;

    @TableField("s_system_name")
    private String systemName;


}
