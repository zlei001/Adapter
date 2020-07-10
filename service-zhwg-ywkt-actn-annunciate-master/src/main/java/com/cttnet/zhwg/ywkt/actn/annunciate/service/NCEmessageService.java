package com.cttnet.zhwg.ywkt.actn.annunciate.service;

import com.cttnet.ywkt.actn.bean.client.sub.ClientSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.client.sub.Provisioning;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.tunnel.sub.LspProvisioningErrorInfo;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.actn.annunciate.enums.BusinessTypeEnum;
import com.cttnet.zhwg.ywkt.actn.annunciate.enums.ProvisioningStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 *
 * @author suguisen
 */
@Slf4j
@Service
public class NCEmessageService {

    /**
     * <p>Description: 业务</p>
     * @author suguisen
     *
     * @param name 业务id
     * @param provisioningState 业务状态
     * @param emsDTO 网管id
     * @param title 业务名称
     * @param tunnelName 隧道id
     * @param businessType 业务类型
     */
    public void dealClientOrEthtSvcPs(String name, String provisioningState, EmsDTO emsDTO, String title, String tunnelName, BusinessTypeEnum businessType) {
        /**
         * 业务配置状态预览，用于向MDSC通告当前业务配置过程所处的状态，
         * ietf-te-types:lsp-state-up（业务状态正常）
         * ietf-te-types:lsp-path-modified（修改成功）
         * ietf-te-types:lsp-path-modify-failed（修改失败）
         */
        if(ProvisioningStateEnum.STATEUP.getValue().equals(provisioningState)){
//            actnCircuitDesignService.dealClientOrEthtSuccess(name,emsDTO,"dealClientOrEthtSvcPs",title,tunnelName);
            // 业务创建成功
            createBusinessSuccess(businessType,name,tunnelName,title, emsDTO);

        }else if (ProvisioningStateEnum.MODIFIED.getValue().equals(provisioningState)){
//            actnCircuitDesignService.dealClientOrEthtModified(name,emsDTO);
            // 带宽调整成功
            updateBandwidthSuccess(businessType,name, emsDTO);


        }else if(ProvisioningStateEnum.MODIFYFAILED.getValue().equals(provisioningState)){
//            actnCircuitDesignService.dealClientOrEthtModifyFailed(name,emsDTO);
            // 带宽调整成功
            updateBandwidthFail(businessType,name, emsDTO);

        }else {

        }
    }

    /**
     * <p>Description: 透传业务创建失败</p>
     * @author suguisen
     *
     */
    public void dealClientSvcError(String name, Provisioning provisioning, EmsDTO emsDTO) {
        if(provisioning!=null){
            ClientSvcErrorInfo clientSvcErrorInfo = new ClientSvcErrorInfo();
            clientSvcErrorInfo.setClientSvcErrorInfo(provisioning.getErrorInfo());
            createBusinessFail(BusinessTypeEnum.CLIENT,name, emsDTO,provisioning.getErrorInfo());
        }
    }

    /**
     * <p>Description: 以太业务创建失败</p>
     *
     */
    public void dealEthtSvcError(String name, Provisioning provisioning, EmsDTO emsDTO) {
        if(provisioning!=null) {
            EthtSvcErrorInfo ethtSvcErrorInfo = new EthtSvcErrorInfo();
            ethtSvcErrorInfo.setEthtSvcErrorInfo(provisioning.getErrorInfo());
            createBusinessFail(BusinessTypeEnum.ETH,name, emsDTO,provisioning.getErrorInfo());
        }
    }

    /**
     * <p>Description: 删除透传业务</p>
     * @author suguisen
     *
     * @date 2019/6/14
     */
    public void dealClientSvcDelete(String name, EmsDTO emsDTO, BusinessTypeEnum businessType) {
        deleteBusinessSuccess(businessType,name, emsDTO);
    }

    /**
     * <p>Description: 删除以太业务</p>
     * @author suguisen
     *
     * @date 2019/6/14
     */
    public void dealEthtSvcDelete(String name, EmsDTO emsDTO, BusinessTypeEnum businessType) {
        deleteBusinessSuccess(businessType,name, emsDTO);
    }

    /**
     * <p>Description: CPE上线</p>
     * @author suguisen
     *
     */
    public void cpeOnline(String name, EmsDTO emsDTO, BusinessTypeEnum businessType) {
        cpeOnline(businessType,name, emsDTO);
    }

    /**
     * <p>Description: 业务创建成功 </p>
     *
     * @param businessType  业务类型
     * @param businessId    业务id
     * @param tunnelId      隧道id
     * @param businessTitle 业务名称
     * @param ems           网管对象
     * @author suguisen
     */
    private void createBusinessSuccess(BusinessTypeEnum businessType, String businessId, String tunnelId, String businessTitle, EmsDTO ems) {
        log.info("业务创建成功");
    }

    /**
     * <p>Description: 带宽调整成功 </p>
     *
     * @param businessType  业务类型
     * @param tunnelId      隧道id
     * @param ems           网管对象
     * @author suguisen
     */
    private void updateBandwidthSuccess(BusinessTypeEnum businessType, String tunnelId, EmsDTO ems) {
        log.info("带宽调整成功");
    }

    /**
     * <p>Description: 带宽调整失败 </p>
     *
     * @param businessType  业务类型
     * @param tunnelId      隧道id
     * @param ems           网管对象
     * @author suguisen
     */
    private void updateBandwidthFail(BusinessTypeEnum businessType, String tunnelId, EmsDTO ems) {
        log.info("带宽调整失败");
    }

    /**
     * <p>Description: 删除业务成功</p>
     * @author suguisen
     *
     * @date 2019/6/14
     */
    private void deleteBusinessSuccess(BusinessTypeEnum businessType, String name, EmsDTO emsDTO) {
        log.info("删除业务成功");
    }

    /**
     * <p>Description: 业务创建失败</p>
     * @param businessType  业务类型
     * @param businessId    业务id
     * @param ems           网管对象
     * @param errorInfo     失败信息对象
     * @author suguisen
     */
    private void createBusinessFail(BusinessTypeEnum businessType, String businessId, EmsDTO ems, LspProvisioningErrorInfo errorInfo) {
        log.info("业务创建失败");
    }

    /**
     * <p>Description: CPE上线成功</p>
     * @author suguisen
     *
     * @date 2019/6/14
     */
    private void cpeOnline(BusinessTypeEnum businessType, String name, EmsDTO emsDTO) {
        log.info("CPE上线成功");
    }
}
