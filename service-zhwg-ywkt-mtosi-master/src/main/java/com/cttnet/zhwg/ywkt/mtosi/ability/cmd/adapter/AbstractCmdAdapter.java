package com.cttnet.zhwg.ywkt.mtosi.ability.cmd.adapter;

import com.catt.pub.net.webservices.client.WSClientUtils;
import com.catt.zhwg.ws.constructor.services.WsConstructor;
import com.catt.zhwg.ws.parser.bean.vo.WXPResult;
import com.catt.zhwg.ws.parser.services.WsParser;
import com.cttnet.common.util.JackJson;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.CmdResult;
import com.cttnet.zhwg.ywkt.mtosi.ability.cmd.bo.Command;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.Cmd;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CmdStatus;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.CustomErrorResponse;
import com.cttnet.zhwg.ywkt.mtosi.ability.enums.FactoryName;
import com.cttnet.zhwg.ywkt.mtosi.ability.task.MtosiLogTask;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.HttpsUtils;
import com.cttnet.zhwg.ywkt.mtosi.ability.utils.XmlUtils;
import com.cttnet.zhwg.ywkt.mtosi.cache.MacEmsData;
import com.cttnet.zhwg.ywkt.mtosi.cache.MtosiMethodsData;
import com.cttnet.zhwg.ywkt.mtosi.cache.MtosiTemplateData;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MacEmsPO;
import com.cttnet.zhwg.ywkt.mtosi.data.po.MtosiLogPO;
import com.cttnet.zhwg.ywkt.mtosi.utils.EnumUtils;
import com.cttnet.zhwg.ywkt.mtosi.utils.PrimaryKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * <pre>
 *  基础命令适配器
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/1/3
 * @since java 1.8
 */
@Slf4j
public abstract class AbstractCmdAdapter {

    private static final String HTTPS = "https";
    private static final int XML_MAC_TYPE = 1;
    /** 命令 */
    protected Cmd cmd;
    /** 网管配置*/
    protected MacEmsPO macEms;
    /** cmd命令对象 */
    protected Command command;
    /** 报文原始出参 */
    protected Map<String, Object> cmdOut;
    /** 原始参数 */
    protected Map<String, Object> param;

    /***
     * 构造方法
     */
    public AbstractCmdAdapter() {
    }



    /**
     * 执行请求命令
     * @param param 请求参数
     * @return CmdResult
     */
    public CmdResult doCommand(Map<String, Object> param) {

        init(param);
        log.info("\r\n[{}]服务[{}]开始执行...", command.getThreadId(), getMethod());
        try {
            //构造请求参数
            createKvs();
            //预处理请求参数,添加公共参数
            paramPretreatment();
            //构造请求参数xml
            buildRequestXml();
            //请求
            callWs();
            //解析返回结果
            parseResponse();
            //更新、解析返回结果
            updateResult();
        } catch (Exception e) {
            log.error("\r\n[{}]服务[{}]执行异常...", command.getThreadId(), getMethod(), e);
            this.command.getCmdResult().setCmdStatus(CmdStatus.RS_INFC_ERROR);
            this.command.getCmdResult().setRemark(CmdStatus.RS_INFC_ERROR.getDesc());
        } finally {
            if (Boolean.parseBoolean(SysConfig.getProperty("ywkt.mtosi.log.save-log", "true"))) {
                saveLog();
            }
        }
        return this.command.getCmdResult();
    }

    /**
     * 初始化
     * @param param param
     */
    private void init(Map<String, Object> param) {

        this.cmd = getCmd();
        if (this.cmd == null) {
            throw new RuntimeException("子适配器未设置cmd命令");
        }
        String emsId = MapUtils.getString(param, "emsId");
        if (!MacEmsData.isExistById(emsId, XML_MAC_TYPE)) {
            throw new RuntimeException("未配置该网管，请联系管理员");
        }
        this.macEms = MacEmsData.getEms(emsId, XML_MAC_TYPE);
        this.param = param;
        this.command = new Command();
        this.command.setKvs(new HashMap<>(5));
        this.command.setThreadId(PrimaryKeyUtils.generateWithoutSplit());
        this.command.setCmdResult(new CmdResult(this.command.getThreadId()));
        //设置头部参数
        String json = macEms.getParams();
        if (StringUtils.isNotBlank(json)) {
            try {
                Map mapByJsonString = JackJson.getMapByJsonString(json);
                this.command.getKvs().putAll(mapByJsonString);
            } catch (Exception e) {
                log.error("\r\n[{}]服务[{}]获取头部参数异常...", command.getThreadId(), getMethod(), e);
            }
        }
        this.command.getKvs().put("security", macEms.getUsername() + ":" + macEms.getPassword());
    }

