package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.AccessTokenDTO;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientResult;
import com.proxy.pay.proxy_pay.weixin.utils.HttpClientUtils;
import com.proxy.pay.proxy_pay.weixin.vo.AccessTokenVO;

/**
 * 获取小程序全局唯一后台接口调用凭据（access_token）。调用各后台接口时都需使用 access_token，开发者需要进行妥善保存。
 * @author huisonglin
 *
 */
@RestController
@RequestMapping("/weixin/xcx")
public class AccessTokenController {

	@RequestMapping("/accessToken")
	public  R accessToken(AccessTokenDTO accessTokenDTO) throws Exception {
/*		accessTokenDTO.setAppid("wx9d25741d9e1075d6");
		accessTokenDTO.setSecret("c33726a8e4267379e5529b5a3e489d35");*/
		Assert.isBlank(accessTokenDTO.getAppid(), "appid不能为空");
		Assert.isBlank(accessTokenDTO.getSecret(), "secret不能为空");
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+accessTokenDTO.getAppid()+"&secret="+accessTokenDTO.getSecret();
		HttpClientResult result = HttpClientUtils.doGet(url);
		String content = result.getContent();
		AccessTokenVO accessTokenVO = JSONUtil.parse(content, AccessTokenVO.class);
		return R.ok().put("data", accessTokenVO);
		

	}

}
