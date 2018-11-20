package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.GenerateOpenIdDTO;
import com.proxy.pay.proxy_pay.weixin.utils.WXUtil;
import com.proxy.pay.proxy_pay.weixin.vo.Jscode2SessionVO;

/**
 * 返回用户的openId
 * @author huisonglin
 *
 */
@RestController
@RequestMapping("/weixin/xcx")
public class GenerateOpenIdController {

	@RequestMapping("/generateOpenId")
	public R GenerateOpenId(GenerateOpenIdDTO generateOpenIdDTO) {
		Assert.isBlank(generateOpenIdDTO.getCode(), "code不能为空");
		Assert.isBlank(generateOpenIdDTO.getAppId(), "appId不能为空");
		Assert.isBlank(generateOpenIdDTO.getAppSecret(), "appSecret不能为空");
		Jscode2SessionVO jscode2SessionVO = WXUtil.getAuthInfo(generateOpenIdDTO.getCode(), generateOpenIdDTO.getAppId(), generateOpenIdDTO.getAppSecret());
		return R.ok().put("data", jscode2SessionVO);
	}
	
}
