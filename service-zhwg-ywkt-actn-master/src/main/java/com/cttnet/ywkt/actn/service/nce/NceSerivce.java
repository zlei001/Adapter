package com.cttnet.ywkt.actn.service.nce;

import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.ywkt.actn.capacity.client.impl.ClientSvcClientImpl;
import com.cttnet.ywkt.actn.capacity.client.impl.EthSvcClientImpl;
import com.cttnet.ywkt.actn.capacity.client.impl.LinkClientImpl;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.domain.client.response.ResponseOfQueryClientSvc;
import com.cttnet.ywkt.actn.domain.eth.response.ResponseOfQueryEthSvc;
import com.cttnet.ywkt.actn.domain.network.response.ResponeOfQueryLink;
import com.cttnet.ywkt.actn.service.ems.ActnMacEmsService;
import com.cttnet.ywkt.actn.support.converter.EmsConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 *
 * @author suguisen
 */
@Service
@Slf4j
public class NceSerivce {
    @Autowired
    private EmsConverter emsConverter;

    /**
     * <p>Description: 查询透传业务</p>
     * @author suguisen
     *
     * @param emsId 网管id
     * @param actnId 业务id
     * @return 透传业务对象（CLIENT）
     */
    public ResponseOfQueryClientSvc getBusinessClientSvc(String emsId, String actnId) {
        ResponseOfQueryClientSvc responseOfQueryClientSvc = null;
        try {
            responseOfQueryClientSvc = new ClientSvcClientImpl(getEmsBean(emsId)).one(actnId);
        } catch (Exception e) {
            log.error("透传业务查询失败", e);
        }
        return responseOfQueryClientSvc;
    }

    /**
     * <p>Description: 查询以太业务</p>
     * @author suguisen
     *
     * @param emsId 网管id
     * @param actnId 业务id
     * @return 以太业务对象（EOO/EOS）
     */
    public ResponseOfQueryEthSvc getBusinessEthSvc(String emsId, String actnId) {
        ResponseOfQueryEthSvc responseOfQueryEthSvc = null;
        try {
            responseOfQueryEthSvc = new EthSvcClientImpl(getEmsBean(emsId)).one(actnId);
        } catch (Exception e) {
            log.error("以太业务查询失败", e);
        }
        return responseOfQueryEthSvc;
    }

    private ActnMacEmsDTO getEmsBean(String emsId) {
        ActnMacEmsService emsService = SpringContextUtils.getBean(ActnMacEmsService.class);
        ActnMacEmsDTO actnMacEmsDTO = emsService.getEmsById(emsId);
        return actnMacEmsDTO;
    }

    /**
     * 查询指定拓扑指定链路
     * @param emsId 网管id
     * @param network
     * @param linkid
     * @return
     * @throws Exception
     */
    public ResponeOfQueryLink queryLink(String emsId, String network, String linkid) {
        ResponeOfQueryLink responeOfQueryLink = null;
        try {
            responeOfQueryLink = new LinkClientImpl(getEmsBean(emsId)).one(network, linkid);
        } catch (Exception e) {
            log.error("查询指定拓扑指定链路异常：",e);
        }
        return responeOfQueryLink;

    }


}
