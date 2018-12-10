package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.weixin.dto.ShareInfoDTO;
import com.proxy.pay.proxy_pay.weixin.utils.AesCbcUtil;
import com.proxy.pay.proxy_pay.weixin.vo.ShareInfoVO;

@RestController
@RequestMapping("/weixin/xcx")
public class XcxDecryptedShareInfo {

	

	@RequestMapping("/decryptedShareInfo")
	public R decryptedShareInfo(ShareInfoDTO shareInfoDTO) throws Exception {
		
		String result = AesCbcUtil.decrypt(shareInfoDTO.getEncryptedData(), shareInfoDTO.getSession_key(), shareInfoDTO.getIv(), "UTF-8"); 
		System.out.println(result);
		ShareInfoVO shareInfoVO;
		try {
			shareInfoVO = JSONUtil.parse(result, ShareInfoVO.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RRException("分享信息解密出错");
		}
		return R.ok().put("data", shareInfoVO);
	}
}
