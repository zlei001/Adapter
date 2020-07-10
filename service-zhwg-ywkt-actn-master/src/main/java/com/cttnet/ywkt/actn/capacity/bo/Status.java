package com.cttnet.ywkt.actn.capacity.bo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Description: 响应状态结构体 </p>
 * @author suguisen
 *
 */
@Builder
@Data
public class Status {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;

    private int status;
    private String desc;

    public void success() {
        success("");
    }

    public void success(String msg) {
        this.status = SUCCESS;
        this.desc = msg;
    }

    public void fail(String err) {
        this.status = FAILURE;
        this.desc = err;
    }

    public boolean isOk() {
        return this.status == SUCCESS;
    }

}
