package com.proxy.pay.proxy_pay.utils;

import com.alibaba.fastjson.JSON;


public class JSONUtil {


	/**
	 * 
	 * 将json形式字符串转换为java实体类
	 * 
	 */
	public static <T> T parse(String jsonStr, Class<T> clazz) {
	    T parseObject = JSON.parseObject(jsonStr, clazz);
	    return parseObject;
	}
	
	
}
