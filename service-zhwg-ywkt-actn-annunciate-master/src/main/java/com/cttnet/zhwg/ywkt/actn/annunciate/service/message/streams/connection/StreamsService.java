package com.cttnet.zhwg.ywkt.actn.annunciate.service.message.streams.connection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cttnet.ywkt.actn.bean.client.ClientSvcInstance;
import com.cttnet.ywkt.actn.bean.client.sub.ClientSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.eth.EthtSvc;
import com.cttnet.ywkt.actn.bean.eth.sub.EthtSvcErrorInfo;
import com.cttnet.ywkt.actn.bean.message.annunciate.Notification;
import com.cttnet.ywkt.actn.bean.message.edit.*;
import com.cttnet.ywkt.actn.bean.tunnel.TeTunnel;
import com.cttnet.ywkt.actn.enums.Operation;
import com.cttnet.ywkt.actn.message.annunciate.AnnRegular;
import com.cttnet.ywkt.actn.message.annunciate.Annunciate;
import com.cttnet.zhwg.ywkt.actn.annunciate.data.dto.ems.EmsDTO;
import com.cttnet.zhwg.ywkt.http.client.TrustAllManager;
import com.cttnet.zhwg.ywkt.actn.annunciate.config.MessageConfig;
import com.cttnet.zhwg.ywkt.actn.annunciate.service.message.base.BasicEmsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.List;

/**
 * <pre>
 *  建立通告连接
 * </pre>
 * <b>项目：</b> & <b>技术支持：凯通科技股份有限公司 (c) 2019 </b>
 *
 * @author zhangyaomin
 * @version 1.0
 * @since jdk 1.8
 */
@Slf4j
public class StreamsService extends BasicEmsClient {

    private SSLContext sslContext;

    /** 运行状态 */
    private boolean running;
    /** 通告回调 */
    private Annunciate annunciate;
    /** 订阅Id */
    private long subscriptionId;
    /** 连接 */
    private URLConnection conn;

    private InputStream is;

    private InputStreamReader iReader;

    private BufferedReader bReader;

    /**
     * 通告
     * @param subscriptionId 订阅Id,保留参数，后续可能需要根据订阅Id获取变更通知
     * @param emsDto 网管对象
     * @param annunciate 通告
     */
	public StreamsService(long subscriptionId, EmsDTO emsDto, Annunciate annunciate) {
		super(emsDto);

		if (annunciate == null) {
			throw new RuntimeException("annunciate is null");
		}
		this.subscriptionId = subscriptionId;
        this.annunciate = annunciate;
		try {
			this.sslContext = SSLContext.getInstance(emsDto.getProtocolLayer() != null ? emsDto.getProtocolLayer() : "TLS");
			this.sslContext.init(null, new TrustManager[] { new TrustAllManager() }, new SecureRandom());
		} catch (GeneralSecurityException e) {
			log.error("", e);
		}
		if (this.sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
		}
	}

    /**
     * 建立通告长连接
     */
    public synchronized void execute() {

        if (isRunning()) {
            return;
        }
        try {
            String url = emsDto.getBaseUrl() + MessageConfig.getInstance().getStreamsUrl();
            URL a = new URL(url);
            //建立http连接
            this.conn = a.openConnection();
            this.conn.setRequestProperty("Authorization",
                    "Basic " + new sun.misc.BASE64Encoder().encode((emsDto.getUsername() + ":" + emsDto.getPassword()).getBytes()));
            this.is = conn.getInputStream();
            this.iReader = new InputStreamReader(this.is, StandardCharsets.UTF_8);
            this.bReader = new BufferedReader(this.iReader);
            String msg = null;
            running = true;
            while ((msg = bReader.readLine()) != null) {
                if (StringUtils.isNotBlank(msg)) {
                	log.info(msg);
                	try {
                	    if (msg.equals(MessageConfig.getInstance().getHeartbeat())) {
                            this.annunciate.heartbeat(msg);
                        } else if(msg.startsWith(MessageConfig.getInstance().getDataPrefix())){
                        	Notification notification = getNotificationByMsg(msg);
                            call(notification, annunciate);
                        }
					} catch (Exception e) {
						log.error(msg, e);
						annunciate.error(msg, e.getMessage());
					}

                }

            }
        } catch (IOException e) {
            log.error("建立通告长连接失败", e);
            running = false;
            try {
                if (bReader != null){
                    this.bReader.close();
                }
                if (iReader != null) {
                    this.iReader.close();
                }
                if (is != null) {
                    this.is.close();
                }
            } catch (IOException ex) {
                log.error("", e);
            }
        }
    }

