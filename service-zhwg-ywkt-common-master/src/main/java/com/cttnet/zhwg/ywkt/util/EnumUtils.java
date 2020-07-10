package com.cttnet.zhwg.ywkt.util;

import org.apache.commons.beanutils.MethodUtils;

/**
 * <pre>
 * 枚举对象工具类
 * </pre>
 * @author zhangyaomin
 * @since java 1.8
 */
public class EnumUtils {

	/**
	 * 校验参数value是否为enumClass枚举的其中一个枚举值，该枚举必须提供一个getValue的方法
	 *
	 * @param enumClass
	 * @param value
	 * @return
	 */
	public static <E extends Enum<E>> boolean isValidEnum( Class<E> enumClass, int value ) {
		return getEnum( enumClass, value ) != null;
	}

	/**
	 * 校验参数value是否为enumClass枚举的其中一个枚举值，该枚举必须提供一个getValue的方法
	 *
	 * @param enumClass
	 * @param value
	 * @return
	 */
	public static <E extends Enum<E>> boolean isValidEnum( Class<E> enumClass, Object value, String method ) {
		return getEnum( enumClass, value, method ) != null;
	}

	/**
	 * 获取enumClass枚举里，枚举值等于参数value的枚举，该枚举必须提供一个getValue的方法
	 * getValue方法不存在或枚举值不存在，均返回null
	 * @param enumClass
	 * @param value
	 * @return
	 */
	public static <E extends Enum<E>> E getEnum( Class<E> enumClass, int value ) {
		return getEnum( enumClass, value, "getValue" );
	}

	/**
	 * 获取enumClass枚举里，枚举值equals参数value的枚举，该枚举必须提供一个参数method的方法，
	 * 参数method方法不存在或枚举值不存在，均返回null
	 *
	 * @param enumClass
	 * @param value
	 * @param method
	 * @return
	 */
	public static <E extends Enum<E>> E getEnum( Class<E> enumClass, Object value, String method ) {
		E[] enumList = enumClass.getEnumConstants();
		for ( E e : enumList ) {
			try {
				if ( MethodUtils.invokeMethod( e, method, new Object[]{} ).toString()
						.equals( value == null ? null : value.toString() ) ) {
					return e;
				}
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * getEnumRange
	 * @param enumClass
	 * @return
	 */
	public static <E extends Enum<E>>  String getEnumRange( Class<E> enumClass ) {
		return getEnumRange(enumClass, "getValue");
	}

	/**
	 * getEnumRange
	 * @param enumClass
	 * @param method
	 * @return
	 */
	public static <E extends Enum<E>>  String getEnumRange( Class<E> enumClass, String method ) {
		String range = "";
		E[] enumList = enumClass.getEnumConstants();
		for ( E e : enumList ) {
			try {
				range += MethodUtils.invokeMethod( e, method, new Object[]{} ).toString() + ",";
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
		if(range.length() > 0) {
			return range.substring(0, range.length() - 1);
		}
		return "";
	}


}
