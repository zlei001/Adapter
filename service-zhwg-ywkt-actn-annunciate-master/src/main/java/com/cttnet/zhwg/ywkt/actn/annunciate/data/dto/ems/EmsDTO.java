package com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems;

import com.cttnet.zhwg.ywkt.http.enums.ProtocolEnum;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * <p>Description: actn网管信息</p>
 *
 * @author suguisen
 */
@Data
public class EmsDTO {

    /** 唯一标识 */
    private String emsId;
    private String emsName;
    private String baseUrl;
    private String username;
    private String password;

    private ProtocolEnum protocolEnum;
    private String protocolLayer;
    private Charset charset;
    private String contentType;

}
