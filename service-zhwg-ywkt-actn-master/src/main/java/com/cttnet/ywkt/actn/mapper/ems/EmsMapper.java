package com.cttnet.ywkt.actn.mapper.ems;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cttnet.ywkt.actn.data.po.ActnMacEmsPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmsMapper extends BaseMapper<ActnMacEmsPO> {
}
