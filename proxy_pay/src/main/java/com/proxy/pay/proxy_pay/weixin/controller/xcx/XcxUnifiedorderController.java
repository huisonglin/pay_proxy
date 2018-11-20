package com.proxy.pay.proxy_pay.weixin.controller.xcx;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.XcxUnifiedorderDTO;
import com.proxy.pay.proxy_pay.weixin.service.xcx.XcxUnifiedorderService;
import com.proxy.pay.proxy_pay.weixin.utils.RequestHandler;
import com.proxy.pay.proxy_pay.weixin.vo.XcxPayPramsVO;


/**
 * 微信小程序支付返回的参数
 * @author huisonglin
 *
 */
@RestController
@RequestMapping("/weixin/xcx")
public class XcxUnifiedorderController {

	@Autowired
	XcxUnifiedorderService xcxUnifiedorderService;
	/**
	 * 获取小程序的支付参数
	 * @throws Exception 
	 */
	@RequestMapping("/unifiedorder")
	public R unifiedorder(HttpServletRequest request ,HttpServletResponse response,XcxUnifiedorderDTO xcxUnifiedorderDTO) throws Exception {
		RequestHandler requestHandler = new RequestHandler(request, response);
		Assert.isBlank(xcxUnifiedorderDTO.getAppid(), "小程序ID不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getBody(), "商品描述不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getMch_id(), "商户号不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getAttach(), "附加数据不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getDetail(), "商品详情不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getNonce_str(), "随机字符串不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getNotify_url(), "通知地址不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getOut_trade_no(), "商户订单号不能为空");
		Assert.isBlank(xcxUnifiedorderDTO.getSpbill_create_ip(), "终端IP不能为空");
		Assert.isNull(xcxUnifiedorderDTO.getTotal_fee(), "标价金额不能为空");
		Assert.isNull(xcxUnifiedorderDTO.getTrade_type(), "交易类型不能为空");
		Assert.isNull(xcxUnifiedorderDTO.getOpenid(), "openid不能为空");
		Assert.isNull(xcxUnifiedorderDTO.getKey(), "商户秘钥不能为空");
		XcxPayPramsVO xcxPayPramsVO = xcxUnifiedorderService.unifiedorder(xcxUnifiedorderDTO, requestHandler);
		return R.ok().put("data", xcxPayPramsVO);
		
	}
}
