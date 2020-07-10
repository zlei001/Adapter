package com.cttnet.ywkt.actn.data.dto.actnNode;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: ACTN 和 I2 转换交互对象</p>
 * @author suguisen
 *
 */
@Data
public class ActnNodeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 网管id*/
	private String emsId;

	private String fullName;

	/** 网元id*/
	private String objId;

	/** 厂家原始串 例：~MD=Huawei/NCE~ME=167772166 */
	private String objName;

	private String type;

	/** actnId 或 UUID */
	private String actnId;

	private String actnNeId;

	/** 上报采集成功的I2对象才使用*/
	private String channelId;

}