    /**
     * 通告对象
     * @param notification
     * @param annunciate
     * @return
     */
    private boolean call(Notification notification, Annunciate annunciate) {


        AnnRegular regular = new AnnRegular(notification);
        Operation operation = regular.getOperation();

        if (isNull(operation, annunciate, notification)) {
            log.error("operation is null");
            return false;
        }
        if (regular.match(AnnRegular.TargetRegexp.CLIENT_PS)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.CLIENT_PS);
            String id = ids.get(0);
            ClientSvcPsEdit psEdit = regular.getObject(ClientSvcPsEdit.class);
            if (isNull(psEdit, annunciate, notification)) {
                return false;
            }
            psEdit.setName(id);
            annunciate.clientSvcPs(operation, id, psEdit.getProvisioningState() , regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.CLIENT)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.CLIENT);
            String id = ids.get(0);
            ClientSvcInstance instance = null;
            if (operation == Operation.DELETE) {
                instance = new ClientSvcInstance();
                instance.setClientSvcName(id);
            } else {
                ClientSvcEdit edit = regular.getObject(ClientSvcEdit.class);
                if (isNull(edit, annunciate, notification)) {
                    return false;
                }
                instance = edit.getClientSvcInstances().get(0);
            }
            annunciate.clientSvc(operation, instance, regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.CLIENT_ERROR)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.CLIENT_ERROR);
            String id = ids.get(0);
            ClientSvcErrorInfo clientSvcErrorInfo = regular.getObject(ClientSvcErrorInfo.class);
            if (isNull(clientSvcErrorInfo, annunciate, notification)) {
                return false;
            }
            annunciate.clientSvcError(operation,id, clientSvcErrorInfo, regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.ETH_PS)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.ETH_PS);
            String id = ids.get(0);
            EthSvcPsEdit psEdit = regular.getObject(EthSvcPsEdit.class);
            if (isNull(psEdit, annunciate, notification)) {
                return false;
            }
            psEdit.setName(id);
            annunciate.ethSvcPs(operation, id, psEdit.getProvisioningState(), regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.ETH)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.ETH);
            String id = ids.get(0);
            EthtSvc instance = null;
            if (operation == Operation.DELETE) {
                instance = new EthtSvc();
                instance.setEthtsvcname(id);
            } else {
                EthSvcEdit edit = regular.getObject(EthSvcEdit.class);
                if (isNull(edit, annunciate, notification)) {
                    return false;
                }
                instance = edit.getEthtSvcs().get(0);
            }
            annunciate.ethSvc(operation, instance, regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.ETH_ERROR)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.ETH_ERROR);
            String id = ids.get(0);
            EthtSvcErrorInfo ethtSvcErrorInfo = regular.getObject(EthtSvcErrorInfo.class);
            if (isNull(ethtSvcErrorInfo, annunciate, notification)) {
                return false;
            }
            annunciate.ethtSvcError(operation,id, ethtSvcErrorInfo, regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.TUNNEL_PS)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.TUNNEL_PS);
            String id = ids.get(0);
            TunnelPsEdit psEdit = regular.getObject(TunnelPsEdit.class);
            if (isNull(psEdit, annunciate, notification)) {
                return false;
            }
            psEdit.setName(id);
            annunciate.tunnelPs(operation, id, psEdit.getProvState(), regular.getTime());
        } else if (regular.match(AnnRegular.TargetRegexp.TUNNEL)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.TUNNEL);
            String id = ids.get(0);
            TeTunnel instance = null;
            if (operation == Operation.DELETE) {
                instance = new TeTunnel();
                instance.setName(id);
            } else {
                TunnelEdit edit = regular.getObject(TunnelEdit.class);
                if (isNull(edit, annunciate, notification)) {
                    return false;
                }
                instance = edit.getTunnels().get(0);
            }
            annunciate.tunnel(operation, instance, regular.getTime());
        }else if (regular.match(AnnRegular.TargetRegexp.CLIENT_CPE_ONLINE)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.CLIENT_CPE_ONLINE);
            String id = ids.get(0);
            annunciate.clientCpeOnline(operation,id,regular.getTime());
        }else if (regular.match(AnnRegular.TargetRegexp.ETH_CPE_ONLINE)) {
            List<String> ids = regular.find(AnnRegular.TargetRegexp.ETH_CPE_ONLINE);
            String id = ids.get(0);
            annunciate.ethCpeOnline(operation,id,regular.getTime());
        } else {
            annunciate.other(operation, notification, regular.getTime());
        }
        return true;
    }

    /**
     *
     * <p>Description: 获取数据</p>
     * @author suguisen
     *
     * @param msg
     * @return
     */
    private Notification getNotificationByMsg(String msg) {

        int indexOf = msg.indexOf("data:");
		if(indexOf != -1) {
			msg = msg.substring(indexOf + 5);
		}
		JSONObject jsonObject = JSON.parseObject(msg);
		Object object = jsonObject.get("ietf-restconf:notification");
		Notification notification = JSONObject.parseObject(object.toString(),Notification.class);
		return notification;
	}


	private boolean isNull(Object object, Annunciate annunciate, Notification notification) {

        if (object == null) {
            annunciate.error(JSON.toJSONString(notification), "通告格式不支持");
            return true;
        }
        return false;
    }


	public boolean isRunning() {
        return running;
    }
}
