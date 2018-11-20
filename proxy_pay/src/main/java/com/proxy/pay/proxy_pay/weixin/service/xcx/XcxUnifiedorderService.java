package com.proxy.pay.proxy_pay.weixin.service.xcx;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.weixin.dto.XcxUnifiedorderDTO;
import com.proxy.pay.proxy_pay.weixin.utils.MD5Util;
import com.proxy.pay.proxy_pay.weixin.utils.RequestHandler;
import com.proxy.pay.proxy_pay.weixin.utils.Sha1Util;
import com.proxy.pay.proxy_pay.weixin.utils.WXUtil;
import com.proxy.pay.proxy_pay.weixin.vo.XcxPayPramsVO;
import com.proxy.pay.proxy_pay.weixin.vo.XcxUnifiedorderVO;

@Service
public class XcxUnifiedorderService {

	
	public XcxPayPramsVO unifiedorder(XcxUnifiedorderDTO xcxUnifiedorderDTO,RequestHandler requestHandler) throws Exception {
		requestHandler.setParameter("appid", xcxUnifiedorderDTO.getAppid()); 
		requestHandler.setParameter("attach", xcxUnifiedorderDTO.getAttach()); 
		requestHandler.setParameter("body", xcxUnifiedorderDTO.getBody()); 
		requestHandler.setParameter("mch_id", xcxUnifiedorderDTO.getMch_id()); 
		requestHandler.setParameter("detail", xcxUnifiedorderDTO.getDetail()); 
		requestHandler.setParameter("nonce_str", xcxUnifiedorderDTO.getNonce_str()); 
		requestHandler.setParameter("notify_url", xcxUnifiedorderDTO.getNotify_url()); 
		requestHandler.setParameter("out_trade_no", xcxUnifiedorderDTO.getOut_trade_no()); 
		requestHandler.setParameter("spbill_create_ip", xcxUnifiedorderDTO.getSpbill_create_ip());  
		requestHandler.setParameter("total_fee", xcxUnifiedorderDTO.getTotal_fee().multiply(new BigDecimal(100)).intValue()+"");
		requestHandler.setParameter("trade_type", xcxUnifiedorderDTO.getTrade_type());
		requestHandler.setParameter("openid", xcxUnifiedorderDTO.getOpenid());
		//生成报文
		String requestXml = requestHandler.getRequestXml(xcxUnifiedorderDTO.getKey());
		System.out.println(requestXml);
		//获取下单信息
		XcxUnifiedorderVO xcxUnifiedorderVO = WXUtil.unifiedorder(requestXml);
		System.out.println("响应的报文"+xcxUnifiedorderVO);
		if(!"SUCCESS".equals(xcxUnifiedorderVO.getResult_code())) {
			throw new RRException(xcxUnifiedorderVO.getReturn_msg()+":"+xcxUnifiedorderVO.getErr_code_des()+"("+xcxUnifiedorderVO.getErr_code()+")");
		}

		String timestamp = Sha1Util.getTimeStamp();
		String noncestr = Sha1Util.getNonceStr();
		String prepayId = xcxUnifiedorderVO.getPrepay_id();
		System.out.println("prepay_id="+prepayId);
		SortedMap<Object,Object> signMap = new TreeMap<Object,Object>();
	    signMap.put("appId", xcxUnifiedorderDTO.getAppid()); 
	    signMap.put("timeStamp", timestamp);
	    signMap.put("nonceStr", noncestr);
	    signMap.put("package", "prepay_id=" + prepayId);
	    signMap.put("signType", "MD5");
	    // 微信支付签名
	    String paySign = MD5Util.createSign(signMap, xcxUnifiedorderDTO.getKey());
	    
		System.out.println("paySign="+paySign);
		System.out.println("noncestr="+noncestr);
		System.out.println("timestamp="+timestamp);
		XcxPayPramsVO xcxPayPramsVO = new XcxPayPramsVO();
		xcxPayPramsVO.set_package("prepay_id="+prepayId);
		xcxPayPramsVO.setNoncestr(noncestr);
		xcxPayPramsVO.setPaySign(paySign);
		xcxPayPramsVO.setTimestamp(timestamp);
		xcxPayPramsVO.setOpenId(xcxUnifiedorderDTO.getOpenid());
		return xcxPayPramsVO;
	}
	
}