    /**
     * 更新返回结果
     */
    protected void updateResult() {
        //如是适配器不只是返回状态，适配器内重写该方法，将cmdOut解析后设置到 cmdResult的result中
    }


    /**
     * 构造请求参数
     */
    protected abstract void createKvs();

    /**
     * cmd
     * @return cmd
     */
    protected abstract Cmd getCmd();


    /**
     *  保有字段列表.
     * 	用于构造请求报文时，强制保留值为空的字段
     * @return 保有字段列表
     */
    protected String[] getKeepTags() {
        return null;
    }

    /**
     * 参数预处理
     */
    protected void paramPretreatment() {

    }



    /**
     * 构造请求报文
     */
    private void buildRequestXml() {

        String requestTemplate = getRequestTemplate();
        log.info("\r\n[{}]服务[{}]获取数据库请求模板成功,使用数据库模板构建参数", command.getThreadId(), getMethod());
        String requestXml;
        if(StringUtils.isEmpty(requestTemplate)){
            log.info("\r\n[{}]服务[{}]获取请求模板为空,使用默认模板构建参数", command.getThreadId(), getMethod());
            requestXml = WsConstructor.constructRequest(getMethod(), command.getKvs(), getKeepTags());
        } else {
            requestXml= WsConstructor.dynConstructRequest(requestTemplate, command.getKvs(), getKeepTags());
        }
        if(StringUtils.isBlank(requestXml)) {
            throw new RuntimeException("请求模板转换参数失败");
        } else {
            requestXml = modifyRequest(requestXml);
        }
        this.command.setRequestXml(requestXml);

    }

    /**
     * 构成请求方法名称
     * @return method
     */
    protected String getConstructRequestMethod() {
        String name = this.cmd.getName();
        return MtosiMethodsData.getMethodName(name);
    }

    /**
     * 方法名称
     * @return method
     */
    protected String getMethod() {
        return this.cmd.getName();
    }

    /**
     * 修改请求参数
     * @param requestXml requestXml
     * @return new
     */
    protected String modifyRequest(String requestXml) {

        return requestXml;
    }

    /**
     * 获取厂家名称
     * @return 厂家名称
     */
    protected FactoryName getFactoryName() {
        if (FactoryName.HW.getCode().equals(this.macEms.getFactoryName())) {
            return FactoryName.HW;
        }
        if (FactoryName.FH.getCode().equals(this.macEms.getFactoryName())) {
            return FactoryName.FH;
        }
        if (FactoryName.ZTE.getCode().equals(this.macEms.getFactoryName())) {
            return FactoryName.ZTE;
        }
        if (FactoryName.AL.getCode().equals(this.macEms.getFactoryName())) {
            return FactoryName.AL;
        }
        return FactoryName.UNKNOW;
    }

    /**
     * 获取请求url
     * @return url
     */
    private String getWsdlUrl() {
        String wsdlUrl;
        String socket = this.macEms.getIp();
        String prefixUrl = StringUtils.isNotBlank(this.macEms.getPrefix()) ? this.macEms.getPrefix() : "";
        int port = this.macEms.getPort();
        String ifName = getConstructRequestMethod();
        String suffixUrl = StringUtils.isNotBlank(this.macEms.getSuffix()) ? this.macEms.getSuffix() : "";
        StringBuilder sb = new StringBuilder();
        sb.append(this.macEms.getProtocol()).append("://").append(socket);
        sb.append(":").append(port);
        if (!prefixUrl.contains("/")) {
            sb.append("/");
        }
        sb.append(prefixUrl).append(ifName);
        sb.append(suffixUrl).append("?wsdl");
        wsdlUrl = sb.toString();
        return wsdlUrl;
    }

