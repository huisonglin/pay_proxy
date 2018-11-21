package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.weixin.dto.PhoneNumberDTO;
import com.proxy.pay.proxy_pay.weixin.utils.AesCbcUtil;
import com.proxy.pay.proxy_pay.weixin.vo.PhoneNumberVO;

/**
 * 解密手机号并返回
 * @author huisonglin
 *
 */
@RestController
@RequestMapping("/weixin/xcx")
public class XcxDecryptedPhoneNumberController {

	@RequestMapping("/decryptedPhoneNumber")
	public R decryptedPhoneNumber(PhoneNumberDTO phoneNumberDTO) throws Exception {
		
		String result = AesCbcUtil.decrypt(phoneNumberDTO.getEncryptedData(), phoneNumberDTO.getSession_key(), phoneNumberDTO.getIv(), "UTF-8"); 
		System.out.println(result);
		PhoneNumberVO phoneNumberVO;
		try {
			phoneNumberVO = JSONUtil.parse(result, PhoneNumberVO.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RRException("用户信息解密出错");
		}
		return R.ok().put("data", phoneNumberVO);
	}
}
