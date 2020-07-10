package com.cttnet.ywkt.actn.capacity.adapter;

import com.alibaba.fastjson.JSON;
import com.cttnet.common.util.SpringContextUtils;
import com.cttnet.ywkt.actn.data.dto.actnNode.ActnNodeDTO;
import com.cttnet.ywkt.actn.data.dto.actnNode.ActnNodeListDTO;
import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.ems.ActnMacEmsDTO;
import com.cttnet.ywkt.actn.data.dto.request.PointDTO;
import com.cttnet.ywkt.actn.service.ems.ActnMacEmsService;
import com.cttnet.ywkt.actn.util.AccessServiceInterfaceUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * <pre>
 *  actn业务操作基础对象类
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public abstract class AbstractActnBaseAdapter {

    /**
     * 流程成功标识，true才走下一个流程
     */
    @Getter
    protected boolean ok;
    /**
     * 一般逻辑异常记录,向上层展示
     */
    @Getter
    protected Set<String> errSet = new HashSet<>();

    /**
     * 服务器内部异常记录，日志记录
     */
    @Getter
    protected Set<String> innerErrSet = new HashSet<>();

    /**
     * NCE-ACTN接口基本信息
     */
    protected ActnMacEmsDTO actnMacEmsDTO;

    protected String emsId;

    /**
     * 响应响应出参
     */
    @Getter
    protected Status responseOfAtomic;


    public AbstractActnBaseAdapter(String emsId) {
        this.emsId = emsId;
        this.ok = true;
    }

    /**
     * 执行
     * @return
     */
    public void execute() {
        _before();
        if (this.ok) {
            _exec();
        }
        _after();
        log.info("调用ACTN能力响应:{}", JSON.toJSONString(this.responseOfAtomic));
    }

    protected void _before() {
        if (ok) {
            initEms();
        }
        initResponse();
    }

    protected abstract void _exec();

    protected void _after() {
        if (this.ok) {
            this.responseOfAtomic.success();
        } else {
            this.responseOfAtomic.fail(JSON.toJSONString(this.errSet));
        }
    }

    /**
     * point对象转换ACTN对象,
     */
    protected void transPoint(List<PointDTO> points) {

        if (points == null || points.isEmpty()) {
            return;
        }
        List<String> neIds = new ArrayList<>();
        List<String> portIds = new ArrayList<>();
        for (PointDTO point : points) {
            neIds.add(point.getNeId());
            //若该节点有带vlan号，则该端口指的是cpe端口
            if (StringUtils.isNotBlank(point.getPortId()) && !"0".equals(point.getPortId()) && point.getVlan() == null) {
                portIds.add(point.getPortId());
            }
        }
        //调接口通过网元id查询ACTN节点
        List<ActnNodeDTO> actnNeList = getACTNIDsByMTOSIIDs(neIds, "Ne");
        if (this.ok && actnNeList != null && !actnNeList.isEmpty()) {
            int count = 0, countPort = 0;
            Map<String, ActnNodeDTO> neMap = transMap(actnNeList);

            //端口
            List<ActnNodeDTO> actnPortList = getACTNIDsByMTOSIIDs(portIds, "Port");
            Map<String, ActnNodeDTO> portMap = transMap(actnPortList);

            for (PointDTO point : points) {
                if (neMap.get(point.getNeId()) != null) {
                    String actnId = neMap.get(point.getNeId()).getActnId();
                    if (StringUtils.isNotBlank(actnId)) {
                        point.setNeId(actnId);
                        count++;
                    }
                }
                //端口
                if (StringUtils.isNotBlank(point.getPortId()) && !"0".equals(point.getPortId())) {
                    //若该节点有带vlan号，则该端口指的是cpe端口，不需要转换
                    if(point.getVlan() != null) continue;
                    String actnId = portMap.get(point.getPortId()).getActnId();
                    if (StringUtils.isNotBlank(actnId)) {
                        point.setPortId(actnId);
                        countPort++;
                    }else{
                        point.setPortId("0");
                    }
                }else {
                    point.setPortId("0");
                }
            }
            log.info("I2转ACTN对象{}条，转换成功{}条", points.size(), count);
            log.info("I2转ACTN对象{}条，转换端口成功{}条", points.size(), countPort);
            if (count != points.size()) {
                addErrInfo("I2对象" + JSON.toJSONString(neIds) +"转ACTN对象转换记录不全", null);
            }
        }else{
            addErrInfo("网元id转ACTN节点失败",null);
        }
    }

    /**
     * list to Map
     * @param actnNodeDTOS
     * @return
     */
    private Map<String, ActnNodeDTO> transMap(List<ActnNodeDTO> actnNodeDTOS) {

        Map<String, ActnNodeDTO> map = new HashMap<>();
        for (ActnNodeDTO actnNodeDTO : actnNodeDTOS) {

            map.put(actnNodeDTO.getObjId(), actnNodeDTO);
        }
        return map;
    }



    /**
     * 查询mtosi对象
     * @param mtosiIds
     * @param type Ne、Port
     * @return
     */
    protected List<ActnNodeDTO> getACTNIDsByMTOSIIDs(List<String> mtosiIds, String type) {

        try {
            List<ActnNodeDTO> actnNodesDto = new ArrayList<>();
            for (String mtosiId : mtosiIds) {
                ActnNodeDTO actnNodeDTO = new ActnNodeDTO();
                actnNodeDTO.setType(type);
                actnNodeDTO.setEmsId(this.actnMacEmsDTO.getEmsId());
                actnNodeDTO.setObjId(mtosiId);
                actnNodesDto.add(actnNodeDTO);
            }
            log.info("调用ID转换请求入参:{}", JSON.toJSONString(actnNodesDto));
            ActnNodeListDTO actnIdsByMtosiIds = AccessServiceInterfaceUtil.getActnIdsByMtosiIds(actnNodesDto);
            List<ActnNodeDTO> actnNodeDtoList = actnIdsByMtosiIds != null ? actnIdsByMtosiIds.getActnNodeVoList() : null;
            log.info("调用ID转换请响应出参:{}", actnNodeDtoList);
            return actnNodeDtoList;
        } catch (Exception e) {
            addErrInfo("通过网元Id" + JSON.toJSONString(mtosiIds)+ "查询ACTN对象失败", e);
        }
        return null;
    }

    /**
     * 初始化EMS信息
     * @return
     */
    private void initEms() {
        ActnMacEmsService emsService = SpringContextUtils.getBean(ActnMacEmsService.class);
        this.actnMacEmsDTO = emsService.getEmsById(emsId);
    }

    private void initResponse() {
        if(this.actnMacEmsDTO ==null){
            this.responseOfAtomic.fail("网管初始化异常");
            this.ok = false;
            return;
        }
        this.responseOfAtomic = Status.builder().status(Status.SUCCESS).build();
    }

    /**
     * 添加异常
     * @param errMsg
     * @param e
     */
    protected void addErrInfo(String errMsg, Exception e) {
        this.ok = false;
        this.errSet.add(errMsg);
        if (e != null) {
            log.error(errMsg, e);
            this.innerErrSet.add(e.getMessage());
        } else {
            log.error(errMsg);
        }
    }


}
