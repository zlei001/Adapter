package com.cttnet.zhwg.ywkt.support.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cttnet.common.enums.ResponseDataPage;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * <pre>分页数据转换器</pre>
 *
 * @author zhangyaomin
 * @date 2020-05-25
 * @since jdk 1.8
 */
public class PageDataConverter {


    /**
     * PO to VO page
     *
     * @param iPage     iPage
     * @param converter converter
     * @return ResponseDataPage
     */
    public static <S, T> ResponseDataPage<List<T>> to(IPage<S> iPage, Converter<List<S>, List<T>> converter) {

        long current = iPage.getCurrent();
        long size = iPage.getSize();
        long total = iPage.getTotal();
        List<S> source = iPage.getRecords();
        List<T> target = converter.convert(source);

        return new ResponseDataPage<>(target, total, (int) current, (int) size);
    }
}
