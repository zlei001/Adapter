package com.cttnet.ywkt.actn.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * <pre>
 *  UUID
 * </pre>
 * @author zhangyaomin
 * @since JDK 1.8
 * @version 1.0
 */
public class PrimaryKeyUtil {

	/**
	 * @return
	 */
	public synchronized static String getKey() {
		return UUID.randomUUID().toString();
	}


	/**
	 * @param name
	 * @return
	 */
	public synchronized static String getKey(String name) {
		if (StringUtils.isEmpty(name)) {
			return getKey();
		}
		return UUID.nameUUIDFromBytes(name.getBytes()).toString();
	}

	public static void main(String[] args) {
		System.out.println(getKey());
		System.out.println(getKey());
		System.out.println(getKey("fdf"));
		System.out.println(getKey("fdf"));
	}
}
