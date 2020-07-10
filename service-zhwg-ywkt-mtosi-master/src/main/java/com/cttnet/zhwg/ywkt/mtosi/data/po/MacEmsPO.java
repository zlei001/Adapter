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
 * 网管信息表
 * </p>
 *
 * @author zhangyaomin
 * @since 2020-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_ywkt_mac_ems")
public class MacEmsPO implements Serializable {

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
     * 接口类型
     */
    @TableField("i_mac_type")
    private Integer macType;

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
     * 前缀
     */
    @TableField("s_prefix")
    private String prefix;

    /**
     * 后缀
     */
    @TableField("s_suffix")
    private String suffix;

    /**
     * 连接超时时长
     */
    @TableField("i_conn_timeout")
    private Integer connTimeout;

    /**
     * 响应超时时长
     */
    @TableField("i_call_timeout")
    private Integer callTimeout;

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
     * 参数
     */
    @TableField("s_params")
    private String params;

    /**
     * 网管版本
     */
    @TableField("s_ems_version")
    private String emsVersion;

    /**
     * 协议
     */
    @TableField("s_protocol")
    private String protocol;
    /**
     * 激活方式: 0单站法  1路径法
     */
    @TableField("i_active_method")
    private String activeMethod;

    /**
     * 更新时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("d_update_time")
    private Date updateTime;

    /**
     * 证书路径
     */
    @TableField("s_keystore_path")
    private String keystorePath;

    /**
     * 证书密码
     */
    @TableField("s_keystore_password")
    private String keystorePassword;


}
