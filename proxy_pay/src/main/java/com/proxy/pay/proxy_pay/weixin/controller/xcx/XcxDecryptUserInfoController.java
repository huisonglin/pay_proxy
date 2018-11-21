package com.proxy.pay.proxy_pay.weixin.controller.xcx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.pay.proxy_pay.exception.RRException;
import com.proxy.pay.proxy_pay.utils.JSONUtil;
import com.proxy.pay.proxy_pay.utils.R;
import com.proxy.pay.proxy_pay.validator.Assert;
import com.proxy.pay.proxy_pay.weixin.dto.DecryptUserInfoDTO;
import com.proxy.pay.proxy_pay.weixin.utils.AesCbcUtil;
import com.proxy.pay.proxy_pay.weixin.vo.DecryptUserInfoVO;

@RestController
@RequestMapping("/weixin/xcx")
public class XcxDecryptUserInfoController {
	
	@RequestMapping("/decryptUserInfo")
	public R decryptUserInfo(DecryptUserInfoDTO decryptUserInfoDTO) throws Exception {
/*		String encryptedData = "4uJtbu0tLQ9s/Hyt86Ti0auCd6XHrCWUD5Cc/doJzz+BvWJ0uVFmw2zVcHG5+9BukH7vS7wC5/zSMTxTy2hzaaOkc0rVwjuMGAl2UJR/q42+ajttLq6wBQC/6jMqSIg0rpqlO+KRFxuHAWtvxto6sqLQ0z8ks/jpvzre29uyHMw+Z/b9/82e5Nnu7BzgZRystptWbCoOyqzlMA2YVvD+6GAquPpybwL97P4ewWd4CGOqX7lArbJIFq7ysHEAKQNxx6MXSC2F4CpxmXRYXmbJe2VzNdhSaUlj3Q6Ytjq422cG3SkPaUUIBH1lPY99r+29dRD5jZQpb318YFn1ekkPtoue+TSP5xxv8HFFHlDMiS2ldNlI+XMUK56iBJQahfV/uNN26x0iIar4fRYaVoimHSYsB80V21CMLrxIDC1+E5nmpurnB+Uee7PhUtNfrD5ce5+WCnpjbwPl/PWC12KDGGR403HqWQp5zfcqFz6DGS8=";
		String session_key = "9RN2XGRm8PKW9acJ+m6NTQ==";
		String iv="oD69jmifPasTwkZfxXLw3A==";*/
		Assert.isBlank(decryptUserInfoDTO.getSession_key(), "session_key不能为空");
		Assert.isBlank(decryptUserInfoDTO.getIv(), "iv不能为空");
		Assert.isBlank(decryptUserInfoDTO.getEncryptedData(), "encryptedData不能为空");
		String result = AesCbcUtil.decrypt(decryptUserInfoDTO.getEncryptedData(), decryptUserInfoDTO.getSession_key(), decryptUserInfoDTO.getIv(), "UTF-8"); 
		DecryptUserInfoVO decryptUserInfoVO;
		try {
			decryptUserInfoVO = JSONUtil.parse(result, DecryptUserInfoVO.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RRException("用户信息解密出错");
		}
		return R.ok().put("data", decryptUserInfoVO);
	}

/*	public static void main(String[] args) throws Exception {
	//String encryptedData = "4uJtbu0tLQ9s/Hyt86Ti0auCd6XHrCWUD5Cc/doJzz+BvWJ0uVFmw2zVcHG5+9BukH7vS7wC5/zSMTxTy2hzaaOkc0rVwjuMGAl2UJR/q42+ajttLq6wBQC/6jMqSIg0rpqlO+KRFxuHAWtvxto6sqLQ0z8ks/jpvzre29uyHMw+Z/b9/82e5Nnu7BzgZRystptWbCoOyqzlMA2YVvD+6GAquPpybwL97P4ewWd4CGOqX7lArbJIFq7ysHEAKQNxx6MXSC2F4CpxmXRYXmbJe2VzNdhSaUlj3Q6Ytjq422cG3SkPaUUIBH1lPY99r+29dRD5jZQpb318YFn1ekkPtoue+TSP5xxv8HFFHlDMiS2ldNlI+XMUK56iBJQahfV/uNN26x0iIar4fRYaVoimHSYsB80V21CMLrxIDC1+E5nmpurnB+Uee7PhUtNfrD5ce5+WCnpjbwPl/PWC12KDGGR403HqWQp5zfcqFz6DGS8=";
		//String encryptedData = "kSs8i3TOtEbFlM/WvnrKGlY0AsWnld3SOK0hsx0mCvXX4znhZb9HA4rckltPLO/mtAAmjWP26XyJ0q3cWkiKO8r/7SiQ87ETULLqGNRAM/tuN8OMnsuGiOePiniFLg7prj6Pmq1oTP5yTQVmqhe0TpMuI6S+ACHr5RB8cXudSrmQGT7yQ+Xw/R16bU5KQvMeU3BguTfA1E5aYyY1KcujwR7DoHMuo/sau6aha1f+eAT/YyTWDzs61r0PBm8rIR0rO3WhpzYUVhew0puhh71wx94+ycoK//5t2lY9UOAzxk1sftEEOh5odgHRZaUisuamT0eGOOTwdrgcbjKg40vcrroXg+8w3De9uuGBXmnpxCiZo4j4PqmeMOhQvz3dz0O6OQRpFTXMroP+Ir4C3o4Eyq5fpr8SpXHxjFmrXQynSSBaBHXLiw99yrhpblGYG6DY9MpDDJToOWbqRiKLtd/b3v31zRTwlcez1i8pDkB1wN4=";
	//String encryptedData = "NvcWig+pK3ePATb2tTjGF0NL/yach15x2g8DEgeIS/URlu0SN6nkmfnkK0AwzsjM2jcmg4x0B2DGg0CBmtS4qzu9zZKtyWalQ8aethCkWFHmHe3XqWC4CJ84WhFmRtdMbJPsinD6XctCf+3rS/+nvg0S6Rcv0LdkZRv/l1b90Pz4G6ROL0ZVPohQHFJIWpfZkq7c2QRf78UB/AI9zQmuGN31vNLDJvKj+mH142K1qwD2s8VIeowOYBw9Yxt06eOGJNjRIKFU21Yk36Jj+DacqPzP6K2+qXFn7xzo4GVgw9BxLTaIveKuuHynvui+XuXdPy++vi+DxmZ0D2WqwQ8HChRgjEVBfn5djFnqk6d+TC6AeuwU/ZNjGmIZwvV9EERhHB2aYODAfkC58M3SbZXJaFQM2KZWYw6p+qDqxL+uIGUpx27TEyd5eU5rKrlwDr8Lne540ituajCDk+BZ9DPbJ9j2EbymAw9D0jBXo+2g2mY=";
		String a = "kysWLDxnNt7XqH+UJwM9Dvgn+AV21rU93Wnhx7Id1zecsvCNJqHN7pUhABCajoRvmUZKUQv7h7PApYuIsX2/eb5/mJuoZ2tJ5prt8hILoA9Cjem1180e+yf1oai45jb2O+HgHcHn5X4L9wFpX3DjO+HmfHgOb9FyK570rWjB+lGLmXRqz5W9l0/Js4tT+5bahx6+4LwoBAvh/ucEtY8APw9ikj5xN9MprArLqEhwUxVgKbzSWbTjzosrPlO7PJfFV/5/w2bTvBrMTOLl7/VImPGFo2E12DM8y14Ft/ed5j6n1hUsz458KS+3N8r3bwvnQnkShz1A8G+cQgOqYDdqgtULq+/UfPZ6Ha1lewPoh/TlatUZVuBhhp7grCF7llo9UZ0EXYFN195uLphwhQHBVkfyShUF4Jd9TRZw4TgY3k4vubtzOun91Hz2At1MlCVRdOPad4V2IGA2CQ2QBbIEbdHKPGsCbAUiWNnjzJi+eTM=";
		String encryptedData = "kysWLDxnNt7XqH UJwM9Dvgn AV21rU93Wnhx7Id1zecsvCNJqHN7pUhABCajoRvmUZKUQv7h7PApYuIsX2/eb5/mJuoZ2tJ5prt8hILoA9Cjem1180e yf1oai45jb2O HgHcHn5X4L9wFpX3DjO HmfHgOb9FyK570rWjB lGLmXRqz5W9l0/Js4tT 5bahx6 4LwoBAvh/ucEtY8APw9ikj5xN9MprArLqEhwUxVgKbzSWbTjzosrPlO7PJfFV/5/w2bTvBrMTOLl7/VImPGFo2E12DM8y14Ft/ed5j6n1hUsz458KS 3N8r3bwvnQnkShz1A8G cQgOqYDdqgtULq /UfPZ6Ha1lewPoh/TlatUZVuBhhp7grCF7llo9UZ0EXYFN195uLphwhQHBVkfyShUF4Jd9TRZw4TgY3k4vubtzOun91Hz2At1MlCVRdOPad4V2IGA2CQ2QBbIEbdHKPGsCbAUiWNnjzJi eTM=";
		String session_key = "Y4kPAhXzmQEY3G3ewmcb5g==";
		String iv="Oq7Wo9rD4qH6wr8WHAhSEQ==";
		String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8"); 
		DecryptUserInfoVO decryptUserInfoVO = JSONUtil.parse(result, DecryptUserInfoVO.class);
		System.out.println(decryptUserInfoVO);
	}*/
}
