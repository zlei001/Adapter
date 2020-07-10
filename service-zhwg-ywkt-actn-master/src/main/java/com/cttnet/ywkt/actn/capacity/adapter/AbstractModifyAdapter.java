package com.cttnet.ywkt.actn.capacity.adapter;

import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;

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
public abstract class AbstractModifyAdapter extends AbstractActnBaseAdapter {

    protected ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO;

    public AbstractModifyAdapter(ModifyBasicSvcRequestDTO modifyBasicSvcRequestDTO) {
        super(modifyBasicSvcRequestDTO.getEmsId());
        this.modifyBasicSvcRequestDTO = modifyBasicSvcRequestDTO;
        //入参校验
        if (this.ok) {
            if (modifyBasicSvcRequestDTO.getBandwidth() == null && modifyBasicSvcRequestDTO.getFlowSwitch()==null ) {
                this.ok = false;
                this.errSet.add("带宽、修改流量开关都为空");
            }
        }
    }

    /**
     * 修改业务
     * @return
     */
    protected abstract void modify();

    @Override
    protected void  _exec() {
        modify();
    }
}
