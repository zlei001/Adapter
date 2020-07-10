package com.kt.dc.otn.service;

import com.kt.dc.otn.bean.RequestResult;

public interface DcOtnService {
    /** 
    * @Description:  1.查询指定网络分层的拓扑信息
    * @Param:  
    * @return:  RequestResult
    * @Author: Coder
    * @Date: 2020-07-07 21:11 
    */
    public RequestResult tm_get_topo();
    
    /** 
    * @Description: 2.查询指定拓扑信息
    * @Param:  
    * @return:  RequestResult
    * @Author: Coder
    * @Date: 2020-07-09 17:54 
    */
    public RequestResult tm_get_point_topo(String param);

    /**
     * @Description: 3.查询指定拓扑信息
     * @Param:  xmlParam
     * @return:  RequestResult
     * @Author: Coder
     * @Date: 2020-07-09 17:55
     */
    public RequestResult tm_get_topo_intne(String xmlParam);

    /**
     * @Description: 4.根据UUID查询节点
     * @Param:  xmlParam
     * @return:  RequestResult
     * @Author: Coder
     * @Date: 2020-07-09 17:55
     */
    public RequestResult tm_get_uuid_ne(String xmlParam);



    /**
    * @Description: 5 查询指定内部节点信息
    * @Param:
    * @return:
    * @Author: Coder
    * @Date: 2020-07-09 17:57
    */
    public RequestResult tm_get_intne();

    /**
    * @Description: 6查询指定网络拓扑的所有外部节点信息
    * @Param:
    * @return:
    * @Author: Coder
    * @Date: 2020-07-09 17:58
    */
    public RequestResult tm_get_topo_extne();
    
    /** 
    * @Description:7.查询指定外部节点信息
    * @Param:  
    * @return:  
    * @Author: Coder
    * @Date: 2020-07-09 17:58 
    */
    public RequestResult tm_get_extne();
    
    
    /** 
    * @Description: 8.查询指定网络拓扑的内部link所有信息
    * @Param:  
    * @return:  
    * @Author: Coder
    * @Date: 2020-07-09 18:00 
    */
    public RequestResult tm_get_topo_intlink();
    
    /** 
    * @Description: 9.根据UUID查询链路
    * @Param:  
    * @return:  
    * @Author: Coder
    * @Date: 2020-07-09 18:04 
    */
    public RequestResult tm_get_uuid_link(String xmlParam);


}
