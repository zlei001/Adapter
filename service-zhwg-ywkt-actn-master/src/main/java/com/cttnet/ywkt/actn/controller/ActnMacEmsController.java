package com.cttnet.ywkt.actn.controller;

import com.cttnet.ywkt.actn.service.ems.ActnMacEmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "透传业务")
@RestController
@RequestMapping("actn/macEms")
public class ActnMacEmsController {

    @Autowired
    private ActnMacEmsService actnMacEmsService;

    @ApiOperation(value = "通过ID获取网管")
    @GetMapping("/getEms/emsId={emsId}")
    ResponseEntity getEmsById(@PathVariable String emsId) {
        return ResponseEntity.ok(actnMacEmsService.getEmsById(emsId));
    }

    @ApiOperation(value = "通过名称获取网管")
    @GetMapping("/getEms/emsName={emsName}")
    ResponseEntity getEmsByName(@PathVariable String emsName) {
        return ResponseEntity.ok(actnMacEmsService.getEmsByName(emsName));
    }

    @ApiOperation(value = "获取默认网管")
    @GetMapping("/getEms/defaultEms")
    ResponseEntity getDefaultEms() {
        return ResponseEntity.ok(actnMacEmsService.getDefaultEms());
    }

    @PostMapping("/reload")
    ResponseEntity reload() {
        return ResponseEntity.ok(actnMacEmsService.reload());
    }

}
