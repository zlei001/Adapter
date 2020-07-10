package com.cttnet.ywkt.actn.data.dto.actnNode;

import lombok.Data;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author suguisen
 */
@Data
public class ActnNodeListDTO {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    private int status;
    private String desc;

    private List<ActnNodeDTO> actnNodeVoList;

}
