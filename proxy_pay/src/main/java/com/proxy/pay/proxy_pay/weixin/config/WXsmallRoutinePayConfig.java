package com.proxy.pay.proxy_pay.weixin.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WXsmallRoutinePayConfig {
	//String a = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9d25741d9e1075d6&redirect_uri=https%3A%2F%2Fwww.huisonglin.top%2Fweixin%2Fsandu%2Fpay&response_type=code&scope=snsapi_userinfo&agentid=AGENTID&state=HUISONGLIN#wechat_redirect";
	// appid
	public static String APP_ID = "wx9d25741d9e1075d6";
	// JSAPI接口中获取openid，审核后在公众平台开启开发模式后可查看
	public static String APP_SECRET = "c33726a8e4267379e5529b5a3e489d35";
	// 受理商ID，身份标识
	public static String MCH_ID = "1498445182";
	// 商户支付密钥Key，装完整数后，配置得到。
	public static String KEY = "1145B1AFA2994480808B42793E486A81";
	// 异步回调地址
	public static String NOTIFY_URL = "https://www.huisonglin.top/wx/notify";
	// 字符编码
	public static String CHARTSET = "UTF-8";
	// 加密方式
	public static String SIGN_TYPE = "MD5";
	// redirect_uri，微信授权重定向地址
	public static String REDIRECT_URI;
	static {
		try {
			REDIRECT_URI = URLEncoder.encode("http://haiminglan.cn/parkcar/weixin/sandu/pay", CHARTSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String redirectUrl = URLEncoder.encode("https://www.huisonglin.top/wx/WXPublicNumberPay", CHARTSET);
	//	System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WXPublicNumberPayConfig.APP_ID+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_base&state=HUISONLGIN#wechat_redirect");
	}
}
