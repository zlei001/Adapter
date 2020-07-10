package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  Ems配置信息
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/2/14
 * @since java 1.8
 */
@Data
public class EmsMacBO implements Serializable {

    private static final long serialVersionUID = 8979153389529042532L;
    /**
     * 网管id
     */
    private String emsId;

    /**
     * 网管名称
     */
    private String emsName;

    /**
     * 厂家名称
     */
    private String factoryName;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 连接超时时长
     */
    private Integer connTimeout;

    /**
     * 响应超时时长
     */
    private Integer callTimeout;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 参数
     */
    private String params;

    /**
     * 网管版本
     */
    private String emsVersion;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 证书路径
     */
    private String keystorePath;

    /**
     * 证书密码
     */
    private String keystorePassword;

}
