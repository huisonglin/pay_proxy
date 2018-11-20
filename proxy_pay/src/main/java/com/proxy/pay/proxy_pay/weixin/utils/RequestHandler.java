package com.proxy.pay.proxy_pay.weixin.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: RequestHandler  
 * @author userLi
 * @date 2017年9月12日 下午2:59:35
 */
public class RequestHandler {
	private String gateUrl;
	private String key;
	private SortedMap<String, String> parameters;
	private String debugInfo;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
		this.key = "";
		this.parameters = new TreeMap<String, String>();
		this.debugInfo = "";
	}
	public void init() {
		//nothing to do
	}
	public String getGateUrl() {
		return gateUrl;
	}

	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	public SortedMap<String, String> getAllParameters() {		
		return this.parameters;
	}

	public String getDebugInfo() {
		return debugInfo;
	}
	public static String CHARTSET = "UTF-8";
	public String getRequestURL(String key) throws UnsupportedEncodingException {
		this.createSign(key);
		StringBuffer sb = new StringBuffer();
		Set<?> es = this.parameters.entrySet();
		Iterator<?> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, CHARTSET) + "&");
			} else {
				sb.append(k + "=" + v.replace("\\.", "%2E") + "&");
			}
		}
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		return this.getGateUrl() + "?" + reqPars;
	}
	
	public void doSend(String key) throws UnsupportedEncodingException, IOException {
		this.response.sendRedirect(this.getRequestURL(key));
	}
	protected void createSign(String key) {
		StringBuffer sb = new StringBuffer();
		Set<?> es = this.parameters.entrySet();
		Iterator<?> it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(), CHARTSET).toUpperCase();
		this.setParameter("sign", sign);
		this.setDebugInfo(sb.toString() + " => sign:" + sign);	
	}
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}
	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}
	
	/**
	 * 自定义函数，官方没有
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	
	/**
	 * 自定义函数，在官方文档中没有
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getRequestXml(String key) throws UnsupportedEncodingException {
		this.createSign(key);
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<?> es = this.parameters.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
