package com.cttnet.ywkt.actn.capacity.adapter.impl.delete;

import com.cttnet.ywkt.actn.capacity.bo.StatusResponse;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
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
public class DeleteEthSvc extends AbstractDeleteAdapter {


    public DeleteEthSvc(DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO) {
        super(deleteBasicSvcRequestDTO);
    }

    @Override
    protected void deleteService() {

        String ethSvcName = deleteBasicSvcRequestDTO.getUuid();
        log.info("删除以太业务入参：{}", ethSvcName);
        StatusResponse response;
        try {
            response = new EthSvcClientImpl(this.actnMacEmsDTO).delete(ethSvcName);
            this.ok = response.isOk();
            if (!response.isOk()) {
                addErrInfo("删除以太业务失败:" + response.getDesc(), null);
            } else {
                log.info("删除以太业务成功");
            }
        } catch (Exception e) {
            log.error("删除以太业务失败", e);
        }
    }
}
