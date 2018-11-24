package com.proxy.pay.proxy_pay.weixin.controller.refund;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.weixin.dto.RefundParamsVO;
import com.proxy.pay.proxy_pay.weixin.service.xcx.RefundService;

@RestController
@RequestMapping("/weixin")
public class WinXinRefundController {
	
	@Autowired
	RefundService refundService;

	@RequestMapping("/refund")
	public void refund() throws Exception {
		refundService = new RefundService();
		RefundParamsVO r = new RefundParamsVO();
		r.setAppid("wxbcac66730a4136dc");
		r.setMch_id("1498445182");
		r.setCertUrl("D:/workspace-sts-3.9.4.RELEASE/YunJiKu/src/main/resources/apiclient_cert.p12");
		r.setApiKey("1145B1AFA2994480808B42793E486A81");
		r.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
		r.setRefundFee("1");
		r.setTotalFee("100");
		r.setTransactionId("4200000235201811243988916054");
		r.setOutTradeNo("8E27E30768AE470CA9AE54670960E5F2");
		r.setOut_refund_no("5416541615414646");
		String refund = refundService.refund(r);
	}
	
	public static void main(String[] args) throws Exception {
		WinXinRefundController w = new WinXinRefundController();
		w.refund();
	}
}
