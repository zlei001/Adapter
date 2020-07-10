package com.cttnet.zhwg.ywkt.support.converter;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

/**
 * <pre>
 *     基础转换类，提供基本的几个方法，直接继承就可以，如果有需要写Mappings的写在 {@link #to(Object)} 方法上
 *     并且接口类上一定要加上 {@link org.mapstruct.Mapper} 注解
 * </pre>
 * <pre>
 *     @org.mapstruct.Mapper(componentModel = "spring") 此注解可通过spring进行注入。
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020-05-25
 * @since jdk 1.8
 */
public interface BasicObjectConverter<SOURCE, TARGET> {

    /**
     * 重写此注解时一定要注意 返回值（TARGET） 和 参数（SOURCE） 的顺序
     *
     * @param source source
     * @return TARGET
     */
    @Mappings({})
    @InheritConfiguration
    TARGET to(SOURCE source);

    /**
     * 重写此注解时一定要注意 返回值（TARGET） 和 参数（SOURCE） 的顺序
     *
     * @param source source
     * @return TARGET
     */
    @InheritConfiguration
    List<TARGET> to(Collection<SOURCE> source);

    /**
     * 重写此注解时一定要注意 返回值（TARGET） 和 参数（SOURCE） 的顺序
     *
     * @param source source
     * @return TARGET
     */
    @InheritInverseConfiguration
    SOURCE from(TARGET source);

    /**
     * 重写此注解时一定要注意 返回值（TARGET） 和 参数（SOURCE） 的顺序
     *
     * @param source source
     * @return TARGET
     */
    @InheritInverseConfiguration
    List<SOURCE> from(Collection<TARGET> source);
}

