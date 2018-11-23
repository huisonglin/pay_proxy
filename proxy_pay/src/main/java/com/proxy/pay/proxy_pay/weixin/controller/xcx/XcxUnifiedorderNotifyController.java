package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.weixin.mq.provider.XcxPayNotiyProvider;
import com.proxy.pay.proxy_pay.weixin.utils.WXUtil;
import com.proxy.pay.proxy_pay.weixin.vo.UnifiedorderAttachVO;

/**
 * 小程序下单异步回调接口
 * @author huisonglin
 *
 */
@RestController
@RequestMapping("/weixin/xcx")
public class XcxUnifiedorderNotifyController {

	@Autowired
	XcxPayNotiyProvider xcxPayNotiyProvider;
	
	@RequestMapping("/unifiedorder/notify")
	public String notify(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("异步回调地址成功");

		try {
			//获取返回参数
			SortedMap<Object, Object> map = WXUtil.getReqParams(request);
			if ("SUCCESS".equals(map.get("return_code"))) {
				// 验证签名
				String attach = map.get("attach").toString();
				if(attach != null) {
					UnifiedorderAttachVO attachVO = JSONUtil.parse(attach, UnifiedorderAttachVO.class);
					if (WXUtil.checkSign(map,attachVO.getApikey())) {
						System.out.println("验签名成功");
						// 这个地方可添加更改商户订单状态操作
						System.out.println(map);
						xcxPayNotiyProvider.XcxPayNotiy(attachVO.getQueueName(),JSON.toJSONString(map));
						return "SUCCESS";
					} else {
						throw new RRException("验签名失败");
					}
				}else {
					throw new RRException("无法解析attach");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FALL";
	}
}
