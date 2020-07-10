package com.cttnet.ywkt.actn.data.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: 业务开通网管信息对象 </p>
 * @author suguisen
 *
 */
@Data
@Accessors(chain = true)
@TableName("t_ywkt_actn_mac_ems")
public class ActnMacEmsPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 网管信息id
     */
    @TableId("s_id")
    private String id;

    /**
     * 网管id
     */
    @TableField("s_ems_id")
    private String emsId;

    /**
     * 网管名称
     */
    @TableField("s_ems_name")
    private String emsName;

    /**
     * 厂家名称
     */
    @TableField("s_factory_name")
    private String factoryName;

    /**
     * ip
     */
    @TableField("s_ip")
    private String ip;

    /**
     * 端口
     */
    @TableField("i_port")
    private Integer port;

    /**
     * 用户名
     */
    @TableField("s_username")
    private String username;

    /**
     * 密码
     */
    @TableField("s_password")
    private String password;

    /**
     * 协议 1：http 2:https
     */
    @TableField("i_protocol")
    private Integer protocol;

    /**
     * 创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("d_create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("d_update_time")
    private Date updateTime;

}
