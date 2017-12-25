/**
 * 
 */
package com.nian.api;

/**
 * 
 * 设备信息
 * @author 念小玲
 *
 * 2016年3月28日上午11:42:46
 */

/**
 * 手机型号参数
 * @author admin
 *
 */
public class DeviceInfo{
	//平吧
	private String platform;
	//型号
	private String model;
	//厂商
	private String factory;
	//屏幕尺寸
	private String screen_size;
	//屏幕密度
	private Double denstiy;
	//IMEI码
	private String imei;
	//mac地址
	private String mac;
	//GPRS模块信息
	private String gprs;	
	//APP(手机等设备）定义信息,即设备所在位置-续度
	private Double latitude;
	//APP(手机等设备）定义信息,即设备所在位置-经度
	private Double longitude;
	
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getScreen_size() {
		return screen_size;
	}
	public void setScreen_size(String screen_size) {
		this.screen_size = screen_size;
	}
	public Double getDenstiy() {
		return denstiy;
	}
	public void setDenstiy(Double denstiy) {
		this.denstiy = denstiy;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getGprs() {
		return gprs;
	}
	public void setGprs(String gprs) {
		this.gprs = gprs;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public DeviceInfo() {
		
	}
	
	
	
	
}
