package com.proxy.pay.proxy_pay.weixin.vo;

/**
 * 小程序支付参数
 * @author huisonglin
 *
 */
public class XcxPayPramsVO {

		private String timestamp;
		private String noncestr;
		private String _package;
		private String paySign;
		private String openId;
		
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getNoncestr() {
			return noncestr;
		}
		public void setNoncestr(String noncestr) {
			this.noncestr = noncestr;
		}
		public String get_package() {
			return _package;
		}
		public void set_package(String _package) {
			this._package = _package;
		}
		public String getPaySign() {
			return paySign;
		}
		public void setPaySign(String paySign) {
			this.paySign = paySign;
		}
		
		
	
}
