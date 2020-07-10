package com.cttnet.zhwg.ywkt.mtosi.ability.utils;

import com.catt.util.lang.CryptoUtils;
import com.cttnet.common.util.SysConfig;
import com.cttnet.zhwg.ywkt.mtosi.ability.constants.Splits;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <PRE>
 * xml工具类
 * </PRE>
 * <B>项    目：</B>凯通J2SE开发平台(KTJSDP)
 * <B>技术支持：</B>广东凯通软件开发技术有限公司 (c) 2015
 * @version   1.0 2015-07-17
 * @author    廖权斌：liaoquanbin@gdcattsoft.com
 * @since     jdk版本：jdk1.6
 */
public class XmlUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(XmlUtils.class);

	public static String getValue(Element element, String key){
		return getValue(element, key, "");
	}
	
	public static String getValue(Element element, String key, String defaultValue){
		return element == null ? defaultValue :
			(element.element(key) == null ? defaultValue : element.element(key).getTextTrim());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getSkList(Element element, String nodeName, String partName, String eKey, String eValue){
		String result = "";
		Element node = element == null ? null : element.element(nodeName);
		if(node != null){
			List parts = node.elements(partName);
			result = getSkList(parts, eKey, eValue);
		}
		return result;
	}
	
	public static String getSkList(List<Object> elements, String eKey, String eValue){
		StringBuffer result = new StringBuffer();
		if(elements != null){
			for (Object object : elements) {
				if(object instanceof Element){
					Element e = (Element)object;
					String key = e.element(eKey) == null ? "" : e.element(eKey).getTextTrim();
					String value = e.element(eValue) == null ? "" : e.element(eValue).getTextTrim();
					result.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(value);
				}
			}
		}
		return result.toString();
	}
	
	public static String getDkList(Element element, String name){
		Element node = element.element(name);
		return getDkList(node);
	}
	
	@SuppressWarnings("rawtypes")
	public static String getDkList(Element element){
		StringBuffer result = new StringBuffer();
		if(element != null){
			List elements = element.elements();
			for (Object object : elements) {
				if(object instanceof Element){
					Element e = (Element)object;
					String key = e.getName().trim();
					String value = e.getTextTrim();
					result.append(Splits.WAVE).append(key).append(Splits.EQUAL).append(value);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 优化xml格式
	 * @param inputXML
	 * @return
	 */
	public static String beautifyXml(String inputXML) {
		String result = inputXML;
		int maxLogSize = Integer.parseInt(SysConfig.getProperty("", "1000"));
		if(inputXML.length() > maxLogSize){
			return result;
		}
		SAXReader reader = new SAXReader();
		XMLWriter writer = null;
		try {
			Document document = reader.read(new StringReader(inputXML));
			StringWriter stringWriter = new StringWriter();
			OutputFormat format = new OutputFormat(" ", true);
			writer = new XMLWriter(stringWriter, format);
			writer.write(document);
			writer.flush();
			String temp = stringWriter.getBuffer().toString();
			String[] lines = temp.split("\n");
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				if(!"".equals(line.trim())){
					sb.append(line).append("\n");
				}
			}
			result = sb.toString();
		} catch (IOException e) {
//			LOG.error("", e);
			
		} catch (DocumentException e) {
//			LOG.error("", e);
			
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
//					LOG.error("", e);
				}
			}
		}
		return result;
	}
	

	
	/**
	 * 私有化构造函数，避免被误用
	 */
	private XmlUtils() {}
	
	/**
	 * 生成xml报文的MD5码
	 * @param xml xml报文(utf-8编码)
	 * @return MD5码(大写)
	 */
	public static String toMD5(final String xml) {
		return CryptoUtils.toMD5((removeExtraInfo(xml))).toUpperCase();
	}
	
	/**
	 * 移除xml报文中的额外信息（命名空间、注释、节点间空字符）
	 * @param xml xml报文
	 * @return 移除额外信息的xml报文
	 */
	public static String removeExtraInfo(final String xml) {
		String rstXml = null;
		if(xml != null) {
			try {
				Document doc = DocumentHelper.parseText(xml);
				Element root = doc.getRootElement();
				removeExtraInfo(root);
				rstXml = root.asXML().replaceAll(">[\\s\r\n]+<", "><").trim();
				
			} catch (Exception e) {
				LOG.error("移除xml中的额外信息失败.");
				LOG.error("", e);
			}
		}
		return (rstXml == null ? xml : rstXml);
	}
	
	/**
	 * 移除指定节点及其所有子孙节点的额外信息（命名空间、注释）
	 * @param e 节点
	 */
	@SuppressWarnings("unchecked")
	private static void removeExtraInfo(Element e) {
		if(e != null) {
			Iterator<Element> childs = e.elementIterator();
			while(childs.hasNext()) {
				removeExtraInfo(childs.next());
			}
			
			// 移除命名空间
			Iterator<Branch> bs = e.content().iterator();
			while(bs.hasNext()) {
				Object b = bs.next();
				if(!(b instanceof DefaultText) && 
						!(b instanceof DefaultElement)) {
					bs.remove();
				}
			}
			
			// 移除命名空间前缀
			e.setName(e.getName());
		}
	}
	
	
	/**
	 * 获取节点文本
	 * @param xml 入参XML
	 * @param eName 节点名字或压缩路径
	 * @return 
	 */
	public static String getNodeValue(String xml,String eName){
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String result = getNodeValue(root, eName);
			return result;
		} catch (DocumentException e) {
			LOG.error("", e);
		}
		return "";
	}
	
	public static String getNodeValue(Element root,String eName){
		Element node = null;
		if(eName.contains("/")){
			String compressPath = eName;
			node = dfsPath(root, "", compressPath);
			
		} else {
			node = dfsPath(root, eName);
			
		}
		String result = "";
		if(node != null){
			result = node.getTextTrim();
		}
		return result;
	}
	
	/**
	 * 是否存在eName的节点
	 * @param xml 入参XML
	 * @param eName 节点名字或压缩路径
	 * @return 
	 */
	public static boolean haveNode(String xml,String eName){
		boolean haveNode = false;
		Element node = getNode(xml, eName);
		if(node != null){
			haveNode = true;
		}
		if("".equals(node.asXML())){
			LOG.error("Element content is empty! Please note!");
		}
		return haveNode;
	}
	
	/**
	 * 获取节点文本
	 * @param xml 入参XML
	 * @param eName 节点名字或压缩路径
	 * @return 
	 */
	public static Element getNode(String xml,String eName){
		Element node = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			if(eName.contains("/")){
				String compressPath = eName;
				node = dfsPath(root, "", compressPath);
				
			} else {
				node = dfsPath(root, eName);
				
			}
		} catch (DocumentException e) {
			LOG.error("", e);
		}
		return node;
	}
	
	/**
	 * 用DFS方法找目标压缩路径所对应的节点
	 * 
	 * @param e
	 *            当前节点
	 * @param ePath
	 *            当前节点路径
	 * @param cmpPath
	 *            目标压缩路径
	 * @return 目标压缩路径所对应的 节点（若找不到返回null）
	 */
	@SuppressWarnings("unchecked")
	public static Element dfsPath(Element e, String ePath, final String cmpPath) {
		Element node = null;
		if (e != null) {
			if (cmpPath.equals(ePath + "/" + e.getName())) {
				node = e;

			} else {
				ePath = ePath + "/" + e.getName().charAt(0);
				for (Iterator<Element> childs = e.elementIterator(); childs
						.hasNext();) {
					Element child = childs.next();
					node = dfsPath(child, ePath, cmpPath);
					if (node != null) {
						break;
					}
				}
			}
		}
		return node;
	}
	
	@SuppressWarnings("unchecked")
	public static Element dfsPath(Element e, String eName) {
		Element node = null;
		if (e != null) {
			if (eName.equals(e.getName())) {
				node = e;

			} else {
				for (Iterator<Element> childs = e.elementIterator(); childs
						.hasNext();) {
					Element child = childs.next();
					node = dfsPath(child, eName);
					if (node != null) {
						break;
					}
				}
			}
		}
		return node;
	}
	
	private static Element genParamsElement(Map<String, String> params){
		Element paramElement = DocumentHelper.createElement("param");
		Set<Entry<String,String>> entrySet = params.entrySet();
		for (Entry<String,String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue() == null ? "" : entry.getValue().toString();
			
			Element keyElement = DocumentHelper.createElement(key);
			keyElement.setText(value);
			
			paramElement.add(keyElement);
		}
		return paramElement;
	}
}
