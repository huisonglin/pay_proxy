/**
  * Copyright 2018 bejson.com 
  */
package com.proxy.pay.proxy_pay.weixin.vo;

/**
 * Auto-generated: 2018-11-21 9:59:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WatermarkVO {

    private long timestamp;
    private String appid;
    public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
     }
     public long getTimestamp() {
         return timestamp;
     }

    public void setAppid(String appid) {
         this.appid = appid;
     }
     public String getAppid() {
         return appid;
     }
	@Override
	public String toString() {
		return "WatermarkVO [timestamp=" + timestamp + ", appid=" + appid + "]";
	}
     

}