package com.cttnet.ywkt.actn.support.converter;

import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.data.po.ActnMacEmsPO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * <pre>网管对象转换器</pre>
 *
 * @author suguisen
 */
@Mapper(componentModel = "spring")
public interface EmsConverter {

    /**
     * PO to VO
     * @param source PO
     * @return VO
     */
    @Named("detail")
    ActnMacEmsDTO poToDTO(ActnMacEmsPO source);

}