    /**
     * 是否演示
     * @return true/false
     */
    protected boolean isShow() {
        return false;
    }

    /**
     * 获取请求模板
     * @return template
     */
    private String getRequestTemplate() {

        return MtosiTemplateData.getRequestTemplate(getFactoryName(), getMethod(), this.macEms.getEmsVersion());
    }

    /**
     * 获取响应参数模板
     * @return  template
     */
    private String getResponseTemplate(){

        return MtosiTemplateData.getResponseTemplate(getFactoryName(), getMethod(), this.macEms.getEmsVersion());
    }



    /**
     * 调用ws接口
     */
    private void callWs() {

        String threadId = this.command.getThreadId();
        String requestXml = this.command.getRequestXml();
        String responseXml = "";
        String wsdl = getWsdlUrl();
        log.info("\r\n[{}]wsdl:\r\n{}",threadId, wsdl);
        log.info("\n[{}]request:\r\n{}",threadId, requestXml);
        this.command.setStartTime(new Date());
        try {
            if(wsdl.startsWith(HTTPS)){
                responseXml = HttpsUtils.get(
                        wsdl,
                        macEms.getKeystorePath(),
                        macEms.getKeystorePassword(),
                        requestXml,
                        macEms.getConnTimeout(),
                        macEms.getCallTimeout());
            } else {
                responseXml = WSClientUtils.callSendMethod(
                        wsdl,
                        getMethod(),
                        requestXml,
                        true,
                        isShow() ? 1000 : macEms.getConnTimeout(),
                        isShow() ? 1000 : macEms.getConnTimeout());
            }
        } catch (SocketTimeoutException e) {
            log.error("\r\n[{}]调用接口超时:\r\nwsdl:{}\r\nmethod:{}",threadId, wsdl, getMethod(), e);
           responseXml = CustomErrorResponse.TIMEOUT.getCode();
        } catch (ConnectException e) {
            log.error("\r\n[{}]接口拒绝连接:\r\nwsdl:{}\r\nmethod:{}",threadId, wsdl, getMethod(), e);
            responseXml = CustomErrorResponse.CONNREFUSED.getCode();
        } catch (Exception e) {
            log.error("\r\n[{}]调用接口失败:\r\nwsdl:{}\r\nmethod:{}",threadId, wsdl, getMethod(), e);
            responseXml = CustomErrorResponse.OTHER.getCode();
        }
        this.command.setResponseXml(responseXml);
        this.command.setEndTime(new Date());
    }


