package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * 功能：企业活动详情 课表
 * Created by Administrator on 2016/9/16.
 */
public class AcplanInfo implements Serializable
{

	//日期
	private String acdate;
	//开始时间
	private String startime;
	//结束时间
	private String stoptime;
	//学习内容
	private String studyInfo;

	public String getAcdate() {
		return acdate;
	}
	public void setAcdate(String acdate) {
		this.acdate = acdate;
	}
	public String getStartime() {
		return startime;
	}
	public void setStartime(String startime) {
		this.startime = startime;
	}
	public String getStoptime() {
		return stoptime;
	}
	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	public String getStudyInfo() {
		return studyInfo;
	}
	public void setStudyInfo(String studyInfo) {
		this.studyInfo = studyInfo;
	}
}
