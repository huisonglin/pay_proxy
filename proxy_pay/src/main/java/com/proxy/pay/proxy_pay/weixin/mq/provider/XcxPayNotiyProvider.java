package com.proxy.pay.proxy_pay.weixin.mq.provider;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class XcxPayNotiyProvider {

	@Autowired
	JmsTemplate jmsTemplate;
	
	public void XcxPayNotiy(String message) {	
		
		jmsTemplate.convertAndSend(new ActiveMQQueue("xcxPayNotiy"),message);	
		
	}
}
