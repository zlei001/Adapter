package com.cttnet.ywkt.actn.capacity.adapter.impl.delete;

import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.ClientSvcClientImpl;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractDeleteAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class DeleteClientSvc extends AbstractDeleteAdapter {


    public DeleteClientSvc(DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO) {
        super(deleteBasicSvcRequestDTO);
    }

    @Override
    protected void deleteService() {

        String clientSvcName = deleteBasicSvcRequestDTO.getUuid();
        log.info("删除透传业务入参：{}", clientSvcName);
        StatusResponse response = null;
        try {
            response = new ClientSvcClientImpl(this.actnMacEmsDTO).delete(clientSvcName);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("删除透传业务失败:" + response.getDesc(), null);
            } else {
                log.info("删除透传业务成功");
            }
        } catch (Exception e) {
            log.error("删除透传业务失败", e);
        }
    }
}
