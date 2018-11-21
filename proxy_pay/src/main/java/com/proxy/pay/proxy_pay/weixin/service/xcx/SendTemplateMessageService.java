package com.proxy.pay.proxy_pay.weixin.service.xcx;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.weixin.dto.TemplateDTO;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientResult;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientUtils;
import com.proxy.pay.proxy_pay.weixin.vo.TemplateVO;

@Service
public class SendTemplateMessageService {

	
	public R sendMessage(TemplateDTO templateDTO) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+templateDTO.getAccess_token();
		HttpClientResult result = HttpClientUtils.doJsonPost(url, JSON.parseObject(JSON.toJSONString(templateDTO)));
		if(result.getCode() == 200) {
			TemplateVO templateVO = JSONUtil.parse(result.getContent(), TemplateVO.class);
			System.out.println(templateVO);
			return R.ok().put("data", templateVO);
		}else {
			throw new RRException("网络请求异常！");
		}
	}
}
