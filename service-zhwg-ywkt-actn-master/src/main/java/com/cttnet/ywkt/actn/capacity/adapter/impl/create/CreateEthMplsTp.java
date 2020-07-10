package com.cttnet.ywkt.actn.capacity.adapter.impl.create;


import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.capacity.adapter.AbstractCreateAdapter;

import java.util.List;

/**
 * <p>Description: MPLS-TP以太业务</p>
 *
 * @author suguisen
 */
public class CreateEthMplsTp extends AbstractCreateAdapter {

    public CreateEthMplsTp(CreateBasicSvcRequestDTO createBasicSvcRequestDTO) {

        super(createBasicSvcRequestDTO,null);
    }

    @Override
    protected void create(List<TeTunnel> teTunnels) {

    }
}
