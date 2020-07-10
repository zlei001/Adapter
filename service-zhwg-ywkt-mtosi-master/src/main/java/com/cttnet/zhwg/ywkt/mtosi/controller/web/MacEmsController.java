package com.cttnet.zhwg.ywkt.mtosi.controller.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.enums.ResponseDataPage;
import com.cttnet.common.util.RequestUtil;
import com.cttnet.zhwg.ywkt.mtosi.cache.MacEmsData;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MacEmsPO;
import com.cttnet.zhwg.ywkt.mtosi.service.web.MacEmsService;
import com.cttnet.zhwg.ywkt.mtosi.utils.PrimaryKeyUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网管信息表 前端控制器
 * </p>
 *
 * @author zhangyaomin
 * @since 2020-01-10
 */
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/web/macEms"})
public class MacEmsController {

    final private MacEmsService macEmsService;

    @Autowired
    public MacEmsController(MacEmsService macEmsService) {
        this.macEmsService = macEmsService;
    }


    @PostMapping("/page")
    public ResponseDataPage<List<MacEmsPO>> page(HttpServletRequest request) {

        Map<String, String> paramMap = RequestUtil.getParamMap(request);
        int page  = MapUtils.getIntValue(paramMap, "page",1);
        int pageSize  = MapUtils.getIntValue(paramMap, "pageSize", 20);
        IPage<MacEmsPO> iPage = new Page<>(page, pageSize);
        //TODO 查询条件待添加
        QueryWrapper<MacEmsPO> wrapper = new QueryWrapper<>();
        IPage<MacEmsPO> pageData = macEmsService.page(iPage, wrapper);
        ResponseDataPage<List<MacEmsPO>> responseDataPage = new ResponseDataPage<>();
        responseDataPage.setPage(page);
        responseDataPage.setPageSize(pageSize);
        responseDataPage.setTotal(pageData.getTotal());
        responseDataPage.setData(pageData.getRecords());
        return responseDataPage;
    }

    @PostMapping("/insert")
    public ResponseData<?> insert(@RequestBody MacEmsPO macEms) {

        String id = PrimaryKeyUtils.generateWithoutSplit(macEms.getEmsId() + macEms.getMacType());
        macEms.setId(id);
        boolean save = macEmsService.save(macEms);
        if (save) {
            MacEmsData.add(macEms);
        }
        return  ResponseData.ok("");
    }

    @PatchMapping("/update")
    public ResponseData<?> update(@RequestBody MacEmsPO macEms) {

        boolean save = macEmsService.updateById(macEms);
        if (save) {
            MacEmsData.delete(macEms.getEmsId(), macEms.getMacType());
            MacEmsData.add(macEms);
        }
        return  ResponseData.ok("");
    }

    @DeleteMapping("/delete")
    public ResponseData<?> delete(HttpServletRequest request) {
        Map<String, String> paramMap = RequestUtil.getParamMap(request);
        String ids = MapUtils.getString(paramMap, "ids");
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        boolean b = macEmsService.removeByIds(strings);
        return  ResponseData.ok("");
    }

    @PatchMapping("/reload")
    public ResponseData<?> reload(HttpServletRequest request) {

        int reload = MacEmsData.reload();
        return  ResponseData.ok("");
    }

}
