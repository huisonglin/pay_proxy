package com.proxy.pay.proxy_pay.weixin.controller.xcx;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.TemplateDTO;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientResult;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientUtils;
import com.proxy.pay.proxy_pay.weixin.vo.TemplateVO;

@RestController
@RequestMapping("/weixin/xcx")
public class SendTemplateMessageController {

	@RequestMapping("/sendTemplateMessage")
	public  R sendTemplateMessageController(TemplateDTO templateDTO) throws Exception {
/*		templateDTO.setAccess_token("15_C6nglM8ZiCTudyAXlnM-zSiprJKP0zEu00bOkFAqD8kTizKLozzLmNXdsIX9aYgUgOYD2GRZe6MRonHkf_ZDjLPUMkt0rA3Fg7qFIrxYwQ4hzSck5Hdkefg6M3de81LgHIeeby-c5dlKT_--YIAdACAGQI");
		Map<String, String> headers = new HashMap<>();
		headers.put("dataType", "json");
		templateDTO.setTemplate_id("sxIMfTTqoDPnBkdDJuaCQ0lDhQKS7Nncm583SlTZNlY");
		templateDTO.setForm_id("wx20174140864185747a2527a81625848926");
		templateDTO.setTouser("osLl_4hs4cKoCt1D4MAd_JTL0pgc");
		Map<String, Map<String, String>> data = new HashMap<>();
		Map<String, String> value = new HashMap<>();
		value.put("value", "2018-11-12");
		data.put("keyword1", value);
		Map<String, String> value2 = new HashMap<>();
		value2.put("value", "拨打电话");
		data.put("keyword2", value2);
		Map<String, String> value3 = new HashMap<>();
		value3.put("value", "￥1.0");
		data.put("keyword3", value3);
		templateDTO.setPage("index");
		templateDTO.setEmphasis_keyword("keyword3.DATA");
		templateDTO.setData(data);*/
		Assert.isBlank(templateDTO.getAccess_token(), "调用凭据不能为空");
		Assert.isBlank(templateDTO.getForm_id(), "form_id不能为空");
		Assert.isBlank(templateDTO.getPage(), "page不能为空");
		Assert.isBlank(templateDTO.getTemplate_id(), "模板ID不能为空");
		Assert.isBlank(templateDTO.getTouser(), "发送人不能为空");
		Assert.isNull(templateDTO.getData(), "data不能为空");
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+templateDTO.getAccess_token();
		HttpClientResult result = HttpClientUtils.doJsonPost(url, JSON.parseObject(JSON.toJSONString(templateDTO)));
		if(result.getCode() == 200) {
			TemplateVO templateVO = JSONUtil.parse(result.getContent(), TemplateVO.class);
			System.out.println(templateVO);
			return R.ok().put("data", templateVO);
		}else {
			throw new RRException("网络请求异常");
		}
	}
	
/*	public static void main(String[] args) throws Exception {
		sendTemplateMessageController(new TemplateDTO());
	}*/
}
