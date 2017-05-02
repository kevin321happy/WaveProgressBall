package com.fips.huashun.modle.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UserInfo implements Serializable
{
    // 用户魔豆
    private int bean_points;
    // 创建时间
    private String create_date;
    //
    private String email;

    private boolean email_verified;

    private String file_id;

    private String last_login;

    private String mail;

    private String openid;

    private String password;
    // 手机号
    private String phone;

    private boolean phone_verified;
    // 当前积分
    private int points;
    // 是否有企业 （0 没有 1 有）
    private String status;
    // 总积分
    private int total_points;
    // 真实姓名
    private String truename;
    // 用户名
    private String user_name;
    // 用户ID
    private String userid;
    // 个性签名
    private String signature;
    // 用户等级
    private String levelname;
    // 用户头像
    private String filepath;
    // 性别
    private String sex;
    // 生日
    private String birthday;
    // 婚恋状态
    private String marry;
    // 工号
    private String worknum;
    // 部门
    private String dep;
    // 职务
    private String job;
    private String readstatus;

    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus;
    }

    public int getBean_points()
    {
        return bean_points;
    }

    public void setBean_points(int bean_points)
    {
        this.bean_points = bean_points;
    }

    public String getCreate_date()
    {
        return create_date;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public boolean isEmail_verified()
    {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified)
    {
        this.email_verified = email_verified;
    }

    public String getFile_id()
    {
        return file_id;
    }

    public void setFile_id(String file_id)
    {
        this.file_id = file_id;
    }

    public String getLast_login()
    {
        return last_login;
    }

    public void setLast_login(String last_login)
    {
        this.last_login = last_login;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public boolean isPhone_verified()
    {
        return phone_verified;
    }

    public void setPhone_verified(boolean phone_verified)
    {
        this.phone_verified = phone_verified;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getTotal_points()
    {
        return total_points;
    }

    public void setTotal_points(int total_points)
    {
        this.total_points = total_points;
    }

    public String getTruename()
    {
        return truename;
    }

    public void setTruename(String truename)
    {
        this.truename = truename;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getLevelname()
    {
        return levelname;
    }

    public void setLevelname(String levelname)
    {
        this.levelname = levelname;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getMarry()
    {
        return marry;
    }

    public void setMarry(String marry)
    {
        this.marry = marry;
    }

    public String getWorknum()
    {
        return worknum;
    }

    public void setWorknum(String worknum)
    {
        this.worknum = worknum;
    }

    public String getDep()
    {
        return dep;
    }

    public void setDep(String dep)
    {
        this.dep = dep;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }
}
