package com.kt.dc.otn.controller;

import com.alibaba.fastjson.JSON;
import com.kt.dc.otn.adapter.impl.DcOtnAdapterServiceImpl;
import com.kt.dc.otn.bean.RequestResult;
import com.kt.dc.otn.util.validationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @Description Description
 * @Author Coder
 * @Date Created in 2020/7/9 0009
 */

@RestController
public class DcOtnAdapterController {
    @Autowired
    DcOtnAdapterServiceImpl dcOtnAdapterService;

    @RequestMapping("/ptn")
    public RequestResult dcOtnAdapter(@RequestBody String param) {
        RequestResult result = new RequestResult();
        if (validationUtil.checkParam(param)){
            result.setResult("请求参数异常！");
            return result;
        }
        Map maps = (Map) JSON.parse(param);
        String flag = maps.get("flag").toString();
        String methodName = maps.get("methodName").toString();
        String params = maps.get("params") == null? null: maps.get("params").toString();
        return dcOtnAdapterService.oTnAdapter(flag, methodName, params);
    }

}
