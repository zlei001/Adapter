package com.cttnet.zhwg.ywkt.actn.annunciate.enums;

/**
 * <p>Description: 业务状态枚举</p>
 * @author suguisen
 *
 */
public enum ProvisioningStateEnum {

	/** 业务状态正常 */
	STATEUP( "ietf-te-types:lsp-state-up"),
	/** 业务创建失败 */
	STATESETUPFAILED( "ietf-te-types:lsp-state-setup-failed"),
	/** 业务修改成功 */
	MODIFIED( "ietf-te-types:lsp-path-modified"),
	/** 业务修改失败 */
	MODIFYFAILED( "ietf-te-types:lsp-path-modify-failed")

	;

	private String value;

	ProvisioningStateEnum(String value) {
		this.value=value;
	}

	public String getValue() {
		return this.value;
	}
}
