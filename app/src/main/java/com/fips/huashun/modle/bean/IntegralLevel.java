package com.fips.huashun.modle.bean;

import java.io.Serializable;
/**
 * 功能：用户积分等级对象
 * @author 张柳 时间：2016年9月6日19:12:32
 *
 */
public class IntegralLevel implements Serializable
{

	private static final long serialVersionUID = 1L;
	// Pid
	private int pid;
	// 等级名称
	private String levelname;
	// 等级范围小
	private int levelpointsmin;
	// 等级范围大
	private int levelpointsmax;
	// 等级编码
	private String levelcode;
	// 备注
	private String bak;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	public int getLevelpointsmin() {
		return levelpointsmin;
	}
	public void setLevelpointsmin(int levelpointsmin) {
		this.levelpointsmin = levelpointsmin;
	}
	public int getLevelpointsmax() {
		return levelpointsmax;
	}
	public void setLevelpointsmax(int levelpointsmax) {
		this.levelpointsmax = levelpointsmax;
	}
	public String getLevelcode() {
		return levelcode;
	}
	public void setLevelcode(String levelcode) {
		this.levelcode = levelcode;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
}
