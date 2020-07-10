package com.cttnet.zhwg.ywkt.mtosi.cache;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 *  南向接口方法
 * </pre>
 *
 * @author zhangyaomin
 * @date 2020/4/7
 * @since java 1.8
 */
@Slf4j
public class MtosiMethodsData {

    public static final String METHODS_FILE = "mtosi-methods.xml";

    /**  */
    public static final Map<String, String> METHODS = new HashMap<>();

    /**
     * 初始化
     */
    public static void init() {

        InputStream resourceAsStream = MtosiMethodsData.class.getClassLoader().getResourceAsStream(METHODS_FILE);
        SAXReader saxreader = new SAXReader();
        try {
            Document read = saxreader.read(resourceAsStream);
            Element rootElement = read.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element next = (Element) iterator.next();
                String ifaceName = next.getName();
                ifaceName = ifaceName.trim();
                String text = next.getTextTrim();
                String[] methods = text.split(",");
                for (String method: methods) {
                    method = method.trim();
                    METHODS.put(method, ifaceName + "/" + method);
                }
            }
        } catch (DocumentException e) {
           log.error("", e);
        }

    }

    /**
     * 获取请求方法
     * @param cmdName cmd
     * @return method
     */
    public static String getMethodName(String cmdName) {
        return METHODS.get(cmdName);
    }


}