    /**
     * 解析响应参数
     */
    private void parseResponse() {
        // 解析响应报文
        String responseXml = this.command.getResponseXml();
        try {
            String responseTemplate = getResponseTemplate();
            if(StringUtils.isBlank(responseTemplate)){
                this.cmdOut = WsParser.parseResponseOnly(getMethod(), responseXml);
            } else {
                this.cmdOut = WsParser.dynParseResponseOnly(responseTemplate, responseXml);
            }
            //处理报文解析异常 为空问题
            if (this.cmdOut == null) {
                log.error("[{}]解析[{}]响应报文解析失败",this.command.getThreadId(), getMethod());
                WXPResult wsdp = new WXPResult();
                wsdp.setStatus(WXPResult.STATUS_ALL_E);
                wsdp.setRemark("响应报文解析失败");
                this.cmdOut = wsdp.toMap();
            }
            String parseStatus = MapUtils.getString(this.cmdOut,WsParser.KEY_STATUS, "");
            if(!WsParser.STATUS_ALL_S.equals(parseStatus) || !responseXml.contains(getMethod())) {
                String remark = MapUtils.getString(this.cmdOut, WsParser.KEY_REMARK, "");
                // 接口返回Fault报文
                if(remark.contains("Fault")
                        || responseXml.contains("soap:Fault")
                        || responseXml.contains("SOAP-ENV:Fault")) {
                    String detailErrorReason = getDetailErrorReason(responseXml);
                    this.command.getCmdResult().setCmdStatus(CmdStatus.RS_IF_RETURN_DATA_ERROR);
                    this.command.getCmdResult().setRemark(detailErrorReason);
                    // 解析接口数据失败
                } if (CustomErrorResponse.CONNREFUSED.getCode().equals(responseXml)
                        || CustomErrorResponse.TIMEOUT.getCode().equals(responseXml)
                        || CustomErrorResponse.OTHER.getCode().equals(responseXml)){
                    CustomErrorResponse customErrorResponse = EnumUtils.getEnum(CustomErrorResponse.class, responseXml, "getCode");
                    String detailErrorReason = customErrorResponse.getDesc();
                    this.command.getCmdResult().setCmdStatus(CmdStatus.RS_IF_CONNREFUSED);
                    this.command.getCmdResult().setRemark(detailErrorReason);

                } else {
                    this.command.getCmdResult().setCmdStatus(CmdStatus.RS_IF_RETURN_DATA_ERROR);
                    if(!responseXml.contains(getMethod())){
                        this.command.getCmdResult().setRemark("厂家接口有问题");
                    } else {
                        this.command.getCmdResult().setRemark(remark);
                    }
                }
            } else {
                this.command.getCmdResult().setCmdStatus(CmdStatus.RS_SUCCESS);
            }
        } catch (Exception e) {
            log.error("[{}]解析[{}]返回参数解析失败", this.command.getThreadId(), getMethod(), e);
            this.command.getCmdResult().setCmdStatus(CmdStatus.RS_IF_RETURN_DATA_ERROR);
            this.command.getCmdResult().setRemark("接口返回数据解析失败");
        }
    }

    /**
     * 获取详细错误原因
     * @param responseXml 响应报文
     * @return 详细错误原因
     */
    protected String getDetailErrorReason(String responseXml){
        String detailReason = "unknow";
        String faultstring = XmlUtils.getNodeValue(responseXml, "faultstring");
        if(FactoryName.FH == getFactoryName()){
            String reason = XmlUtils.getNodeValue(responseXml, "reason");
            if(faultstring.equals(reason)){
                detailReason = faultstring;
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append("faultstring:[").append(faultstring).append("];");
                sb.append("reason:[").append(reason).append("]");
                detailReason = sb.toString();
            }

        } else if(FactoryName.HW == getFactoryName()){
            String additionalText = XmlUtils.getNodeValue(responseXml, "additionalText");
            detailReason = faultstring + ":" + additionalText ;
        } else {
            detailReason = faultstring;
        }
        return detailReason;
    }

    /**
     * 获取日志
     * @return MtosiLogPO
     */
    private MtosiLogPO getMtWsExeLog() {

        MtosiLogPO mtWsExeLog = new MtosiLogPO();
        mtWsExeLog.setId(this.command.getThreadId());
        mtWsExeLog.setWsUrl(getWsdlUrl());
        mtWsExeLog.setWsMethod(getMethod());
        mtWsExeLog.setInsertTime(new Date());
        mtWsExeLog.setRequestTime(this.command.getStartTime());
        mtWsExeLog.setResponseTime(this.command.getEndTime());
        mtWsExeLog.setRequestStr(this.command.getRequestXml());
        mtWsExeLog.setResponseStr(this.command.getResponseXml());
        mtWsExeLog.setDelay(this.command.getEndTime().getTime() - this.command.getStartTime().getTime());
        mtWsExeLog.setResult(this.command.getCmdResult().getCmdStatus().getDesc());
        mtWsExeLog.setRemark(this.command.getCmdResult().getRemark());
        mtWsExeLog.setSystemName(SysConfig.getProperty("spring.application.name") + ":" + SysConfig.getProperty("server.port"));
       return mtWsExeLog;
    }

    /**
     * 保存日志
     */
    private void saveLog() {

        MtosiLogPO mtWsExeLog = getMtWsExeLog();
        List<MtosiLogPO> mtWsExeLogs = new ArrayList<>();
        mtWsExeLogs.add(mtWsExeLog);
        MtosiLogTask.add(mtWsExeLogs);
    }

}
