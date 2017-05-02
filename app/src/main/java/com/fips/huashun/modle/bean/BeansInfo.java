package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：用户魔豆充值消费记录实体类
 * @author xie
 */

public class BeansInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	//记录id
	private String pid;
	//当前魔豆
	private String currentbean;
	//变化魔豆
	private String changebean;
	//记录生成时间
	private String addtime;
	//变更原因
	private String reason;
	//类型 1充值 2消费
	private String type;
	//消费信息
	private String buyinfo;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCurrentbean() {
		return currentbean;
	}
	public void setCurrentbean(String currentbean) {
		this.currentbean = currentbean;
	}
	public String getChangebean() {
		return changebean;
	}
	public void setChangebean(String changebean) {
		this.changebean = changebean;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBuyinfo() {
		return buyinfo;
	}
	public void setBuyinfo(String buyinfo) {
		this.buyinfo = buyinfo;
	}
}
