package com.proxy.pay.proxy_pay.weixin.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.weixin.config.WXsmallRoutinePayConfig;
import com.proxy.pay.proxy_pay.weixin.vo.Jscode2SessionVO;
import com.proxy.pay.proxy_pay.weixin.vo.XcxUnifiedorderVO;



public class WXUtil {


	/**
	 * 微信小程序获取用户的openId
	 */
	public static Jscode2SessionVO getAuthInfo(String code,String appid,String appsecret) {
		String parms = "appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";
		String url = "https://api.weixin.qq.com/sns/jscode2session?"+parms;
		String result = HttpClientUtil.sendGET(url, null);
		System.out.println("result:"+result);		
		// 获取openid
		Jscode2SessionVO jscode2SessionVO = JSONUtil.parse(result, Jscode2SessionVO.class);
		return jscode2SessionVO;
	} 
	public static String CHARTSET = "UTF-8";
	public static XcxUnifiedorderVO unifiedorder(String requestXml) throws Exception {
		// 统一下单接口提交 xml格式
		URL orderUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
		HttpURLConnection conn = (HttpURLConnection) orderUrl.openConnection();
		conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
		conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
		conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
		conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
		conn.setUseCaches(false); // Post 请求不能使用缓存
		// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
		conn.setRequestProperty("Content-Length", requestXml.length() + "");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), CHARTSET);
		out.write(requestXml.toString());
		out.flush();
		out.close();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			return null;
		}
		// 获取响应内容体
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARTSET));
		String line = "";
		StringBuffer strBuf = new StringBuffer();
		while ((line = in.readLine()) != null) {
			strBuf.append(line).append("\n");
		}
		in.close();
		Map<String, String> orderMap = doXMLParse(strBuf.toString());
		XcxUnifiedorderVO xcxUnifiedorderVO = (XcxUnifiedorderVO)MapOrBeanConvert.convertMap(XcxUnifiedorderVO.class, orderMap);
		return xcxUnifiedorderVO;
	}
	
	/**
 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据
 * @Title: doXMLParse  
 * @param @param strxml
 * @param @return
 * @param @throws JDOMException
 * @param @throws IOException   
 * @author userLi   
 * @return Map<String,String>   
 * @date 2017年9月12日 下午3:09:13  
 * @throws
 */
public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
	strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

	if(null == strxml || "".equals(strxml)) {
		return null;
	}
	Map<String, String> m = new HashMap<String, String>();
	
	InputStream in = new ByteArrayInputStream(strxml.getBytes(CHARTSET));
	SAXBuilder builder = new SAXBuilder();
	Document doc = builder.build(in);
	Element root = doc.getRootElement();
	List<?> list = root.getChildren();
	Iterator<?> it = list.iterator();
	while(it.hasNext()) {
		Element e = (Element) it.next();
		String k = e.getName();
		String v = "";
		List<?> children = e.getChildren();
		if(children.isEmpty()) {
			v = e.getTextNormalize();
		} else {
			v = getChildrenText(children);
		}
		
		m.put(k, v);
	}
	in.close();
	
	return m;
}

/**
 * 获取子结点的xml
 * @Title: getChildrenText  
 * @param @param children
 * @param @return   
 * @author userLi   
 * @return String   
 * @date 2017年9月12日 下午3:09:27  
 * @throws
 */
public static String getChildrenText(List<?> children) {
	StringBuffer sb = new StringBuffer();
	if(!children.isEmpty()) {
		Iterator<?> it = children.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String name = e.getName();
			String value = e.getTextNormalize();
			List<?> list = e.getChildren();
			sb.append("<" + name + ">");
			if(!list.isEmpty()) {
				sb.append(getChildrenText(list));
			}
			sb.append(value);
			sb.append("</" + name + ">");
		}
	}
	
	return sb.toString();
}

	public static JSONObject getAuthInfoForXCX(String code,String appId,String appSecret) {
	/*		String openParam = "appid=" + SanduConfig.APP_ID + "&secret=" + SanduConfig.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
		String openJsonStr = HttpClientUtil.sendGET("https://api.weixin.qq.com/sns/oauth2/access_token", openParam);*/
		String openParam = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
		String openJsonStr = HttpClientUtil.sendGET("https://api.weixin.qq.com/sns/jscode2session", openParam);
		System.out.println("openJsonStr:"+openJsonStr);
		
		// 获取openid
		JSONObject openJson = JSONObject.parseObject(openJsonStr);
		return openJson;
	} 
	
	/**
	 * 签名验证
	 * @return
	 */
	public static boolean checkSign(SortedMap<Object,Object> params,String apiKey){
		
		String sign = params.get("sign").toString();//签名
		String mSign = MD5Util.createSign(params, apiKey);
		if(sign.equals(mSign)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获取支付回调请求参数
	 * @param request
	 * @return
	 */
	public static SortedMap<Object,Object> getReqParams(HttpServletRequest request) throws Exception {
		
		InputStream inStream = request.getInputStream();
	    ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int len = 0;
	    while ((len = inStream.read(buffer)) != -1) {
	        outSteam.write(buffer, 0, len);
	    }
	    outSteam.close();
	    inStream.close();
	    String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
	    
	    Map<String, String> map = WXUtil.doXMLParse(result);
	    SortedMap<Object,Object> dd = new TreeMap<Object,Object>();
	    for(Object keyValue : map.keySet()){
	    	dd.put(keyValue, map.get(keyValue));
	    }
	    
	    return dd;
	}


}
