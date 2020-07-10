package com.cttnet.ywkt.actn.controller;

import com.cttnet.common.enums.ResponseData;
import com.cttnet.ywkt.actn.capacity.bo.Status;
import com.cttnet.ywkt.actn.data.dto.request.CreateBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.DeleteBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.ModifyBasicSvcRequestDTO;
import com.cttnet.ywkt.actn.data.dto.request.prec.ClientSvcPrecomputedRequestDTO;
import com.cttnet.ywkt.actn.data.dto.response.ClientSvcPrecomputedResponseDTO;
import com.cttnet.ywkt.actn.data.valid.CreateBusiness;
import com.cttnet.ywkt.actn.data.valid.DeleteBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyBandwidthBusiness;
import com.cttnet.ywkt.actn.data.valid.ModifyFlowSwitchBusiness;
import com.cttnet.ywkt.actn.service.ClientSvcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>透传业务Controller</pre>
 *
 * @author zhangyaomin
 * @date 2020-06-20
 * @since jdk 1.8
 */
@Api(tags = "透传业务")
@RestController
@RequestMapping("client/svc")
public class ClientSvcController {

    final private ClientSvcService clientSvcService;

    public ClientSvcController(ClientSvcService clientSvcService) {
        this.clientSvcService = clientSvcService;
    }

    @ApiOperation(value = "创建业务")
    @PostMapping("/create")
    public ResponseData<String> create(@Validated({CreateBusiness.class}) @RequestBody CreateBasicSvcRequestDTO request) {
        String result = clientSvcService.create(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "修改带宽")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "bandwidth", value = "bandwidth", dataTypeClass = Long.class, paramType = "path")
    })
    @PatchMapping("/update")
    public ResponseData<String> updateBandwidth(@Validated({ModifyBandwidthBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {
        String result = clientSvcService.updateBandwidth(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "调整流量开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path"),
            @ApiImplicitParam(name = "name", value = "name", dataTypeClass = String.class, paramType = "path")
    })
    @PatchMapping("/updateFlowSwitch")
    public ResponseData<String> updateFlowSwitch(@Validated({ModifyFlowSwitchBusiness.class}) @RequestBody ModifyBasicSvcRequestDTO request) {

        String result = clientSvcService.updateFlowSwitch(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "删除业务")
    @ApiImplicitParam(name = "uuid", value = "uuid", dataTypeClass = String.class, paramType = "path")
    @DeleteMapping("/delete")
    public ResponseData<String> delete(@Validated({DeleteBusiness.class}) @RequestBody DeleteBasicSvcRequestDTO request) {

        String result = clientSvcService.delete(request);
        return ResponseData.ok(result);
    }

    @ApiOperation(value = "预计算")
    @PostMapping("/precomputed")
    public ResponseData<ClientSvcPrecomputedResponseDTO> precomputed(@Validated @RequestBody ClientSvcPrecomputedRequestDTO request) {

        ClientSvcPrecomputedResponseDTO precomputed = clientSvcService.precomputed(request);
        return ResponseData.ok(precomputed);
    }


}
