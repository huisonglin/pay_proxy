package com.proxy.pay.proxy_pay.weixin.vo;

/**
 * 获取小程序openId的实体类
 * @author huisonglin
 *
 */
public class Jscode2SessionVO {

	private String errcode;
	
	private String errmsg;
	
	private String session_key;
	
	private String openid;
	

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
}
