package com.proxy.pay.proxy_pay.weixin.vo;

public class DecryptUserInfoVO {

	    private String openId;
	    private String nickName;
	    private Integer gender;
	    private String language;
	    private String city;
	    private String province;
	    private String country;
	    private String avatarUrl;
	    private WatermarkVO watermark;
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public Integer getGender() {
			return gender;
		}
		public void setGender(Integer gender) {
			this.gender = gender;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getAvatarUrl() {
			return avatarUrl;
		}
		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}
		public WatermarkVO getWatermark() {
			return watermark;
		}
		public void setWatermark(WatermarkVO watermark) {
			this.watermark = watermark;
		}
		@Override
		public String toString() {
			return "DecryptUserInfoVO [openId=" + openId + ", nickName=" + nickName + ", gender=" + gender
					+ ", language=" + language + ", city=" + city + ", province=" + province + ", country=" + country
					+ ", avatarUrl=" + avatarUrl + ", watermark=" + watermark + "]";
		}
	    
	
}
