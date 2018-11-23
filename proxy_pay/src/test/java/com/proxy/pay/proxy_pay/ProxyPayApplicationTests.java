package com.proxy.pay.proxy_pay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.proxy.pay.proxy_pay.weixin.mq.provider.XcxPayNotiyProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyPayApplicationTests {

	@Autowired
	XcxPayNotiyProvider xcxPayNotiyProvider;
	@Test
	public void contextLoads() {
		xcxPayNotiyProvider.XcxPayNotiy("xcxPayNotiy-dev","消失吧");
	}

}
