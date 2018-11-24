package com.proxy.pay.proxy_pay.weixin.controller.refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.WeiXinRefundDTO;
import com.proxy.pay.proxy_pay.weixin.service.xcx.RefundService;
import com.proxy.pay.proxy_pay.weixin.vo.WeiXinRefundVO;

@RestController
@RequestMapping("/weixin")
public class WinXinRefundController {
	
	@Autowired
	RefundService refundService;

	@RequestMapping("/refund")
	public R refund(WeiXinRefundDTO weiXinRefundDTO) throws Exception {
/*		weiXinRefundDTO.setAppid("wxbcac66730a4136dc");
		weiXinRefundDTO.setMch_id("1498445182");
		weiXinRefundDTO.setCertUrl("D:/workspace-sts-3.9.4.RELEASE/YunJiKu/src/main/resources/apiclient_cert.p12");
		weiXinRefundDTO.setApiKey("1145B1AFA2994480808B42793E486A81");
		weiXinRefundDTO.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
		weiXinRefundDTO.setRefundFee("1");
		weiXinRefundDTO.setTotalFee("100");
		weiXinRefundDTO.setTransactionId("4200000235201811243988916054");
		weiXinRefundDTO.setOutTradeNo("8E27E30768AE470CA9AE54670960E5F2");
		weiXinRefundDTO.setOut_refund_no("5416541615414646");*/
		Assert.isBlank(weiXinRefundDTO.getApiKey(), "商户密钥不能为空");
		Assert.isBlank(weiXinRefundDTO.getAppid(), "appId不能为空");
		Assert.isBlank(weiXinRefundDTO.getCertUrl(), "证书地址不能为空");
		Assert.isBlank(weiXinRefundDTO.getMch_id(), "商户号不能为空");
		Assert.isBlank(weiXinRefundDTO.getNonce_str(), "随机字符串不能为空");
		Assert.isBlank(weiXinRefundDTO.getOut_refund_no(), "商户退款单号不能为空");
		Assert.isBlank(weiXinRefundDTO.getOutTradeNo(), "商户订单号 不能为空");
		Assert.isBlank(weiXinRefundDTO.getRefundFee(), "退款金额 不能为空");
		Assert.isBlank(weiXinRefundDTO.getTotalFee(), "订单金额不能为空");
		Assert.isBlank(weiXinRefundDTO.getTransactionId(), "交易号不能为空");
		String result = refundService.refund(weiXinRefundDTO);
		WeiXinRefundVO weiXinRefundVO = JSONUtil.parse(result, WeiXinRefundVO.class);
		return R.ok().put("data", weiXinRefundVO);
	}
	
/*	public static void main(String[] args) throws Exception {
		WinXinRefundController w = new WinXinRefundController();
	//	w.refund();
	}*/
}
