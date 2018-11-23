package com.proxy.pay.proxy_pay.weixin.vo;

public class UnifiedorderAttachVO {

	private String apikey;
	
	private String queueName;

	

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public UnifiedorderAttachVO() {
		
	}
	public UnifiedorderAttachVO(String apiKey, String queueName) {
		super();
		this.apikey = apiKey;
		this.queueName = queueName;
	}
	
	
}
