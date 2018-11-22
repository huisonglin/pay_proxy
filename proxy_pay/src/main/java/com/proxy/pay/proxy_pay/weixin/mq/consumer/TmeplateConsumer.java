package com.proxy.pay.proxy_pay.weixin.mq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.weixin.dto.TemplateDTO;
import com.proxy.pay.proxy_pay.weixin.service.xcx.SendTemplateMessageService;

@Component
public class TmeplateConsumer {

	@Autowired
	SendTemplateMessageService sendTemplateMessageService;
	/**
	 * 小程序模板通知发送
	 * @param message
	 */
	@JmsListener(destination="xcxTmeplateNotify")
	public void consumer(String message) {		
		sendTemplateMessageService.sendMessage(JSONUtil.parse(message, TemplateDTO.class));
	}
}
