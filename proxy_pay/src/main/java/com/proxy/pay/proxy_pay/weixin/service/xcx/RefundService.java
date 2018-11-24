package com.proxy.pay.proxy_pay.weixin.service.xcx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.proxy.pay.proxy_pay.weixin.dto.RefundParamsVO;
import com.proxy.pay.proxy_pay.weixin.utils.WXUtil;


@Component
public class RefundService {
	

    @SuppressWarnings("deprecation")
	public  String refund(RefundParamsVO refundParamsVO) throws Exception {

    	KeyStore keyStore  = KeyStore.getInstance("PKCS12");
    	String mchId = refundParamsVO.getMch_id();
        FileInputStream instream = new FileInputStream(new File(refundParamsVO.getCertUrl()));
        try {
            keyStore.load(instream, mchId.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        // Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpget = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            Map<String, Object> signMap = new HashMap<>();
            signMap.put("appid", refundParamsVO.getAppid());
            signMap.put("mch_id", mchId);
            signMap.put("out_trade_no", refundParamsVO.getOutTradeNo());  //商户订单号
            signMap.put("transaction_id", refundParamsVO.getTransactionId()); //微信订单号
            signMap.put("out_refund_no", refundParamsVO.getOut_refund_no());  //商户退款单号
            signMap.put("total_fee", refundParamsVO.getTotalFee());  //订单金额
            signMap.put("refund_fee", refundParamsVO.getRefundFee()); //退款金额
            signMap.put("nonce_str",refundParamsVO.getNonce_str());
            String reqBody = WXUtil.TranferXmlAddSign(signMap, refundParamsVO.getApiKey());
            System.out.println("executing request" + httpget.getRequestLine());
            httpget.setEntity(new StringEntity(reqBody));
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text;
                    String result="";
                    while ((text = bufferedReader.readLine()) != null) {
                    	result+=text;
                    }    
                    if(result !=null||!"".equals(result)) {
                        Map<String, String> resultMap = WXUtil.doXMLParse(result);
                        System.out.println("退款返回信息:"+JSON.toJSONString(resultMap));
                        if("SUCCESS".equals(resultMap.get("return_code"))) {
                        	if("SUCCESS".equals(resultMap.get("result_code"))) {
                        		
                        	}
                        }
                        return resultMap.get("return_msg");     
                    }   
                }
                
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return null;
    }
}
