package com.example.sampleandroid.mode;

import java.io.Serializable;

public class AppVersion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1431253138874395049L;
	/**
	 * 0=不需要 </br>1=建议升级</br> 2=需要升级
	 */
	public String update;
	/**
	 * 升级后的版本
	 */
	public String version;
	/**
	 * 升级描述
	 */
	public String updateDesc;
	/**
	 * 升级描述
	 */
	public String downLoadUrl;
	@Override
	public String toString() {
		return "AppVersion [update=" + update + ", version=" + version
				+ ", updateDesc=" + updateDesc + ", downLoadUrl=" + downLoadUrl
				+ "]";
	}

}