package com.cttnet.ywkt.actn.data.dto.request.prec;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>以太EOS业务预计算请求DTO</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Data
@ApiModel(description = "以太EOS业务预计算")
public class EosSvcPrecomputedRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
