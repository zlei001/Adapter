package com.cttnet.ywkt.actn.capacity.adapter;

import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;

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
public abstract class AbstractDeleteAdapter extends AbstractActnBaseAdapter {

    protected DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO;

    public AbstractDeleteAdapter(DeleteBasicSvcRequestDTO deleteBasicSvcRequestDTO) {
        super(deleteBasicSvcRequestDTO.getEmsId());
        this.deleteBasicSvcRequestDTO = deleteBasicSvcRequestDTO;
    }

    /**
     * 删除业务
     * @return
     */
    protected abstract void deleteService();

    @Override
    protected void _exec() {

        deleteService();
    }


}
