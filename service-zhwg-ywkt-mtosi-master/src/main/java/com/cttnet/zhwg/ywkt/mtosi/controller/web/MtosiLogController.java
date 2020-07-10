package com.cttnet.zhwg.ywkt.mtosi.controller.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cttnet.common.controller.BaseController;
import com.cttnet.common.enums.ResponseData;
import com.cttnet.common.util.RequestUtil;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MtosiLogPO;
import com.cttnet.zhwg.ywkt.mtosi.service.web.MtosiLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * i2日志表 前端控制器
 * </p>
 *
 * @author zhangyaomin
 * @since 2020-01-10
 */
@RestController
@RequestMapping(value = {"${server.prefix:${spring.application.name:}}/web/mtosiLog"})
public class MtosiLogController extends BaseController {


    private final MtosiLogService mtosiLogService;

    @Autowired
    public MtosiLogController(MtosiLogService mtosiLogService) {
        this.mtosiLogService = mtosiLogService;
    }

    @ApiOperation(value = "page", notes = "分页查询日志记录", httpMethod = "POST")
    @PostMapping("/page")
    public ResponseData<IPage<MtosiLogPO>> page(HttpServletRequest request) {

        Map<String, String> paramMap = RequestUtil.getParamMap(request);
        int pageNo = MapUtils.getIntValue(paramMap, "pageNo", 1);
        int pageSize = MapUtils.getIntValue(paramMap, "pageSize", 1);

        IPage<MtosiLogPO> page = new Page<>(pageNo, pageSize);
        QueryWrapper<MtosiLogPO> queryWrapper = new QueryWrapper();

        if (MapUtils.getString(paramMap, "method") != null) {
            queryWrapper.like("s_ws_method", MapUtils.getString(paramMap, "method"));
        }
        if (MapUtils.getString(paramMap, "requestTime[0]") != null && MapUtils.getString(paramMap, "requestTime[1]") != null) {
            queryWrapper.between("d_request_time", MapUtils.getString(paramMap, "requestTime[0]"), MapUtils.getString(paramMap, "requestTime[1]"));
        }

        return ResponseData.ok(mtosiLogService.page(page, queryWrapper));
    }

    /*@ApiOperation(value = "delete", notes = "删除日志记录", httpMethod = "POST")
    @PostMapping("/delete")
    @Transactional
    public ResponseData<?> delete(HttpServletRequest request) {

        Map<String, String> paramMap = RequestUtil.getParamMap(request);
        String ids = MapUtils.getString(paramMap,"Ids");

        List list = Arrays.asList(ids.split(","));
        boolean isSuccess = mtosiLogService.removeByIds(list);

        return isSuccess ? ResponseData.ok("删除成功") : ResponseData.fail("删除失败");
    }*/
}
